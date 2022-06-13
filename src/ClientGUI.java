import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientGUI {
    public static void main(String[] args) throws IOException {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setPreferredSize(new Dimension(500, 600));
        addDetail(jPanel, new ClientOne());
        JFrame frame = new JFrame("Client One");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(jPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(null);
        jPanel2.setPreferredSize(new Dimension(500, 600));
        addDetail(jPanel2, new ClientOne());
        JFrame frame2 = new JFrame("Client Two");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.add(jPanel);
        frame2.pack();
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
    }

    public static void addDetail(JPanel jPanel, ClientOne client) {
        JLabel ip = new JLabel("IP:");
        ip.setBounds(60, 20, 20, 20);
        jPanel.add(ip);
        JTextField ipField = new JTextField(15);
        ipField.setBounds(80, 20, 100, 20);
        ipField.setText("127.0.0.1");
        jPanel.add(ipField);

        JLabel port = new JLabel("port:");
        port.setBounds(290, 20, 30, 20);
        jPanel.add(port);
        JTextField portField = new JTextField(15);
        portField.setBounds(320, 20, 100, 20);
        jPanel.add(portField);

        JLabel md5 = new JLabel("MD5:");
        md5.setBounds(60, 80, 30, 20);
        jPanel.add(md5);
        JTextField md5Field = new JTextField(30);
        md5Field.setBounds(90, 80, 250, 20);
        jPanel.add(md5Field);

        JButton get = new JButton("Get File");
        get.setBounds(340, 80, 80, 20);
        jPanel.add(get);

        JProgressBar bar = new JProgressBar(0, 100);
        bar.setBounds(150, 130, 200, 20);
        bar.setValue(50);
        jPanel.add(bar);

        JButton upload = new JButton("Upload File");
        upload.setBounds(190, 180, 120, 30);
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.clientThread.judge = 1;
            }
        });
        jPanel.add(upload);

        JTextArea message = new JTextArea();
        message.setBounds(100, 250, 300, 220);
        message.setEditable(false);
        message.setLineWrap(true);
        jPanel.add(message);

        JButton disconnect = new JButton("DISCONNECT");
        disconnect.setBounds(80, 520, 150, 40);
        jPanel.add(disconnect);

        JButton connect = new JButton("CONNECT");
        connect.setBounds(270, 520, 150, 40);
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.broadcast();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        jPanel.add(connect);
    }
}
