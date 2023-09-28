import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

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
    JTextArea jTextArea;
    JTextField jTextFieldLogin;
    JTextField jPasswordField;
    JButton btnLogin;
    boolean isLoginOk = false;
    String loggedUsername; // Получаем текст из поля ввода


    public Client(Server server) {
        this.server = server;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(POS_X - WIDTH, POS_Y, WIDTH, HEIGHT);
        setTitle("Chat Client");
        jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        setResizable(false);

        JPanel panBottom = new JPanel(new GridLayout(1, 1));
        JPanel panNorth = new JPanel(new GridLayout(1, 1));
        panNorth.add(loginPassPanel());
        panBottom.add(sendMessagePanel());
        add(panNorth, BorderLayout.NORTH);
        add(jTextArea);
        add(panBottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private Component loginPassPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(2, 3));
        JTextField jTextFieldIP = new JTextField();
        jTextFieldIP.setText("127.0.0.1");
        JTextField jTextFieldPort = new JTextField();
        jTextFieldPort.setText("8080");
        jTextFieldLogin = new JTextField();
        jTextFieldLogin.setText(FIELD_LOGIN_DEFAULT);
        jPasswordField = new JPasswordField();
        jPasswordField.setText("1234");
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server.isServerWorking) {
                    loggedUsername = jTextFieldLogin.getText(); // Получаем текст из поля ввода
                    String pass = jPasswordField.getText(); // Получаем текст из поля ввода
                    if ((loggedUsername.equalsIgnoreCase(LOGIN_1) && pass.equals(PASSWORD_1)
                            || (loggedUsername.equalsIgnoreCase(LOGIN_2) && pass.equals(PASSWORD_2)))) {
                        isLoginOk = true;
                        JOptionPane.showMessageDialog(loginPanel, "You are logged in as: " + loggedUsername);
                    } else {
                        isLoginOk = false;
                        JOptionPane.showMessageDialog(loginPanel, "Login or password do not match, re-login please");
                    }
                } else {
                    JOptionPane.showMessageDialog(loginPanel, "Server not working, contact to system administrator.");
                }
            }
        });

        loginPanel.add(jTextFieldIP);
        loginPanel.add(jTextFieldPort);
        loginPanel.add(new JLabel());
        loginPanel.add(jTextFieldLogin);
        loginPanel.add(jPasswordField);
        loginPanel.add(btnLogin);
        return loginPanel;
    }

    private Component sendMessagePanel() {
        JPanel sendMessage = new JPanel(new GridLayout(1, 2));
        JButton send = new JButton("Send");
        JTextField sendTextField = new JTextField();
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server.isServerWorking || !isLoginOk) {
                    String message = sendTextField.getText();
                    if (isLoginOk) {
                        jTextArea.append(loggedUsername + ": " + message + "\n");
                        server.jTextArea.append(LocalDate.now()+" "+loggedUsername + ": " + message + "\n");
                    } else {
                        JOptionPane.showMessageDialog(sendMessage, "You are not logged in.");
                    }
                } else {
                    JOptionPane.showMessageDialog(sendMessage, "Server not working, contact to system administrator.");
                }
            }
        });
        sendMessage.add(sendTextField);
        sendMessage.add(send);

        return sendMessage;
    }
}
