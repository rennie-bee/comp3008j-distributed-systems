import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class ServerThread extends Thread {
    private Server server;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private UUID GUID;


    public ServerThread(Server server, Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream
    ) {
        this.server = server;
        this.socket = socket;
        this.dis = dataInputStream;
        this.dos = dataOutputStream;
    }

    @Override
    public void run() {
        // Initialize the connection with the new client
        try {
            int delay = Integer.parseInt(dis.readUTF());
            int clientPort = Integer.parseInt(dis.readUTF());
            GUID = UUID.randomUUID(); // generate a new GUID for this client

            int size = dis.read();
            for (int i = 0; i < size; i++) {
                String fileMD5 = dis.readUTF();
                ArrayList<UUID> list = server.getUHRT().get(fileMD5);
                if (list == null) {
                    ArrayList<UUID> newList = new ArrayList<>();
                    newList.add(GUID);
                    server.getUHRT().put(fileMD5, newList);
                } else {
                    list.add(GUID);
                    server.getUHRT().replace(fileMD5,list);
                }
            }

            server.getUHPT().put(String.valueOf(GUID), delay); // generate a new key-value in UHPT
            dos.writeUTF(String.valueOf(GUID));
            server.getPortMap().put(String.valueOf(GUID), clientPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Interact with client
        while (true) {
            try {
                dos.writeUTF(socket + ", what do you want to do? [exit|upload|receive]");
                String response = dis.readUTF();
                if (response.equals("exit")) {
                    server.getUHPT().remove(GUID);
                    for (ArrayList<UUID> value : server.getUHRT().values()) {
                        value.remove(GUID);
                    }
                    server.getPortMap().remove(GUID);
                    System.out.println("Client " + this.socket + " exit.");
                    break;
                } else if (response.equals("upload")) {
                    dos.writeUTF("Please choose your file");
                    String fileMD5 = dis.readUTF();
                    ArrayList<UUID> list = server.getUHRT().get(fileMD5);
                    if (list == null) {
                        ArrayList<UUID> newList = new ArrayList<>();
                        newList.add(GUID);
                        server.getUHRT().put(fileMD5, newList);
                    } else {
                        list.add(GUID);
                        server.getUHRT().replace(fileMD5,list);
                    }
                } else if (response.equals("receive")) {
                    dos.writeUTF("Please enter the file MD5");
                    String inputMD5 = dis.readUTF();
                    if(server.getUHRT().containsKey(inputMD5)) {
                        //dos.write(1);
                        ArrayList<UUID> list = server.getUHRT().get(inputMD5);
                        int minDelay = server.getUHPT().get(list.get(0).toString());
                        for (UUID uuid : list) {
                            int delay = server.getUHPT().get(uuid.toString());
                            if (delay <= minDelay) {
                                minDelay = delay;
                            }
                        }
                        String key = null;
                        for (String getKey : server.getUHPT().keySet()) {
                            if(server.getUHPT().get(getKey).equals(minDelay)) {
                                key = getKey;
                            }
                        }
                        int port = server.getPortMap().get(key);
                        String path = dis.readUTF();
                        Socket socketReceive = new Socket(InetAddress.getByName("localhost"), port);
                        DataInputStream dis2 = new DataInputStream(socketReceive.getInputStream());
                        DataOutputStream dos2 = new DataOutputStream(socketReceive.getOutputStream());
                        dos2.writeUTF(path);
                        String fileName = dis2.readUTF();
                        Long fileLength = dis2.readLong();

                        // select file manually
                        int result = 0;
                        String storePath = null;
                        JFileChooser fileChooser = new JFileChooser();
                        FileSystemView fsv = FileSystemView.getFileSystemView();
                        fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
                        fileChooser.setDialogTitle("Select Your Directory");
                        fileChooser.setApproveButtonText("Confirm");
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        result = fileChooser.showOpenDialog(fileChooser);
                        if (JFileChooser.APPROVE_OPTION == result) {
                            storePath = fileChooser.getSelectedFile().getPath();
                        }

                        File file = new File(storePath + File.separatorChar + fileName);
                        System.out.println("file " + file);
                        System.out.println("file name: " + fileName);
                        System.out.println("file length: " + fileLength);
                        FileOutputStream fos2 = new FileOutputStream(file);

                        byte[] bytes = new byte[1024];
                        int length = 0;
                        //long size = dataInputStream.readLong();
                        while((length = dis2.read(bytes, 0, bytes.length)) != -1) {
                            //while(size > 0 && (length = dataInputStream.read(bytes, 0, (int)Math.min(bytes.length, size))) != -1) {
                            //while((length = dataInputStream.read(bytes)) != -1) {
                            fos2.write(bytes, 0, length);
                            fos2.flush();
                        }
                        System.out.println("File receive OK");
                        fos2.close();
                        dis2.close();
                        dos2.close();
                        socketReceive.close();
                    } else {
                        dos.write(0);
                        System.out.println("No such file available");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            this.dis.close();
            this.dos.close();

           // this.fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
