import java.io.*;
import java.net.Socket;

public class ClientThreadSend extends Thread{
    private FileInputStream fis;
    private DataOutputStream dos;
    private DataInputStream dis;
    private Socket socket;

    public ClientThreadSend(Socket socket, DataOutputStream dos, DataInputStream dis) {
        this.socket = socket;
        this.dos = dos;
        this.dis = dis;
    }

    @Override
    public void run() {
        try {
            String path = dis.readUTF();
            File file = new File(path);
            fis = new FileInputStream(file);
            dos.writeUTF(file.getName());
            dos.flush();
            dos.writeLong(file.length());
            dos.flush();
            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
                dos.write(bytes, 0, length);
                dos.flush();
            }
            System.out.println("File send OK");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            fis.close();
            dos.close();
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
