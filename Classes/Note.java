package Classes;

import java.util.Scanner;

public class Note {
    public String title;
    public String content;

    private int day;
    private int month;
    private int year;

    public String date;

    public String setTitle(String title) {
        this.title = title;
        return title;
    }
    public String setContent(String content) {
        this.content = content;
        return content;
    }

    private String setDate(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = this.day + "/" + this.month + "/" + this.year;
        return this.date;
    }

    public void createNote(Scanner scanner){
        scanner.nextLine();
        System.out.println("Enter the title");
        String title = scanner.nextLine();
        System.out.println("Enter the content");
        String content = scanner.nextLine();
        System.out.println("==Enter the date==");
        System.out.println("Month (Number)");
        int month = scanner.nextInt();
        System.out.println("Day (Number)");
        int day = scanner.nextInt();
        System.out.println("Year (Number)");
        int year = scanner.nextInt();

        
        this.setContent(content);
        this.setDate(day, month, year);
        this.setTitle(title);
    }
    public String dbLine(){
        return "INSERT INTO notes (title, content, date, day, month, year) VALUES ('"+ this.title + "', '" + this.content + "', '" + this.date + "'," + Integer.toString(this.day) + ", " + Integer.toString(this.month) + ", " + Integer.toString(this.year) + ")";
    }
}
