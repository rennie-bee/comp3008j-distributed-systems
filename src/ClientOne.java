import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class ClientOne {
    private final InetAddress ip = InetAddress.getByName("localhost");
    static int port = 5055; // port for this peer
    final static int delay = 50;
    private String GUID; // GUID for this peer
    private Socket socket;

    private FileInputStream fileInputStream;

    private HashMap<String, String> DHRT = new HashMap<>(); // Distributed Hashed Resource Table. key is MD5 of file, value is file path


    public HashMap<String, String> getDHRT() {
        return DHRT;
    }

    public void setDHRT(HashMap<String, String> DHRT) {
        this.DHRT = DHRT;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public int getPort() {
        return port;
    }

    public ClientOne() throws IOException {
        this.socket = new Socket(ip, Server.port);
        System.out.println("Succeed connecting to server: " + ip);
    }

    ClientThread clientThread;
    ClientThreadPeer clientThreadPeer;

    public void broadcast() throws IOException {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            clientThread = new ClientThread(this, socket, dis, dos);
            clientThreadPeer = new ClientThreadPeer(port);
            clientThread.start();
            clientThreadPeer.begin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            ClientOne clientOne = new ClientOne();
            clientOne.broadcast();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


