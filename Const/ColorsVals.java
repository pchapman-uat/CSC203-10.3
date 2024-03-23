package Const;

// Hasmap is used like a dictonary
// It has a key, and a value
import java.util.HashMap;
import java.util.Map;

public class ColorsVals {
    // These are constant values, meaning they will not change
    // These strings are treated as a command in the terminal, changing the color of all text following it
    // ANSI reset will reset the value back to the normal for the users display
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // Add all of the valid colors as part of the colorMap
    public static final Map<String, String> colorMap = new HashMap<>();
        static {
            colorMap.put("RED", ANSI_RED);
            colorMap.put("GREEN", ANSI_GREEN);
            colorMap.put("YELLOW", ANSI_YELLOW);
            colorMap.put("BLUE", ANSI_BLUE);
            colorMap.put("PURPLE", ANSI_PURPLE);
            colorMap.put("CYAN", ANSI_CYAN);
            colorMap.put("WHITE", ANSI_WHITE);
        }
  
}
