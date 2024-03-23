// Package the note with the classes for the project
// Everything in the folder is already imported
package Classes;

// Import the scanner library, this is only used to have a object type for inputs for methods
import java.util.Scanner;

public class Note {
    // Declare the variables
    public String title;
    public String content;

    public Date date;

    public int id;

    // Setter and getters are created for each variable, however this is not necessary
    
    // Set the variable, and return the value
    public String setTitle(String title) {
        this.title = title;
        return title;
    }
    public String setContent(String content) {
        this.content = content;
        return content;
    }
    public int setID(int id) {
        this.id = id;
        return id;
    }
    public Date setDate(int day, int month, int year){
        this.date = new Date();
        this.date.intDate(day, month, year);
        return this.date;
    }

    // Get the variable
    public Date getDate(){
        return this.date;
    }

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public int getID() {
        return id;
    }

    // Print the note
    public String printNote(){
        return "===" + this.title + "===" + "\n" + "Date: " + this.date.dateString + "| ID:" +this.id + "\n" + this.content + "\n";
    }

    // Create a while interacting with the user via the scanner
    public void createNote(Scanner scanner){
        scanner.nextLine();
        System.out.println("Enter the title");
        String title = scanner.nextLine();
        System.out.println("Enter the content");
        String content = scanner.nextLine();
        // Create a new date object
        this.date = new Date();
        // Interact with the user to create a date
        this.date.interactCreateDate(scanner);
        // Set the variables
        this.setContent(content);
        this.setTitle(title);
    }
    // These are database related methods that generate SQL statements
    public String dbInsert(){
        return "INSERT INTO notes (title, content, date, day, month, year) VALUES ('"+ this.title + "', '" + this.content + "', '" + this.date.dateString + "'," + Integer.toString(this.date.day) + ", " + Integer.toString(this.date.month) + ", " + Integer.toString(this.date.year) + ")";
    }
    public String dbUpdate(){
        return "UPDATE notes SET title = '" + this.title + "', content = '" + this.content + "', date = '" + this.date.dateString + "', day = " + Integer.toString(this.date.day) + ", month = " + Integer.toString(this.date.month) + ", year = " + Integer.toString(this.date.year) + " WHERE id = " + Integer.toString(this.id);
    }
    public String dbDelete(){
        return "DELETE FROM notes WHERE id = " + Integer.toString(this.id);
    }
}
