import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DBNAME = "hotel_db";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DBNAME;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "chandan@root";
    private static volatile Connection connection; // volatile to ensure proper memory visibility
    //This ensures that all threads will see the correctly initialized connection instance.

    // Private constructor to prevent direct instantiation
    private DBConnection(){
        // load driver and setup connection
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");

            connection = DriverManager.getConnection(URL, USERNAME,PASSWORD);
            System.out.println("Java connected to the database successfully");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading Database driver : " + e.getMessage());
        } catch (SQLException e){
            throw new RuntimeException("Error connecting to the database : " + e.getMessage());
        }


    }

    public static Connection getInstance(){
        if(connection == null){ // This checks if the connection has already been created.
            // If it's not null, we skip the rest of the code and directly return the existing connection.
            synchronized (DBConnection.class) { // . If multiple threads try to initialize the connection
                // at the same time, this block ensures that only one thread executes the initialization code.
                // Without synchronized, two threads might create separate connections, violating the Singleton pattern.
                if(connection == null){ // Double-Checked Locking
                    // This is necessary because another thread could have created the connection
                    // between the time the first if (connection == null) was checked and when the thread entered
                    // the synchronized block.
                    new DBConnection();
                }
            }
        }

        return connection;
    }

    public static void closeConnection() {
        if(connection != null){
            try{
                connection.close();
                System.out.println("Database connection closed successfully");
            } catch (SQLException e) {
                throw new RuntimeException("Error while closing the database : " + e.getMessage());
            }
        }
    }
}
