package en.hi.dtsapp.model.DAOs;

import java.time.format.DateTimeFormatter;

/**
 * Static methods and variables that could be used by any class in the project.
 * 
 * @author Erling Óskar Kristjánsson eok4@hi.is
 * Háskóli Íslands
 */
public class DTSMethods {
    
    // Returns True if input contains or is from the set { ; ' " null }
    public static boolean isBadInput(String input){
        return (input.contains("'") || input.contains(";") || input.contains("\"")
                 || input.trim().equals("") || input == null);
    }

    // Global formatters
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
}
