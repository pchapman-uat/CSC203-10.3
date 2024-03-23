package Classes;
public class Date {
    public int day;
    public int month;
    public int year;
    public String date;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.date = this.day + "/" + this.month + "/" + this.year;
    }
}
