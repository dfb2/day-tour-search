/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package en.hi.dtsapp.model;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * Hlutverk 
 * Static methods and variables that could be used by any class in the project.
 * 
 * @author Erling Óskar Kristjánsson eok4@hi.is
 * @date
 * Háskóli Íslands
 */
public class DTSMethods {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    public static final SimpleDateFormat SDF = new SimpleDateFormat("ddMMyyyy");
}
