import javax.swing.*;

public class MainSession extends JFrame {
    public MainSession() {
        super("Main Session");
        this.setContentPane(contentPanel);

        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JButton ordersButton;
    private JTextField searchField;
    private JButton exitButton;
    private JPanel navBar;
    private JButton payButton;
    private JButton clientButton;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton amountButton;
    private JButton a4Button;
    private JButton a7Button;
    private JButton plusMinusButton;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton discountButton;
    private JButton priceButton;
    private JButton eraseButton;
    private JButton a0Button;
    private JButton pointButton;
    private JPanel rightPanel;
    private JPanel keyboardPanel;
    private JLabel userLabel;
    private JPanel contentPanel;
    private JPanel billPanel;
}
