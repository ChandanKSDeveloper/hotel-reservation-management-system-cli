import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        Connection connection = DBConnection.getInstance();
        RunStatement statement = new RunStatement(connection);
        TextUI ui = new TextUI(connection, statement);
        ui.start();


    }
}