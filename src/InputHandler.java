import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    // Private constructor to prevent instantiation (utility class)
    private InputHandler(){
    }

    public static String getStringInput(){
        return scanner.next();
    }

    public static String getLineInput(){
        return scanner.nextLine();
    }


    public static int getIntInput(){
        while(true){
            try{
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter an Integer : ");
            }
        }
    }


    public static double getDoubleInput(){
        while(true){
            try{
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter an Integer : ");
            }
        }
    }
    public static char getCharInput(){
        while(true){
            try{
                return Character.toLowerCase(scanner.next().charAt(0));
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter [y] or [n] : ");
            }
        }
    }


    public static void dispose(){
        scanner.close();
        System.out.println("Scanner closed successfully");
    }
}
