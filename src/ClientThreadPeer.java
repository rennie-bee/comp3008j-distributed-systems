import javax.imageio.IIOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThreadPeer{
    private ServerSocket smallSocket;
    private Socket socket = null;
    private int port;

    public ClientThreadPeer(int port) {
        this.port = port;
    }

    public void begin() throws IOException {
        try {
            smallSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                socket = smallSocket.accept();
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                Thread thread = new ClientThreadSend(socket, dos, dis);
                thread.start();

            } catch (Exception e) {
                smallSocket.close();
                e.printStackTrace();
            }

        }


    }


}
