import com.sun.jdi.Value;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Locale;
import java.util.Vector;

public class SQL {
    public static String makeSelection(String tableName, String[] fieldNames, String target) {
        StringBuilder query = new StringBuilder("SELECT * FROM ");
        query.append(tableName);

        if(!target.equals(""))
            if(fieldNames.length > 0 && !fieldNames[0].equals("")) {
                String prefix = getPrefix(tableName);
                query.append(" WHERE ");


                for (int i = 0; i < fieldNames.length; i++) {
                    String fieldName = fieldNames[i];
                    if (fieldName.equals("id")) {
                        if (target.matches("^\\d+$")) {
                            query.append(prefix);
                            query.append("id=");
                            query.append(target);
                            query.append(" OR ");
                        }
                    } else {
                        query.append(prefix);
                        query.append(fieldName);
                        query.append("='");
                        query.append(target);
                        query.append("'");
                        if (i != fieldNames.length - 1)
                            query.append(" OR ");
                    }
                }
            }

        query.append(";");

        return query.toString();
    }
    public static ResultSet consult(String query) throws SQLException {
        Statement s = getConnection().createStatement();
        return s.executeQuery(query);
    }
    public static void searchAction(String target, String tableName, String columnNames, JTable table) {
        String[] columns = columnNames.split(", ");
        String query;
        query = SQL.makeSelection(tableName, columns, target);
        try {
            ResultSet rs = SQL.consult(query);
            SQL.updateTable(table, rs);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static int validateLogin(String username, int password) {
        String query = "SELECT * FROM users WHERE usr_username=? AND usr_password=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setString(1, username);
            ps.setInt(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return (rs.getBoolean("is_admin")) ? 1 : 2;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public static void insert(String query) {

    }

    public static void delete(String query) {

    }

    public static void makeUpdate(String tableName, String colName, Object newValue, int id) throws SQLException {
        String prefix = getPrefix(tableName);

        String query = "UPDATE ? SET ?=? WHERE id=?";

        PreparedStatement ps = getConnection().prepareStatement(query);
        ps.setString(1, removeSpaces(tableName));
        ps.setString(2, prefix + removeSpaces(colName));
        if(newValue instanceof Integer)
            ps.setInt(3, (Integer) newValue);
        else if(newValue instanceof Double)
            ps.setDouble(3, (Double) newValue);
        else
            ps.setString(3, (String) newValue);
        ps.setInt(4, id);

        System.out.println(ps.toString());

        ps.executeUpdate();
    }

    private static String getPrefix(String tableName) {
        String prefix = tableName.substring(0, 2);
        for (int i = 2; i < tableName.length(); i++) {
            char c = tableName.charAt(i);
            if (!(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')) {
                prefix += c + "_";
                break;
            }
        }
        return prefix;
    }

    private static String removeSpaces(String s) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < s.length(); i++)
            if(s.charAt(i) != ' ')
                builder.append(s.charAt(i));
        return s.toLowerCase(Locale.ROOT);
    }

    public static void updateTable(JTable table, ResultSet rs) throws SQLException {
        int columnCount = rs.getMetaData().getColumnCount();

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        while(rs.next()) {
            Vector<Object> row = new Vector<>();
            for(int i = 1; i <= columnCount; i++)
                row.add(rs.getObject(i));
            model.addRow(row);
        }
    }

    private static Connection getConnection() {
        if(connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, pass);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }

    private static final String url = "jdbc:mariadb://localhost:3306/toy_storee";
    private static final String user = "root";
    private static final String pass = "5@%gFtF$rx";
    private static Connection connection;
}