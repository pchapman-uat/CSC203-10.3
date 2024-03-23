package Classes;
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
        this.dateString = dateString;
        this.day = Integer.parseInt(this.dateString.split("/")[0]);
        this.month = Integer.parseInt(this.dateString.split("/")[1]);
        this.year = Integer.parseInt(this.dateString.split("/")[2]);
    }
}
