import java.sql.*;
import java.util.Scanner;


import Classes.*;

public class Main {
    public static void printArr(String[] array){
        for(String message : array){
            System.out.println(message);
        }
    }
    public static String filterNotes(Scanner scanner){
        scanner.nextLine();
        String[] filterTypes = new String[] {
            "Please enter selection type", 
            "1. By ID", 
            "2. Date range", 
            "3. Specific date",
            "4. Exit"
        };
        printArr(filterTypes);
        int responce = scanner.nextInt();
        scanner.nextLine();
        if(responce == 1){
            System.out.println("Enter the ID");
            int id = scanner.nextInt();
            return "SELECT * from notes WHERE id = "+ Integer.toString(id);
        } else if (responce == 2){
            String[] filter = selectDateRange(scanner);
            return selectFromFilter(filter);
        } else if (responce == 3){
            String[] filter = selectSpecificDate(scanner);
            return selectFromFilter(filter);
        } else {
            System.out.println("Not a valid option");
            return null;
        }
    }

    public static String[] selectSpecificDate(Scanner scanner){
        System.out.println("Please enter the date (mm/dd/yyyy)");
        String date = scanner.nextLine();
        int month = Integer.parseInt(date.split("/")[0]);
        int day = Integer.parseInt(date.split("/")[1]);
        int year = Integer.parseInt(date.split("/")[2]);
        return new String[] {Integer.toString(year), Integer.toString(month), Integer.toString(day)};
    }

    public static String[] selectDateRange(Scanner scanner){
        String month;
        String day;
        String year;

        System.out.println("Please enter the year (A for all)");
        String responce = scanner.nextLine();
        if(responce.equals("A")) return new String[] {};
        else year = responce;
        System.out.println("Please enter the month  (A for all)");
        responce = scanner.nextLine();
        if(responce.equals("A")) return new String[] {year};
        else month = responce;
        System.out.println("Please enter the day  (A for all)");
        responce = scanner.nextLine();
        if(responce.equals("A")) return new String[] {year, month};
        else day = responce;
        return new String[] {year, month, day};
    }
    public static String selectFromFilter(String[] filters){
        String where = "";
        for(int i =0; i < filters.length; i++) {
            String source = "";
            if(i == 0) source = "year";
            else if(i == 1) source = "month";
            else source = "day";
            where += source + "=" +filters[i];
            if(i!= filters.length - 1) where += " AND ";
        }
        if(!where.equals("")) where = " WHERE " + where;
        System.out.println("SELECT * FROM notes" + where);
        return "SELECT * FROM notes" + where;
    }

    public static int printNotes(Statement statement, String selectSQL) throws SQLException{
        ResultSet resultSet = statement.executeQuery(selectSQL);
        int count = 0;
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id") + ", Date: " + resultSet.getString("date") + ", Title: " + resultSet.getString("title"));
            count++;
        }
        return count;
    }

    public static Note selectNote(Scanner scanner,int count, Statement statement) throws SQLException{
        System.out.println("Please enter the id of the note you would like to select");
        int id = scanner.nextInt();
        if(id < 0) {
            System.out.println("Invalid id");
        } else {
            String selectSQL2 = "SELECT * FROM notes WHERE id = " + id;
            ResultSet resultSet2 = statement.executeQuery(selectSQL2);
            while (resultSet2.next()) {
                Note note = new Note();
                note.setContent(resultSet2.getString("content"));
                note.setTitle(resultSet2.getString("title"));
                note.setDate(resultSet2.getInt("day"), resultSet2.getInt("month"), resultSet2.getInt("year"));
                note.setID(resultSet2.getInt("id"));
                System.out.println(note.printNote());
                return note;
            }
        }
        return null;
    }

    public static int ineractGetNote(Scanner scanner, Statement statement) throws SQLException{
        String selectSQL = filterNotes(scanner);
        if(selectSQL != null) return printNotes(statement, selectSQL);
        else return 0;
        
    }
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        
        try {
            // Connect to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:db/data.sqlite3");
            
            // Create a table
            statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, title TEXT, content TEXT, date TEXT, day INT, month INT, year INT)";
            statement.executeUpdate(createTableSQL);
            
            Scanner scanner = new Scanner(System.in);
            
            String[] intro = new String[]{
                "\n\n",
                "\t\tWelcome to the note manager!",
                "In this program you can add notes, delete notes, update notes, and read notes.",
                "Push enter when you are ready"
            };
            printArr(intro);
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
            while(choice != 5){
                printArr(mainOptions);
                choice = scanner.nextInt();
                System.out.println("\n\n");
                if(choice == 1) {
                    System.out.println("Adding a note");
                    Note note = new Note();
                    note.createNote(scanner);
                    System.out.println("Inserting into database");
                    statement.executeUpdate(note.dbInsert());
                }
                else if(choice == 2) {
                    int count = ineractGetNote(scanner, statement);
                    if(count > 0){
                        System.out.println("Deleting a note");
                        Note note = selectNote(scanner, count, statement);
                        System.out.println("Deleting from database");
                        statement.executeUpdate(note.dbDelete());
                    }
    
                }   
                else if(choice == 3) {
                    System.out.println("Updating a note");
                    int count = ineractGetNote(scanner, statement);
                    if(count == 0) {
                        System.out.println("No notes found");
                    } else {
                        Note note = selectNote(scanner, count, statement);
                        if(note != null){
                            int changeChoice = 0;
                            String[] choices = new String[] {
                                "What would you like to change?",
                                "1. Title",
                                "2. Content",
                                "3. Date",
                                "4. Exit"
                            };
                            while (changeChoice != 4) {
                                printArr(choices);
                                changeChoice = scanner.nextInt();   
                                // NOTE: Due to a scanner bug, a scan is made now
                                scanner.nextLine();
                                if(changeChoice == 1){
                                    System.out.println("Changing title");
                                    System.out.println("Please enter the new title");
                                    String change = scanner.nextLine();
                                    note.setTitle(change);
                                }
                                else if(changeChoice == 2){
                                    System.out.println("Changing content");
                                    System.out.println("Please enter the new content");
                                    String change = scanner.nextLine();
                                    note.setContent(change);
                                }
                                else if(changeChoice == 3){
                                    System.out.println("Changing date");
                                    System.out.println("Please enter the new date");

                                    note.interactDate(scanner);
                                }
            
                                System.out.println("Updating database");
                                statement.executeUpdate(note.dbUpdate());
                            }
        
                        } else {
                            System.out.println("Not a valid note");
                        }
                    }
                }
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
        scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
