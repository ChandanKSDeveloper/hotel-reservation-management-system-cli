
import java.sql.*;

public class RunStatement {
    private final Statement statement;

    public RunStatement(Connection connection) throws SQLException {
        if(connection == null){
            throw new IllegalArgumentException("Connection cannot be null");
        }

        // this.statement = connection.createStatement();
        this.statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    public void executeQuery(String query){ // retrieve data
        try(ResultSet resultSet = statement.executeQuery(query)){
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Determine columnWidths dynamically
            int[] columnWidths = new int[columnCount];

            for (int i = 1; i <= columnCount; i++) {
                columnWidths[i-1] = Math.max(metaData.getColumnName(i).length(), 19); // 10 is minimum with
            }
            // Pre-compute the width of each column based on data
            while(resultSet.next()){
                for (int i = 1; i < columnCount; i++) {
                    int dataLength = resultSet.getString(i) != null ? resultSet.getString(i).length() : 10;
                    columnWidths[i-1] = Math.max(columnWidths[i-1], dataLength);
                }
            }

            /*
                here I faced an error :
                    Operation not allowed for a result set of type ResultSet.TYPE_FORWARD_ONLY.
                to solve this we need to create statement with resultSet to support scrolling like
                TYPE_SCROLL_INSENSITIVE or TYPE_SCROLL_SENSITIVE.
            */
            resultSet.beforeFirst();

            //header
            printSeparator(columnWidths);

            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("|  %-" + columnWidths[i-1] + "s", metaData.getColumnName(i));
            }

            System.out.println("|");

            printSeparator(columnWidths);

            // print rows for data
            while(resultSet.next()){
                for (int i = 1; i <= columnCount; i++) {
                    String data = resultSet.getString(i) != null ? resultSet.getString(i) : "null";
                    System.out.printf("| %-" + columnWidths[i-1] + "s ", data);
                }
                System.out.println("|");
            }

            printSeparator(columnWidths);
            System.out.println();

        } catch (SQLException e) {
            System.err.println("Error executing query : "+ query);
            e.printStackTrace();
        }
    }




    public  int executeUpdate(String query){
        try {
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Query executed successfully, rows affected : " + rowsAffected);
            System.out.println();
            return rowsAffected;
        } catch (SQLException e) {
            System.out.println("Error executing query: "+ query);
            e.printStackTrace();
            return -1;
        }
    }

    public boolean isResultSetNotEmpty(String query)  throws SQLException{
        try(ResultSet resultSet = statement.executeQuery(query)){
            return resultSet.next();
        }
    }


    public  void close(){
        if(statement != null){
            try{
                statement.close();
                System.out.println("Statement closed successfully");
            } catch (SQLException e) {
                System.out.println("Error closing statement: ");
                e.printStackTrace();
            }
        }
    }

    private void printSeparator(int[] columnWidths){
        StringBuilder separator = new StringBuilder("+");
        for(int width : columnWidths){
            separator.append("-".repeat(width + 2)).append("+");
        }

        System.out.println(separator);
    }
}
