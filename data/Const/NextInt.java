package data.Const;

import java.util.Scanner;

import data.Classes.Color;

public class NextInt {
    // Accept the scanner and max value to check for
    public static int nextInt(Scanner scanner, int max){
        // Try to get the next int, catch if its an input mismatch error
        try {
            // Set the value
            int value = scanner.nextInt();
            // If the value is greater than the max, and if the max is not smaller than 0
            // The second part is used so if a negative value is passed for the max it will allways allow it
            if(value > max && !(max <= 0)) {
                // From the color class, run the red print line statement
                Color.redPrintln("Invalid input, value must be between 0-"+Integer.toString(max));
                // Loop the function by calling itself
                return nextInt(scanner, max);
            }
            // Else end the loop and return the value
            else return value;
        // On the mismatch error
         } catch (java.util.InputMismatchException e) {
            // Prompt the error
            Color.redPrintln("Invalid input, please enter a number");
            // Forward the scanner
            scanner.next();
            // Loop the function
            return nextInt(scanner, max);
         }
    }

}
