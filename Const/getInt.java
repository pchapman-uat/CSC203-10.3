package Const;

import java.util.Scanner;

import Classes.Color;

public class getInt {
    // TODO: Comment
    public static int nextInt(Scanner scanner, int max){
        try {
            int value = scanner.nextInt();
            if(value > max && !(max <= 0)) {
                Color.redPrintln("Invalid input, value must be between 0-"+Integer.toString(max));
                return nextInt(scanner, max);
            }
            else return value;
         } catch (java.util.InputMismatchException e) {
             Color.redPrintln("Invalid input, please enter a number");
             scanner.next();
             return nextInt(scanner, max);
         }
    }

}
