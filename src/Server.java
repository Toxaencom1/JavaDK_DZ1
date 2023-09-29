import fileJob.ReadFromFile;
import fileJob.WriteToFile;

import javax.swing.*;
import java.awt.*;

/**
 *JFrame, Chat Server window
 */
public class Server extends JFrame{
    //region Static fields
    public static final String LOG_PATH = ".\\src\\fileJob\\log.txt";
    private static final int POS_X =0;
    private static final int POS_Y =0;
    private static final int WIDTH =400;
    private static final int HEIGHT = 800;
    //endregion
    //region Fields
    private final String IP = "127.0.0.1";
    private final String PORT = "8080";
    private boolean isServerWorking=false;
    private JButton btnStart, btnStop;
    private JTextArea textArea;
    private String textAreaFromFile;
    //endregion

    /**
     * Server constructor, contains JTextArea and two JButton (Start and stop),
     * Window is not resizable with EXIT_ON_CLOSE behavior on default 'x' button.
     * Also has two listeners on main buttons.
     * To exit pres 'Stop' button after server is already stopped.
     */
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
                textAreaFromFile = ReadFromFile.read(LOG_PATH);
                textArea.setText(textAreaFromFile);

            } else {
                JOptionPane.showMessageDialog(btnStart, "Server already started");
                System.out.println("Server already started");
            }
        });
        btnStop.addActionListener(e -> {
            if (isServerWorking) {
                isServerWorking=false;
                JOptionPane.showMessageDialog(btnStop, "Server Stopped\nClick <Stop> again to exit");
                System.out.println("Server Stopped");
            } else {
                JOptionPane.showMessageDialog(btnStop, " Exiting");
                System.out.println("Exiting");
                System.exit(0);
            }
        });
        JPanel mainBottom = new JPanel(new GridLayout(1, 2));
        mainBottom.add(btnStart);
        mainBottom.add(btnStop);
        add(textArea);
        add(mainBottom, BorderLayout.SOUTH);
        setVisible(true);
    }
    //region Methods

    /**
     * Append to log file message text.
     * @param message - text to write in log file.
     */
    public void writeToLog(String message){
        WriteToFile.write(message,LOG_PATH);
    }

    /**
     * Read all strings from log file message text.
     * @return String with all log messages
     */
    public String readFromLog(){
        return ReadFromFile.read(LOG_PATH);
    }

    /**
     * Checks servers up or not
     * @return boolean
     */
    public boolean isServerWorking() {
        return isServerWorking;
    }

    /**
     * Add message to main text area on frame
     * @param message formatted text message
     */
    public void addToTextArea(String message) {
        textArea.append(message);
    }
    //endregion

    //region Getters/Setters
    public void setServerWorking(boolean serverWorking) {
        isServerWorking = serverWorking;
    }

    public String getTextAreaFromFile() {
        return textAreaFromFile;
    }

    public String getIP() {
        return IP;
    }

    public String getPORT() {
        return PORT;
    }
    //endregion
}
