import javax.swing.*;

public class Windows {
    private static LogIn login;
    private static MainSession userSession;
    private static Database database;

    private static String logedUser = "";

    //private static Stack<JFrame> windows;
    /*private static ImageIcon appIcon =
            new ImageIcon(Objects.requireNonNull(Windows.class.getResource("/res/icons" +
                    "/transparent_dark_logo.png")));
     */

    public static void logOut(JFrame currentWindow) {
        setLogedUser("");
        login.setVisible(true);
        currentWindow.dispose();
    }

    public static LogIn getLogin() {
        if(login == null) {
            login = new LogIn();
        }
        return login;
    }
    public static MainSession getUserSession() {
        if(userSession == null) {
            userSession = new MainSession();
        }
        return userSession;
    }

    public static Database getDatabase() {
        if(database == null) {
            database = new Database();
        }
        return database;
    }

    public static String getLogedUser() {
        return logedUser;
    }

    public static void setLogedUser(String logedUser) {
        Windows.logedUser = logedUser;
    }
}
