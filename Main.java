// import the sql library
import static data.Const.NextInt.nextInt;

import java.sql.*;
// import the scanner library
import java.util.Scanner;

import data.Classes.Color;
import data.Classes.Date;
import data.Classes.Note;

public class Main {
    // Using an array of strings, print all one at a time
    public static void printArr(String[] array){
        for(String message : array){
            System.out.println(message);
        }
    }
    // filter notes by id, date range, or specific date
    public static String filterNotes(Scanner scanner){
        // Scanner is called, but not saved due to a bug with the scanner
        scanner.nextLine();
        // Print all the filter options
        String[] filterTypes = new String[] {
            "Please enter selection type", 
            "1. By ID", 
            "2. Date range", 
            "3. Specific date",
            "4. Exit"
        };
        printArr(filterTypes);
        // Get the user's selection

        int responce = nextInt(scanner, 4);
        
        
        scanner.nextLine();

        // If the user selected by ID, ask for the ID
        if(responce == 1){
            System.out.println("Enter the ID");
            int id = nextInt(scanner, -1);
            // Return the SQL statement to filter by ID
            return "SELECT * from notes WHERE id = "+ Integer.toString(id);
        } else if (responce == 2){
            // If the user selected by date range, ask for the date range
            String[] filter = selectDateRange(scanner);
            // Using the created array, return the SQL statement to filter by date range
            return selectFromFilter(filter);
        } else if (responce == 3){
            // If the user selected by specific date, ask for the specific date
            String[] filter = selectSpecificDate(scanner);
            // Using the created array, return the SQL statement to filter by specific date
            return selectFromFilter(filter);
        // If the user selected to exit, return null
        } else if(responce == 4){
            return null;
        // If the user selected an invalid option, return null
        }else {
            System.out.println("Not a valid option");
            return null;
        }
    }

    // Using the scanner, prompt the user to enter a specific date
    public static String[] selectSpecificDate(Scanner scanner){
        System.out.println("Please enter the date (mm/dd/yyyy)");
        // Create a new date object
        Date date = new Date();
        // Get the user's input and save it to the date object by calling  the string parsing method
        date.stringDate(scanner.nextLine());
        // Return the array of strings containing the year, month, and day
        return new String[] {Integer.toString(date.year), Integer.toString(date.month), Integer.toString(date.day)};
    }

    // Using the scanner, prompt the user to enter a date range
    public static String[] selectDateRange(Scanner scanner){
        String month;
        String day;
        String year;

        // Ask the user for the year, if user inputs A, return an empty array signifying all years, else save the year
        System.out.println("Please enter the year (A for all)");
        String responce = scanner.nextLine();
        if(responce.equals("A")) return new String[] {};
        else year = responce;
        // Ask the user for the month, if user inputs A, return an array with the year signifying all months for that year, else save the month
        System.out.println("Please enter the month  (A for all)");
        responce = scanner.nextLine();
        if(responce.equals("A")) return new String[] {year};
        else month = responce;
        // Ask the user for the day, if user inputs A, return an array with year and month signifying all days for that month and year, else save the day
        System.out.println("Please enter the day  (A for all)");
        responce = scanner.nextLine();
        if(responce.equals("A")) return new String[] {year, month};
        else day = responce;
        // Return the array of strings containing the year, month, and day
        // This return will only run if the user enters a valid year, month, and day
        return new String[] {year, month, day};
    }

    // Using the array of strings, return the SQL statement to filter by date range
    public static String selectFromFilter(String[] filters){
        String where = "";
        // Loop through the array of strings and create the SQL statement
        for(int i =0; i < filters.length; i++) {
            String source = "";
            // Set the source based on the index of the string in the array
            if(i == 0) source = "year";
            else if(i == 1) source = "month";
            else source = "day";
            // Add the source and the filter to the where statement
            where += source + "=" +filters[i];
            // Add the AND statement to the where statement, if the index is not the last index in the array
            if(i!= filters.length - 1) where += " AND ";
        }
        // If the where statement is not empty, add the WHERE statement to the SQL statement
        if(!where.equals("")) where = " WHERE " + where;
        // Return the SQL statement to filter by date range
        return "SELECT * FROM notes" + where;
    }

