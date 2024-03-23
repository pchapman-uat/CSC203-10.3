import java.sql.*;
import java.util.Scanner;


import Classes.*;

public class Main {
    public static String filterNotes(Scanner scanner){
        scanner.nextLine();


        // TODO: Add an option to input ID rather than date
        // TODO: Add option to add formated date (mm/dd/yyyy) 

        // TODO: While loop to allow multiple attempts
        System.out.println("Please enter selection type");
        System.out.println("1. By ID");
        System.out.println("2. Date range");
        System.out.println("3. Specific date");  
        int responce = scanner.nextInt();
        scanner.nextLine();
        if(responce == 1){
            System.out.println("Enter the ID");
            int id = scanner.nextInt();
            return "SELECT * from notes WHERE ID = "+ Integer.toString(id);
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
        // TODO: Intepret input and return a string array of the year, month, and day
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
        if(responce.equals("A")) return new String[] {"*"};
        else year = responce;
        System.out.println("Please enter the month");
        responce = scanner.nextLine();
        if(responce.equals("A")) return new String[] {year, "*"};
        else month = responce;
        System.out.println("Please enter the day");
        responce = scanner.nextLine();
        if(responce.equals("A")) return new String[] {year, month, "*"};
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
        if(id < 0 || id > count) {
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
        return printNotes(statement, selectSQL);
    }
    // TODO: Set a while loop to allow for multiple inputs
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
            System.out.println("What would you like to do>");
            System.out.println("1. Add a note");
            System.out.println("2. Delete a note");
            System.out.println("3. Update a note");
            System.out.println("4. Read a note");

            int choice = scanner.nextInt();
            if(choice == 1) {
                System.out.println("Adding a note");
                Note note = new Note();
                note.createNote(scanner);
                System.out.println("Inserting into database");
                System.out.println(note.dbInsert());
                statement.executeUpdate(note.dbInsert());
            }
            else if(choice == 2) {
                int count = ineractGetNote(scanner, statement);
                if(count > 0){
                    System.out.println("Deleting a note");
                    Note note = selectNote(scanner, count, statement);
                    System.out.println("Deleting from database");
                    System.out.println(note.dbDelete());
                    statement.executeUpdate(note.dbDelete());
                }

            }   
            else if(choice == 3) {
                System.out.println("Updating a note");
                int count = ineractGetNote(scanner, statement);
                if(count == 0) {
                    System.out.println("No notes found");
                } else {
                    // TODO: Add a while loop to allow for multiple inputs
                    Note note = selectNote(scanner, count, statement);

                    System.out.println("What would you like to do?");
                    System.out.println("1. Change title");
                    System.out.println("2. Change content");
                    System.out.println("3. Change date");
                    int changeChoice = scanner.nextInt();

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
                        System.out.println("Please enter the new day");
                        int day = scanner.nextInt();
                        System.out.println("Please enter the new month");
                        int month = scanner.nextInt();
                        System.out.println("Please enter the new year");
                        int year = scanner.nextInt();
                        note.setDate(day, month, year);
                    }

                    System.out.println("Updating database");
                    System.out.println(note.dbUpdate());
                    statement.executeUpdate(note.dbUpdate());
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
            // Retrieve data

            
         
            // Display data

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
