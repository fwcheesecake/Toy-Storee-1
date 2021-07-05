import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.SQLException;

public class Database extends JFrame implements ComponentListener {
    public Database() {
        super("Database");
        this.setContentPane(contentPanel);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        productsTableModel = (DefaultTableModel) productsTable.getModel();
        String[] colNames = new String[]{"ID", "Name", "Category", "Price", "Description"};
        for(String s : colNames)
            productsTableModel.addColumn(s);
        SQL.searchAction("", "toys", "", productsTable);

        usersTableModel = (DefaultTableModel) usersTable.getModel();
        colNames = new String[]{"ID", "First Name", "Last Name", "Address", "Phone 1", "Phone 2", "Age", "Username", "Password", "Is Admin"};
        for(String s : colNames)
            usersTableModel .addColumn(s);
        SQL.searchAction("", "users", "", usersTable);

        salesTableModel = (DefaultTableModel) salesTable.getModel();
        colNames = new String[]{"ID", "Name", "Category", "Price", "Description"};
        for(String s : colNames)
            salesTableModel.addColumn(s);
        SQL.searchAction("", "toys", "", salesTable);

        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String target = searchField.getText().trim();
                String table = dataLabel.getText().toLowerCase();
                switch(table) {
                    case "toys" -> SQL.searchAction(target, table, "id, name, description", productsTable);
                    case "users" -> SQL.searchAction(target, table, "id, firstname, lastname, username",
                            usersTable);
                    default -> SQL.searchAction("", "toys", "", productsTable);
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Windows.logOut(Windows.getDatabase());
            }
        });
        usersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dataLabel.setText("Users");

                CardLayout cl = (CardLayout) tablePanel.getLayout();
                cl.show(tablePanel, "Card3");
            }
        });
        productsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dataLabel.setText("Toys");

                CardLayout cl = (CardLayout) tablePanel.getLayout();
                cl.show(tablePanel, "Card1");
            }
        });
        productsTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                if(tableModelEvent.getType() == TableModelEvent.UPDATE) {
                    System.out.println("Max Col: " + productsTable.getColumnCount());
                    System.out.println("Max Row: " + productsTable.getRowCount());
                    int col = productsTable.getSelectedColumn();
                    int row = productsTable.getSelectedRow();
                    System.out.println("ID: " + productsTableModel.getValueAt(row, 0) + "\nName: " +  productsTableModel.getValueAt(row, col));
                    try {
                        SQL.makeUpdate("toys", productsTableModel.getColumnName(col), productsTableModel.getValueAt(row, col), (Integer) productsTableModel.getValueAt(row, 0));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
    }

    private JPanel navBar;
    private JButton cashierButton;
    private JTable productsTable;
    private DefaultTableModel productsTableModel;
    private JButton usersButton;
    private JButton salesButton;
    private JButton productsButton;
    private JButton exitButton;
    private JButton createButton;
    private JTextField searchField;
    private JButton searchButton;
    private JPanel contentPanel;
    private JLabel userLabel;
    private JPanel searchPanel;
    private JLabel dataLabel;
    private JPanel tablePanel;
    private JTable salesTable;
    private DefaultTableModel salesTableModel;
    private JTable usersTable;
    private JScrollPane productsCard;
    private JScrollPane salesCard;
    private JScrollPane usersCard;
    private DefaultTableModel usersTableModel;

    @Override
    public void componentResized(ComponentEvent componentEvent) {

    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {

    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {
        userLabel.setText(Windows.getLogedUser());
    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {

    }
}