    // Query the database for notes based on the SQL statement
    public static int printNotes(Statement statement, String selectSQL) throws SQLException{
        // Execute the SQL statement
        ResultSet resultSet = statement.executeQuery(selectSQL);
        // Loop through the results and print them
        int count = 0;
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id") + ", Date: " + resultSet.getString("date") + ", Title: " + resultSet.getString("title"));
            count++;
        }
        // Return the number of results
        return count;
    }

    // Query the database for notes based on the SQL statement
    public static Note selectNote(Scanner scanner,int count, Statement statement) throws SQLException{
        // Ask the user for the id of the note they would like to select
        // If the user enters a negative number, return null
        System.out.println("Please enter the id of the note you would like to select");
        int id = nextInt(scanner, -1);
        // Create the SQL statement to select the note by id
        String selectSQL2 = "SELECT * FROM notes WHERE id = " + id;
        ResultSet resultSet2 = statement.executeQuery(selectSQL2);
        // Loop through the results and print them
        // This only happens once as there is a singular result for each ID, or there is no result
        while (resultSet2.next()) {
            // Create a new note object
            Note note = new Note();
            // Set the note's properties based on the results of the SQL statement
            note.setContent(resultSet2.getString("content"));
            note.setTitle(resultSet2.getString("title"));
            note.setDate(resultSet2.getInt("day"), resultSet2.getInt("month"), resultSet2.getInt("year"));
            note.setID(resultSet2.getInt("id"));
            note.setColor(resultSet2.getString("color"));
            // Display the note information
            System.out.println(note.printNote());
            // Return the note object
            return note;
        }
        return null;
    }

    // Handle the interations for getting a note
    public static int ineractGetNote(Scanner scanner, Statement statement) throws SQLException{
        // Ask the user for the filter type, and return the SQL statement to filter by the filter type
        String selectSQL = filterNotes(scanner);
        // If the SQL statement is not null, print the notes based on the SQL statement
        if(selectSQL != null) return printNotes(statement, selectSQL);
        else return 0;
        
    }
    // The main method will run on start
    public static void main(String[] args) {
        // Create a connection to the database
        // These are null until the connection is made
        Connection connection = null;
        Statement statement = null;
        
        // All code it in the try block so that the connection will be closed if there is an error
        try {
            // Connect to SQLite database
            // The driver is located in lib folder
            // The database is located in the db folder
            // To use the driver it must be specified in settings.json in vscode
            connection = DriverManager.getConnection("jdbc:sqlite:data/db/data.sqlite3");
            
            // Create the statement object for interactions with the database
            statement = connection.createStatement();
            // Create the table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, title TEXT, content TEXT, date TEXT, day INT, month INT, year INT, color TEXT DEFAULT WHITE)";
            // Execute the SQL statement to create the table
            statement.executeUpdate(createTableSQL);
            
            // Create a scanner object
            Scanner scanner = new Scanner(System.in);
            // Print the introductory text
            String[] intro = new String[]{
                "\n\n",
                Color.blue("\t\tWelcome to the note manager!"), //This generates a blue colored message
                "In this program you can add notes, delete notes, update notes, and read notes.",
                "Push enter when you are ready"
            };
            printArr(intro);
            // Wait for the user to press enter, then display the options
            scanner.nextLine();
            System.out.println("\n\n");
            String[] mainOptions = new String[] { 
                "What would you like to do?", 
                "1. Add a note", 
                "2. Delete a note", 
                "3. Update a note", 
                "4. Read a note",
                "5. Exit"
            };
            int choice = 0;
            // While the user does not choose to exit, keep looping through the options
            while(choice != 5){
                // Print the options
                printArr(mainOptions);
                // Get the user's choice
                choice = nextInt(scanner, 5);
                System.out.println("\n\n");
                // If use chooses to add a note, create a new note object, then insert it into the database
                if(choice == 1) {
                    System.out.println("Adding a note");
                    Note note = new Note();
                    // Run the interation for creating a note object
                    note.createNote(scanner);
                    System.out.println("Inserting into database");
                    // Insert the note into the database
                    // dbInsert() returns the SQL statement to insert the note into the database
                    statement.executeUpdate(note.dbInsert());
                }
                // If the user chooses to delete a note, get the note object, then delete it from the database
                else if(choice == 2) {
                    // query for the number of notes that match the filter
                    int count = ineractGetNote(scanner, statement);
                    // If there are notes, select one to delete
                    if(count > 0){
                        System.out.println("Deleting a note");
                        // Create the note object based on the user's input
                        Note note = selectNote(scanner, count, statement);
                        // Run the delete SQL statement from the note object
                        System.out.println("Deleting from database");
                        // dbDelete() returns the SQL statement to delete the note from the database
                        statement.executeUpdate(note.dbDelete());
                    } else {
                        // Dispaly that no notes where found that match the filter
                        System.out.println("No notes found");
                    }
    
                }   
                // If the user chooses to update a note, get the note object, then update it in the database
                else if(choice == 3) {
                    System.out.println("Updating a note");
                    // query for the number of notes that match the filter by the user
                    int count = ineractGetNote(scanner, statement);
                    // If there are notes, select one to update
                    if(count == 0) {
                        System.out.println("No notes found");
                    } else {
                        // Create the note object based on the user's input
                        Note note = selectNote(scanner, count, statement);
                        // Check if the note object is not null
                        if(note != null){
                            // Display the update changes that can be done
                            int changeChoice = -1;
                            String[] choices = new String[] {
                                "What would you like to change?",
                                "1. Title",
                                "2. Content",
                                "3. Date",
                                "4. Color",
                                "0. Exit"
                            };
                            // While the user does not choose to exit, keep looping through the options
                            while (changeChoice != 0) {
                                // Display the options
                                printArr(choices);
                                // Get the user's choice
                                changeChoice = nextInt(scanner, 4);  
                                // NOTE: Due to a scanner bug, a scan is made now
                                scanner.nextLine();
                                // If the user chooses to change the title, update the title in the note object
                                if(changeChoice == 1){
                                    System.out.println("Changing title");
                                    System.out.println("Please enter the new title");
                                    String change = scanner.nextLine();
                                    note.setTitle(change);
                                }
                                // If the user chooses to change the content, update the content in the note object
                                else if(changeChoice == 2){
                                    System.out.println("Changing content");
                                    System.out.println("Please enter the new content");
                                    String change = scanner.nextLine();
                                    note.setContent(change);
                                }
                                // If the user chooses to change the date, update the date in the note object
                                else if(changeChoice == 3){
                                    System.out.println("Changing date");
                                    // Run the interact date method to get the new date
                                    note.date.interactCreateDate(scanner);
                                }
                                else if(changeChoice == 4){
                                    System.out.println("Changing color");
                                    note.interactColor(scanner);
                                }
                                // Update the database with the new changes
                                System.out.println("Updating database");
                                statement.executeUpdate(note.dbUpdate());
                            }
                        // If the user a non valid number, display an error message
                        } else {
                            System.out.println("Not a valid note");
                        }
                    }
                }
                // If the user chooses to read a note run the getNote method to display the note, otherwise display an error message
                else if(choice == 4) {
                    System.out.println("Read a note");
                    int count = ineractGetNote(scanner, statement);
                    if(count == 0) {
                        System.out.println("No notes found");
                    } else {
                        selectNote(scanner, count, statement);
                    }
                }
            }
        // Close the scanner when the user chooses to exit
        scanner.close();
        // If there is an SQL exception, display the error message
        } catch (SQLException e) {
            e.printStackTrace();
        // When the code finishes close the connection and statement
        } finally {
            // Close resources
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
