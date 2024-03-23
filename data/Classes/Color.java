package data.Classes;
import static data.Const.ColorsVals.*;

public class Color {
    public static String genLine(String color, String message){
        return color+message+ANSI_RESET;
    }
    public static String red(String message) {
        return genLine(ANSI_RED, message);
    }

    public static String black(String message) {
        return genLine(ANSI_BLACK, message);
    }

    public static String green(String message) {
        return genLine(ANSI_GREEN, message);
    }

    public static String yellow(String message) {
        return genLine(ANSI_YELLOW, message);
    }

    public static String blue(String message) {
        return genLine(ANSI_BLUE, message);
    }

    public static String purple(String message) {
        return genLine(ANSI_PURPLE, message);
    }

    public static String cyan(String message) {
        return genLine(ANSI_CYAN, message);
    }

    public static String white(String message) {
        return genLine(ANSI_WHITE, message);
    }

    public static void redPrintln(String message) {
        printLine(ANSI_RED, message);
    }

    public static void blackPrintln(String message) {
        printLine(ANSI_BLACK, message);
    }

    public static void greenPrintln(String message) {
        printLine(ANSI_GREEN, message);
    }

    public static void yellowPrintln(String message) {
        printLine(ANSI_YELLOW, message);
    }

    public static void bluePrintln(String message) {
        printLine(ANSI_BLUE, message);
    }

    public static void purplePrintln(String message) {
        printLine(ANSI_PURPLE, message);
    }

    public static void cyanPrintln(String message) {
        printLine(ANSI_CYAN, message);
    }

    public static void whitePrintln(String message) {
        printLine(ANSI_WHITE, message);
    }

    private static void printLine(String color, String message) {
        System.out.println(color + message + ANSI_RESET);
    }
}
