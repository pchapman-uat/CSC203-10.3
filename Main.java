import java.sql.*;
import java.util.Scanner;


import Classes.*;

public class Main {
    public static String[] filterNotes(Scanner scanner){
        scanner.nextLine();
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
                System.out.println(note.dbLine());
                statement.executeUpdate(note.dbLine());
            }
            else if(choice == 2) {
                System.out.println("Deleting a note");
            }
            else if(choice == 3) {
                System.out.println("Updating a note");
            }
            else if(choice == 4) {
                System.out.println("Read a note");
                String[] filters = filterNotes(scanner);
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
                String selectSQL = "SELECT * FROM notes" + where;
                ResultSet resultSet = statement.executeQuery(selectSQL);
                int count = 0;
                while (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("id") + ", Date: " + resultSet.getString("date") + ", Title: " + resultSet.getString("title"));
                    count++;
                }
                if(count == 0) {
                    System.out.println("No notes found");
                } else {
                    System.out.println("Please enter the id of the note you would like to read");
                    int id = scanner.nextInt();
                    if(id < 0 || id > count) {
                        System.out.println("Invalid id");
                    } else {
                        String selectSQL2 = "SELECT * FROM notes WHERE id = " + id;
                        ResultSet resultSet2 = statement.executeQuery(selectSQL2);
                        while (resultSet2.next()) {
                            System.out.println("ID: " + resultSet2.getInt("id") + ", Date: " + resultSet2.getString("date") + ", Title: " + resultSet2.getString("title"));
                            System.out.println("Content: " + resultSet2.getString("content"));
                        }
                    }
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
