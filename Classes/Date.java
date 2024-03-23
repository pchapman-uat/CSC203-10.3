package Classes;

import java.util.Scanner;

public class Date {
    public int day;
    public int month;
    public int year;
    public String dateString;

    public void intDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.dateString = this.day + "/" + this.month + "/" + this.year;
    }
    
    public void stringDate(String dateString) {
        this.month = Integer.parseInt(dateString.split("/")[0]);
        this.day = Integer.parseInt(dateString.split("/")[1]);
        this.year = Integer.parseInt(dateString.split("/")[2]);
        this.dateString = this.day + "/" + this.month + "/" + this.year;
    }

    public void interactCreateDate(Scanner scanner){
        System.out.println("==Enter the date==");
        System.out.println("1. Format Date (mm/dd/yyyy)");
        System.out.println("2. Date numbers");
        int responce = scanner.nextInt();
        if(responce == 1){
            scanner.nextLine();
            System.out.println("Enter the date (mm/dd/yyyy)");
            String dateString = scanner.nextLine();
            this.stringDate(dateString);
        } else if(responce == 2){
           interactDate(scanner);
        } else {
            System.out.println("Not a valid option");
        }
    }
    public void interactDate(Scanner scanner){
        System.out.println("Day (Number)");
        int day = scanner.nextInt();
        System.out.println("Month (Number)");
        int month = scanner.nextInt();
        System.out.println("Year (Number)");
        int year = scanner.nextInt();
        this.intDate(day, month, year);
    }
}
