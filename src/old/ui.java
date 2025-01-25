package old;

import java.sql.Connection;
import java.sql.SQLException;

public class ui {
}
/*
import java.sql.Connection;
import java.sql.SQLException;

public class TextUI {
    private static final String DBNAME = "hotel_db";
    private static final String TABLENAME = "reservation";
    private final Connection connection;
    private final RunStatement statement;

    public TextUI( Connection connection,  RunStatement statement){
        this.connection = connection;
        this.statement = statement;
    }

    public void start() throws InterruptedException, SQLException {
        header();
        startOperation();
    }

    private void header(){
        System.out.println("+------------------------------------------------------------+");
        System.out.print(  "|       WELCOME TO HOTEL RESERVATION MANAGEMENT SYSTEM       |\n");
        System.out.println("+------------------------------------------------------------+");
    }

    private void startOperation() throws InterruptedException, SQLException {
        while(true){
            System.out.println("+-------------------------------------------+");
            System.out.println("|                  Operation                |");
            System.out.println("+-------------------------------------------+");
            System.out.println("| [1] Reserve A room                        |");
            System.out.println("| [2] View All Reservation                  |");
            System.out.println("| [3] Get Room Number                       |");
            System.out.println("| [4] Update Reservation                    |");
            System.out.println("| [5] Upgrade Reservation                   |");
            System.out.println("| [6] Delete Reservation                    |");
            System.out.println("| [0] Exit                                  |");
            System.out.println("+-------------------------------------------+");

            System.out.print("Choose an option : ");
            int choice = InputHandler.getIntInput();

            switch (choice){
                case 1:
                    reserveRoom();
                    break;
                case 2:
                    viewReservations();
                    break;
                case 3:
                    getRoomNumber();
                    break;
                case 4:
                    updateReservation();
                    break;
                case 5:
                    upgradeReservation();
                    break;
                case 6:
                    deleteReservation();
                    break;
                case 0:
                    exit();
                    InputHandler.dispose();
                    statement.close();
                    connection.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void reserveRoom() throws SQLException, InterruptedException {
        System.out.print("Enter Guest Name : ");
        String guestName = InputHandler.getLineInput();

        int roomNo = 0;

        while (true){
            System.out.print("Enter room number (1-200): "); // check if room is
            roomNo = InputHandler.getIntInput();
            if(roomNo >= 1 && roomNo <=200 && !isRoomAvailable(roomNo)){
                break;
            }
            System.out.println("Invalid or already booked room number. Please try again.");

        }

        System.out.print("Enter contact number : ");
        String contactNumber = InputHandler.getLineInput();

        if(guestName.isEmpty() || contactNumber.isEmpty()){
            System.out.println("Failed to Reserve A room");
            Thread.sleep(500);
            System.out.println("+-------------------------------------------+");
            System.out.println("|                  CONDITIONS               |");
            System.out.println("+-------------------------------------------+");
            System.out.println("| 1. Name cannot be Empty                   |");
            System.out.println("| 2. Room no. can only be from 1 to 200     |");
            System.out.println("| 3. Contact number cannot be Empty         |");
            System.out.println("+-------------------------------------------+");

            reserveRoom();

        }

        String roomType = getRoomTypeInput();

        String sqlQuery = "INSERT INTO " + TABLENAME + " (guest_name, room_number, contact_number, room_type) VALUES ( '" + guestName + "' , " + roomNo + " , '" + contactNumber +"', '" + roomType  + "');" ;

        int row = statement.executeUpdate(sqlQuery);

        if (row > 0) {
            System.out.println("Reservation successful");
        } else {
            System.out.println("Reservation failed");
        }


    }

    private String getRoomTypeInput(){

        while(true){
            System.out.println("Select room type : ");
            System.out.println("\t[1] Standard");
            System.out.println("\t[2] Deluxe");
            System.out.println("\t[3] Superior");
            System.out.print("Enter Room Type : ");
            int choice = InputHandler.getIntInput();

            System.out.println();
            switch (choice){
                case 1: return "Standard";

                case 2: return "Deluxe";

                case 3: return "Superior";

                default: System.out.println("Invalid option. Please try Again. ");
            }
        }
    }

    private void viewReservations() {
        String sqlQuery = "SELECT * FROM " + TABLENAME + ";";
        statement.executeQuery(sqlQuery);
    }

    private void getRoomNumber() {
        System.out.print("Enter Reservation ID : ");
        int reservationId = InputHandler.getIntInput();
        System.out.print("Enter Guest Name : ");
        String guestName = InputHandler.getLineInput();

        String sqlQuery = "SELECT room_number FROM " + TABLENAME + " WHERE reservation_id = " + reservationId + " AND guest_name = '" + guestName + "';";

        statement.executeQuery(sqlQuery);

    }



    private void updateReservation() throws SQLException, InterruptedException {
        System.out.print("Enter Reservation ID to update : ");
        int reservationId = InputHandler.getIntInput();

        if(!reservationExist(reservationId)){
            System.out.println("Reservation not found for the given ID.");

            while(true){

                System.out.print("Press [y] to create a new Reservation or [n] to no reservation: ");

                char c = InputHandler.getCharInput();
                System.out.println();
                switch (c){
                    case 'y':
                        reserveRoom();
                        return;
                    case 'n':
                        return;
                    default:
                        System.out.println("Invalid Choice. please try Again");
                }
            }
        }

        boolean validChoice = false;
        String columnName = null;
        String newValue = null;

        while(!validChoice){
            System.out.println("+------------    UPDATE RESERVATION    ------------+");
            System.out.println("|    [1] Change Name                               |");
            System.out.println("|    [2] Change Your Room                          |");
            System.out.println("|    [3] Change Contact number                     |");
            System.out.println("|    [0] Go Back                                   |");
            System.out.println("+--------------------------------------------------+");
            System.out.print("Which thing you want to Update : ");

            int choice = InputHandler.getIntInput();


            switch (choice){
                case 0:
                    return;
                case 1:
                    System.out.print("Enter New Guest Name : ");
                    columnName = "guest_name";
                    newValue = InputHandler.getLineInput();
                    validChoice = true;
                    break;
                case 2:
                    changeRoom(reservationId);
                    return;
                case 3:
                    System.out.print("Enter New Number : ");
                    columnName = "contact_number";
                    newValue = InputHandler.getLineInput();
                    validChoice = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try Again. ");
            }
        }

        String sqlQuery = "UPDATE " + TABLENAME + " SET " + columnName + " = '" + newValue + "' WHERE reservation_id = " + reservationId + ";";

        int rowAffected = statement.executeUpdate(sqlQuery);
        if(rowAffected > 0) {
            System.out.println("Reservation updated successfully.");
        } else {
            System.out.println("Failed to update the reservation. Please try again.");
        }

    }

    private void changeRoom(int reservationId) throws SQLException{
        System.out.print("Enter the new Room number :");
        int newRoomNumber = InputHandler.getIntInput();

        String sqlQuery = "UPDATE " + TABLENAME + " SET room_number = " + newRoomNumber + " WHERE reservation_id = " + reservationId + ";";

        int rowsAffected = statement.executeUpdate(sqlQuery);
        if (rowsAffected > 0) {
            System.out.println("Room updated successfully.");
        } else {
            System.out.println("Failed to update the room. Please try again.");
        }


    }


    private void upgradeReservation() throws SQLException, InterruptedException {
        System.out.print("Enter Reservation ID to update : ");
        int reservationId = InputHandler.getIntInput();

        if(!reservationExist(reservationId)){
            System.out.println("Reservation not found for the given ID.");

            while(true){

                System.out.print("Press [y] to create a new Reservation or [n] to no reservation: ");

                char c = InputHandler.getCharInput();
                System.out.println();
                switch (c){
                    case 'y':
                        reserveRoom();
                        return;
                    case 'n':
                        return;
                    default:
                        System.out.println("Invalid Choice. please try Again");
                }
            }
        }

        boolean validChoice = false;
        String columnName = "room_type";
        String newValue = null;

        while(!validChoice){
            System.out.println("+------------   UPGRADE RESERVATION   ------------+");
            System.out.println("|    [1] Upgrade Room to Deluxe                   |");
            System.out.println("|    [2] Upgrade Room to Superior                 |");
            System.out.println("|    [3] Upgrade Room to Standard                 |");
            System.out.println("|    [0] Go Back                                  |");
            System.out.println("+-------------------------------------------------+");
            System.out.print("Where you want to upgrade : ");

            int choice = InputHandler.getIntInput();
            System.out.println();


            switch (choice){
                case 0:
                    return;
                case 1:
                    newValue = "Deluxe";
                    validChoice = true;
                    break;
                case 2:
                    newValue = "Superior";
                    validChoice = true;
                    break;
                case 3:
                    newValue = "Standard";
                    validChoice = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try Again. ");
            }
        }



        String sqlQuery = "UPDATE " + TABLENAME + " SET " + columnName + " = '" + newValue + "' WHERE reservation_id = " + reservationId + ";";

        int rowAffected = statement.executeUpdate(sqlQuery);
        if(rowAffected > 0) {
            System.out.println("Room Upgraded Successfully.");
        } else {
            System.out.println("Failed to update the reservation. Please try again.");
        }

    }






    private boolean reservationExist(int reservationId) {
        String sqlQuery = "SELECT reservation_id FROM " + TABLENAME + " WHERE reservation_id = " + reservationId;

        try{
            return statement.isResultSetNotEmpty(sqlQuery);
        } catch (Exception e) {
            System.err.println("Error checking reservation existence: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void deleteReservation() {
        //check it a reservation exist
        // if true then delete it
        // if not, mean reservation doesn't exist try again
        System.out.print("Enter Reservation ID to Delete : ");
        int reservationId = InputHandler.getIntInput();
//        System.out.println();
        if(!reservationExist(reservationId)){
            System.out.println("Sorry, but no Reservation founded with this reservation Id");
            return;
        }
        String sqlQuery = "DELETE FROM " + TABLENAME + " WHERE reservation_id = " + reservationId;

        int rowAffected = statement.executeUpdate(sqlQuery);
        if (rowAffected > 0) {
            System.out.println("Reservation deleted successfully!");
        } else {
            System.out.println("Reservation deletion failed.");
        }


    }

    private boolean isRoomAvailable(int roomNumber){
        String sqlQuery = "SELECT room_number FROM " + TABLENAME + " WHERE room_number = " + roomNumber;
        try {
            return statement.isResultSetNotEmpty(sqlQuery);
        } catch (SQLException e) {
            System.err.println("Error checking room number : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }



    private void exit() throws InterruptedException, SQLException {
        System.out.println("Are You Sure : ");
        System.out.println("Press [y] to Exit");
        System.out.println("Press any other to return HRMS");
        System.out.print("Enter Your Choice : ");
        char c = InputHandler.getCharInput();

        if(c == 'y'){
            System.out.print("Exiting System");
            for (int i = 5; i > 0 ; i--) {
                System.out.print(".");
                Thread.sleep(500);
            }

            System.out.println();
            System.out.println("ThankYou For Using Hotel Reservation Management System!!!");
            statement.close();
            connection.close();
        }
        else {
            startOperation();
        }
    }
}
*/