package Classes;

import java.util.Scanner;

public class Note {
    public String title;
    public String content;

    public Date date;

    public int id;

    public String setTitle(String title) {
        this.title = title;
        return title;
    }
    public String setContent(String content) {
        this.content = content;
        return content;
    }

    public Date getDate(){
        return this.date;
    }

    public Date setDate(int day, int month, int year){
        this.date = new Date(day, month, year);
        return this.date;
    }
    public int setID(int id) {
        this.id = id;
        return id;
    }

    public String printNote(){
        return "===" + this.title + "===" + "\n" + "Date: " + this.date + "| ID:" +this.id + "\n" + this.content + "\n";
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
        this.date = new Date(day, month, year);
        this.setTitle(title);
    }
    public String dbInsert(){
        return "INSERT INTO notes (title, content, date, day, month, year) VALUES ('"+ this.title + "', '" + this.content + "', '" + this.date + "'," + Integer.toString(this.date.day) + ", " + Integer.toString(this.date.month) + ", " + Integer.toString(this.date.year) + ")";
    }
    public String dbUpdate(){
        return "UPDATE notes SET title = '" + this.title + "', content = '" + this.content + "', date = '" + this.date + "', day = " + Integer.toString(this.date.day) + ", month = " + Integer.toString(this.date.month) + ", year = " + Integer.toString(this.date.year) + " WHERE id = " + Integer.toString(this.id);
    }

    public String dbDelete(){
        return "DELETE FROM notes WHERE id = " + Integer.toString(this.id);
    }
}
