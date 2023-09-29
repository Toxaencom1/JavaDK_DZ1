import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Client extends JFrame {
    private static final int POS_X = 1920;
    private static final int POS_Y = 0;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 800;
    private static final String LOGIN_1 = "Test1";
    private static final String PASSWORD_1 = "1234";
    private static final String LOGIN_2 = "Test2";
    private static final String PASSWORD_2 = "1234";
    private static final String FIELD_LOGIN_DEFAULT = "Test1";

    private final Server server;
    private JTextArea textArea;
    private JTextField textFieldLogin;
    private JTextField passwordField;
    private JLabel loginName = new JLabel("");
    private boolean isLoginOk = false;
    private String loggedUsername;


    public Client(Server server) {
        this.server = server;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(POS_X - WIDTH, POS_Y, WIDTH, HEIGHT);
        setTitle("Chat Client");
        textArea = new JTextArea();
        textArea.setEditable(false);
        setResizable(true);
        JPanel mainBottom = new JPanel(new GridLayout(1, 1));
        JPanel mainNorth = new JPanel(new GridLayout(1, 1));
        mainNorth.add(loginPassPanel());
        mainBottom.add(sendMessagePanel());
        add(mainNorth, BorderLayout.NORTH);
        add(textArea);
        add(mainBottom, BorderLayout.SOUTH);
        setVisible(true);
    }

    private Component loginPassPanel() {
        JPanel panelLogin = new JPanel(new GridLayout(2, 3));
        JTextField textFieldIP = new JTextField();
        textFieldIP.setText("127.0.0.1");
        JTextField textFieldPort = new JTextField();
        textFieldPort.setText("8080");
        textFieldLogin = new JTextField();
        textFieldLogin.setText(FIELD_LOGIN_DEFAULT);
        passwordField = new JPasswordField();
        passwordField.setText("1234");
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> {
            if (server.isServerWorking()
                    && server.getIP().equals(textFieldIP.getText())
                    && server.getPORT().equals(textFieldPort.getText())) {
                loggedUsername = textFieldLogin.getText();
                loginName.setText(String.format(" Logged as: "+loggedUsername));
                String pass = passwordField.getText();
                if ((loggedUsername.equalsIgnoreCase(LOGIN_1) && pass.equals(PASSWORD_1)
                        || (loggedUsername.equalsIgnoreCase(LOGIN_2) && pass.equals(PASSWORD_2)))) {
                    isLoginOk = true;
                    textArea.setText(server.readFromLog());
                    JOptionPane.showMessageDialog(panelLogin, "You are logged in as: " + loggedUsername);
                } else {
                    isLoginOk = false;
                    JOptionPane.showMessageDialog(panelLogin, "Login or password do not match, re-login please");
                }
            } else {
                JOptionPane.showMessageDialog(panelLogin, "Server not working, contact to system administrator.");
            }
        });

        panelLogin.add(textFieldIP);
        panelLogin.add(textFieldPort);
        panelLogin.add(loginName);
        panelLogin.add(textFieldLogin);
        panelLogin.add(passwordField);
        panelLogin.add(btnLogin);
        return panelLogin;
    }

    private Component sendMessagePanel() {
        JPanel panelSendMessage = new JPanel(new GridLayout(1, 2));
        JButton btnSend = new JButton("Send");
        JTextField textFieldSend = new JTextField();
        textFieldSend.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnSend.doClick();
                    textFieldSend.setText("");
                }
            }
        });
        btnSend.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date currentDate = new Date();
            String formattedDate = sdf.format(currentDate);
            if (server.isServerWorking() || !isLoginOk) {
                String message = textFieldSend.getText();
                if (isLoginOk) {
                    String loggedMessage = String.format(formattedDate+" "+loggedUsername + ": " + message + "\n");
                    textArea.append(loggedMessage);
                    server.addToTextArea(loggedMessage);
                    server.writeToLog(loggedMessage);
                    textFieldSend.setText("");
                } else {
                    JOptionPane.showMessageDialog(panelSendMessage, "You are not logged in.");
                }
            } else {
                JOptionPane.showMessageDialog(panelSendMessage, "Server not working, contact to system administrator.");
            }
        });
        panelSendMessage.add(textFieldSend);
        panelSendMessage.add(btnSend);
        return panelSendMessage;
    }
}
