/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.logica;

import java.util.Set;

/**
 *
 * @author aguil
 */
public class Validador {
 
    public  Validador(){
    
    }
    
    public static boolean noVacio(String cadena) {
       return cadena != null && !cadena.trim().isEmpty();     
    }
    
    public static boolean en(Set<String> permitidos, String valor) {
    return valor != null && permitidos.contains(valor.toUpperCase());
    }
}  
  
