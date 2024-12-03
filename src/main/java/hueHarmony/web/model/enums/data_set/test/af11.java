package hueHarmony.web.model.enums.data_set.test;

public class af11 {
    public static void main(String[] args) {
        // Print a greeting message
        System.out.println("Hello, welcome to the Dummy Program!");

        // Call a dummy method
        int sum = addNumbers(8, 12);
        System.out.println("The sum of 8 and 12 is: " + sum);

        // Example of a loop
        System.out.println("Printing numbers from 1 to 5:");
        for (int i = 1; i <= 5; i++) {
            System.out.println(i);
        }

        // Example of a conditional
        String message = getStatus(18);
        System.out.println("Status message: " + message);
    }

    // A method to add two numbers
    public static int addNumbers(int a, int b) {
        return a + b;
    }

    // A method to return a status message
    public static String getStatus(int age) {
        if (age >= 18) {
            return "You are an adult.";
        } else {
            return "You are a minor.";
        }
    }
}
