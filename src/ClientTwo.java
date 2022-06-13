import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class ClientTwo {
    private final InetAddress ip = InetAddress.getByName("localhost");
    static int port = 8888; // port for this peer
    final static int delay = 88;
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

    public ClientTwo() throws IOException {
        this.socket = new Socket(ip, Server.port);
        System.out.println("Succeed connecting to server: " + ip);
    }

    ClientThreadTwo clientThreadTwo;
    ClientThreadPeer clientThreadPeer;

    public void broadcast() throws IOException {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            clientThreadTwo = new ClientThreadTwo(this, socket, dis, dos);
            clientThreadPeer = new ClientThreadPeer(port);
            clientThreadTwo.start();
            clientThreadPeer.begin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            ClientTwo clientTwo = new ClientTwo();
            clientTwo.broadcast();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


