package Const;

import java.util.HashMap;
import java.util.Map;

public class ColorsVals {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

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
