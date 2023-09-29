import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client extends JFrame {
    private static final int POS_X = 1920;
    private static final int POS_Y = 0;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 800;
    private static final String LOGIN_1 = "TaXah";
    private static final String PASSWORD_1 = "1234";
    private static final String LOGIN_2 = "Anton";
    private static final String PASSWORD_2 = "1234";
    private static final String FIELD_LOGIN_DEFAULT = "TaXaH";
    Server server;
    JTextArea textArea;
    JTextField textFieldLogin;
    JTextField passwordField;
    JButton btnLogin;
    boolean isLoginOk = false;
    String loggedUsername;


    public Client(Server server) {
        this.server = server;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(POS_X - WIDTH, POS_Y, WIDTH, HEIGHT);
        setTitle("Chat Client");
        textArea = new JTextArea();
        textArea.setEditable(false);
        setResizable(false);

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
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> {
            if (server.isServerWorking) {
                loggedUsername = textFieldLogin.getText(); // Получаем текст из поля ввода
                String pass = passwordField.getText(); // Получаем текст из поля ввода
                if ((loggedUsername.equalsIgnoreCase(LOGIN_1) && pass.equals(PASSWORD_1)
                        || (loggedUsername.equalsIgnoreCase(LOGIN_2) && pass.equals(PASSWORD_2)))) {
                    isLoginOk = true;
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
        panelLogin.add(new JLabel());
        panelLogin.add(textFieldLogin);
        panelLogin.add(passwordField);
        panelLogin.add(btnLogin);
        return panelLogin;
    }

    private Component sendMessagePanel() {
        JPanel panelSendMessage = new JPanel(new GridLayout(1, 2));
        JButton btnSend = new JButton("Send");
        JTextField textFieldSend = new JTextField();
        btnSend.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date currentDate = new Date();
            String formattedDate = sdf.format(currentDate);
            if (server.isServerWorking || !isLoginOk) {
                String message = textFieldSend.getText();
                if (isLoginOk) {
                    textArea.append(loggedUsername + ": " + message + "\n");
                    server.textArea.append(formattedDate+" "+loggedUsername + ": " + message + "\n");
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
