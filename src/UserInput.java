import java.util.Scanner;

public class UserInput {
    private static Scanner scnr = new Scanner(System.in);
    public static int getInt(String prompt, int inclusiveLowerBound, int inclusiveUpperBound) {
        while (true) {
            System.out.print(prompt);
            String entry = scnr.next();
            try {
                int response = Integer.parseInt(entry);
                if (response < inclusiveLowerBound || response > inclusiveUpperBound) {
                    System.out.println("Out of bounds response. Must be between " + inclusiveLowerBound + " and " + inclusiveUpperBound + " inclusive.");
                }
                else return response;
            } catch (Exception e) {
                System.out.println("Invalid input: must be an integer. Try again.");
            }
        }
    }
    public static int getInt(String prompt, int inclusiveLowerBound) {
        while (true) {
            System.out.print(prompt);
            String entry = scnr.next();
            try {
                int response = Integer.parseInt(entry);
                if (response < inclusiveLowerBound) {
                    System.out.println("Out of bounds response. Must be greater than or equal to " + inclusiveLowerBound);
                }
                else return response;
            } catch (Exception e) {
                System.out.println("Invalid input: must be an integer. Try again.");
            }
        }
    }
    public static int getInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String entry = scnr.next();
            try {
                return Integer.parseInt(entry);
            } catch (Exception e) {
                System.out.println("Invalid input: must be an integer. Try again.");
            }
        }
    }
}
