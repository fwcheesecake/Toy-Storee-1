import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn extends JFrame {
    public LogIn() {
        super("Log In or Sign Up");
        this.setContentPane(contentPanel);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.setSize(400, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameField.getText();

                char[] passArr = passwordField.getPassword();
                StringBuilder passBuild = new StringBuilder();

                for(char c : passArr)
                    passBuild.append(c);

                String password = passBuild.toString();

                int result = SQL.validateLogin(username, password.hashCode());
                if(result == 2) {
                    Windows.setLogedUser(username);
                    Windows.getUserSession().setVisible(true);
                    Windows.getLogin().dispose();
                } else if(result == 1) {
                    Windows.setLogedUser(username);
                    Windows.getDatabase().setVisible(true);
                    Windows.getLogin().dispose();
                } else {
                    System.out.println("Usuario equivocado");
                }
            }
        });
    }

    private JPanel contentPanel;
    private JLabel logoLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
}
