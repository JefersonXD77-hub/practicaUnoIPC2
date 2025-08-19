/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.logica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author aguil
 */
public class Validador {
 
    private static final DateTimeFormatter F_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter F_HORA  = DateTimeFormatter.ofPattern("HH:mm");
    
    public  Validador(){
      
    }
    
    public static boolean noVacio(String cadena) {
       return cadena != null && !cadena.trim().isEmpty();     
    }
    
    public static boolean lenMax(String s, int max) {
        return s != null && s.length() <= max;
    }

    public static LocalDate parseFechaDDMMYYYY(String s) {
    if (s == null || s.isBlank()) {
        return null; 
    }
    try {
        return LocalDate.parse(s, F_FECHA);
    } catch (DateTimeParseException e) {
        return null; 
    }
}


    public static LocalTime parseHoraHHmm(String s) {
        try { return LocalTime.parse(s, F_HORA); }
        catch (DateTimeParseException e) { return null; }
    }
    public static <E extends Enum<E>> E safeEnum(Class<E> enumType, String value) {
        if (value == null) return null;
        try { return Enum.valueOf(enumType, value.trim().toUpperCase()); }
        catch (IllegalArgumentException ex) { return null; }
    }
    
}  
  
