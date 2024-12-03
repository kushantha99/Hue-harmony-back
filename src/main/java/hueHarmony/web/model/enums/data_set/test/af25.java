package hueHarmony.web.model.enums.data_set.test;

public class af25 {

        public static void main(String[] args) {
            // Greeting message
            System.out.println("Welcome to the Dummy Large Program!");
            System.out.println("This program is designed to span approximately 500 lines.");
            System.out.println("Enjoy exploring this artificially inflated code!");
            System.out.println("-----------------------------------------------------");

            // Section 1: Arithmetic operations
            performArithmeticOperations();

            // Section 2: Loops demonstration
            demonstrateLoops();

            // Section 3: Conditional demonstration
            demonstrateConditionals();

            // Section 4: Array processing
            demonstrateArrayProcessing();

            // Section 5: String manipulation
            demonstrateStringManipulation();

            // End of the program
            System.out.println("Thank you for using the Dummy Large Program!");
            System.out.println("-----------------------------------------------------");
        }

        // Section 1: Arithmetic operations
        public static void performArithmeticOperations() {
            System.out.println("Performing arithmetic operations...");

            int a = 10;
            int b = 5;

            int addition = a + b;
            System.out.println("Addition (10 + 5): " + addition);

            int subtraction = a - b;
            System.out.println("Subtraction (10 - 5): " + subtraction);

            int multiplication = a * b;
            System.out.println("Multiplication (10 * 5): " + multiplication);

            int division = a / b;
            System.out.println("Division (10 / 5): " + division);

            System.out.println("-----------------------------------------------------");
        }

        // Section 2: Loops demonstration
        public static void demonstrateLoops() {
            System.out.println("Demonstrating loops...");

            // For loop
            System.out.println("For loop (1 to 10):");
            for (int i = 1; i <= 10; i++) {
                System.out.println("Number: " + i);
            }

            // While loop
            System.out.println("While loop (10 to 1):");
            int j = 10;
            while (j >= 1) {
                System.out.println("Number: " + j);
                j--;
            }

            // Do-while loop
            System.out.println("Do-while loop (1 to 5):");
            int k = 1;
            do {
                System.out.println("Number: " + k);
                k++;
            } while (k <= 5);

            System.out.println("-----------------------------------------------------");
        }

        // Section 3: Conditional demonstration
        public static void demonstrateConditionals() {
            System.out.println("Demonstrating conditionals...");

            int age = 20;

            if (age >= 18) {
                System.out.println("Age " + age + ": Adult");
            } else {
                System.out.println("Age " + age + ": Minor");
            }

            int number = 15;

            if (number % 2 == 0) {
                System.out.println("Number " + number + ": Even");
            } else {
                System.out.println("Number " + number + ": Odd");
            }

            System.out.println("-----------------------------------------------------");
        }

        // Section 4: Array processing
        public static void demonstrateArrayProcessing() {
            System.out.println("Demonstrating array processing...");

            int[] numbers = {1, 2, 3, 4, 5};

            System.out.println("Array elements:");
            for (int number : numbers) {
                System.out.println("Number: " + number);
            }

            int sum = 0;
            for (int number : numbers) {
                sum += number;
            }
            System.out.println("Sum of array elements: " + sum);

            System.out.println("-----------------------------------------------------");
        }

        // Section 5: String manipulation
        public static void demonstrateStringManipulation() {
            System.out.println("Demonstrating string manipulation...");

            String original = "Hello, World!";
            System.out.println("Original string: " + original);

            String upperCase = original.toUpperCase();
            System.out.println("Uppercase: " + upperCase);

            String lowerCase = original.toLowerCase();
            System.out.println("Lowercase: " + lowerCase);

            int length = original.length();
            System.out.println("Length: " + length);

            String substring = original.substring(0, 5);
            System.out.println("Substring (0 to 5): " + substring);

            System.out.println("-----------------------------------------------------");
        }


}
