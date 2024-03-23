// Package the note with the classes for the project
// Everything in the folder is already imported
package Classes;
// Import the scanner library, this is only used to have a object type for inputs for methods
import java.util.Scanner;

import Const.getInt;

public class Date {
    // Declare the variables
    public int day;
    public int month;
    public int year;
    public String dateString;

    // Using intagers, set the date as well as the date string
    public void intDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        // NOTE: Year is not formated, meaning the year is not always 4 digits long
        this.year = year;
        this.dateString = this.day + "/" + this.month + "/" + this.year;
    }
    
    // Using strings, set the date as well as the date string
    public void stringDate(String dateString) {
        // Split the array for each "/", and convert to integers
        String[] dateArr = dateString.split("/");
        this.month = Integer.parseInt(dateArr[0]);
        this.day = Integer.parseInt(dateArr[1]);
        this.year = Integer.parseInt(dateArr[2]);
        this.dateString = this.day + "/" + this.month + "/" + this.year;
    }

    // Using the scanner have an interation for the user to input the date
    public void interactCreateDate(Scanner scanner){
        // Ask the user for the type of date they want to enter
        System.out.println("==Enter the date==");
        System.out.println("1. Formated Date (mm/dd/yyyy)");
        System.out.println("2. Date numbers (m,d,y)");
        int responce = scanner.nextInt();
        // If the user selected formated date, ask for the formated date
        if(responce == 1){
            // The scanner is called due to a bug that is not saving the scanner
            scanner.nextLine();
            System.out.println("Enter the date (mm/dd/yyyy)");
            // Get the date string from the scanner
            String dateString = scanner.nextLine();
            // Parse the date string and set the date
            this.stringDate(dateString);
        } else if(responce == 2){
            // If the user selected date numbers, ask for the date numbers
           this.interactDate(scanner);
        } else {
            // If the user selected an invalid option, print an error message
            System.out.println("Not a valid option");
        }
    }
    // Using the scanner have an interation for the user to input the date on number at a time
    public void interactDate(Scanner scanner){
        // Ask the user for the date numbers (Day, Month, Year)
        System.out.println("Day (Number)");
        int day = getInt.nextInt(scanner, -1);
        System.out.println("Month (Number)");
        int month = getInt.nextInt(scanner, -1);
        System.out.println("Year (Number)");
        int year = getInt.nextInt(scanner, -1);
        // Set the date using the integers
        this.intDate(day, month, year);
    }
}
