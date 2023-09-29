import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * JFrame, Chat Client window
 */
public class Client extends JFrame {
    //region Static fields
    private static final int POS_X = 1920;
    private static final int POS_Y = 0;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 800;
    private static final String LOGIN_1 = "Test1";
    private static final String PASSWORD_1 = "1234";
    private static final String LOGIN_2 = "Test2";
    private static final String PASSWORD_2 = "1234";
    private static final String FIELD_LOGIN_DEFAULT = "Test1";
    //endregion
    //region Fields
    private final Server server;
    private JTextArea textArea;
    private JTextField textFieldLogin;
    private JTextField passwordField;
    private JLabel loginName = new JLabel("");
    private boolean isLoginOk = false;
    private String loggedUsername;
    //endregion

    /**
     * Client constructor, contains two panels(login and send message) and main text area.
     * Window is resizable with EXIT_ON_CLOSE behavior on default 'x' button.
     * Also has tree listeners on main buttons and text fields.
     * @param server client needs server to work.
     */
    public Client(Server server) {
        this.server = server;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(POS_X - WIDTH, POS_Y, WIDTH, HEIGHT);
        setTitle("Chat Client");
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        setResizable(true);
        JPanel mainBottom = new JPanel(new GridLayout(1, 1));
        JPanel mainNorth = new JPanel(new GridLayout(1, 1));
        mainNorth.add(loginPassPanel());
        mainBottom.add(sendMessagePanel());
        add(mainNorth, BorderLayout.NORTH);
        add(scrollPane);
        add(mainBottom, BorderLayout.SOUTH);
        setVisible(true);
    }
    //region Private methods
    /**
     * Method to add login panel.
     * Has a login, password, ip and port verifications.
     * Also has Listener to checks and collect data from server.
     * @return Component of JFrame
     */
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

    /**
     *  Method to send message panel.
     *  Has a text field and buttons.
     *  Also has two Listeners to send and log messages from users.
     * @return Component of JFrame
     */
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
    //endregion
}
