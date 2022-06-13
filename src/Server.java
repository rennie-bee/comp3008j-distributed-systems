import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Server {
    final static int port = 10086;
    private ServerSocket serverSocket;
    private Socket socket = null;
    private HashMap<String, Integer> UHPT = new HashMap<>(); // Universal Hashed Peer Table
    private HashMap<String, ArrayList<UUID>> UHRT = new HashMap<>(); // Universal Hashed Resource Table
    private HashMap<String, Integer> portMap = new HashMap<>(); // key: String of GUID, value: port

    public HashMap<String, Integer> getUHPT() {
        return UHPT;
    }

    public void setUHPT(HashMap<String, Integer> UHPT) {
        this.UHPT = UHPT;
    }

    public HashMap<String, ArrayList<UUID>> getUHRT() {
        return UHRT;
    }

    public void setUHRT(HashMap<String, ArrayList<UUID>> UHRT) {
        this.UHRT = UHRT;
    }

    public HashMap<String, Integer> getPortMap() {
        return portMap;
    }

    public Server() throws IOException{
        this.serverSocket = new ServerSocket(port);
    }

    public void begin() throws IOException {
        while (true) {
            try {
                socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                // FileOutputStream fileOutputStream = new FileOutputStream(socket.getOutputStream());
                System.out.println("A new client connects" + socket);
                Thread thread = new ServerThread(this, socket, dataInputStream, dataOutputStream);
                thread.start();
            } catch (Exception e) {
                assert socket != null;
                socket.close();
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        try {
            Server server = new Server();
            server.begin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





