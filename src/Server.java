import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Server extends JFrame{

    private static final int POS_X =0;
    private static final int POS_Y =0;
    private static final int WIDTH =400;
    private static final int HEIGHT = 800;

    boolean isServerWorking=false;
    JButton btnStart, btnStop;
    JTextArea textArea;

    public Server() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(POS_X,POS_Y,WIDTH,HEIGHT);

        setTitle("Chat Server");
        btnStop = new JButton("Stop");
        btnStart = new JButton("Start");
        textArea = new JTextArea();
        textArea.setEditable(false);
        setResizable(false);
        btnStart.addActionListener(e -> {
            if (!isServerWorking) {
                isServerWorking=true;
//                    JOptionPane.showMessageDialog(btnStart, "Server Start");
                System.out.println("Server Start");
            } else {
                JOptionPane.showMessageDialog(btnStart, "Server already started");
                System.out.println("Server already started");
            }
        });
        btnStop.addActionListener(e -> {
            if (isServerWorking) {
                isServerWorking=false;
                JOptionPane.showMessageDialog(btnStop, "Server Stop");
                System.out.println("Server Stopped");
            } else {
                JOptionPane.showMessageDialog(btnStop, "Server not running");
                System.out.println("Server not running");
            }
        });
        JPanel mainBottom = new JPanel(new GridLayout(1, 2));
        mainBottom.add(btnStart);
        mainBottom.add(btnStop);

        add(textArea);
        add(mainBottom, BorderLayout.SOUTH);
        setVisible(true);

    }

    public boolean isServerWorking() {
        return isServerWorking;
    }

    public void setServerWorking(boolean serverWorking) {
        isServerWorking = serverWorking;
    }
}
