/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.modelo;

/**
 *
 * @author aguil
 */
public class Asistencia {
    
    private String id_actividad;
    private String correo;
    
    public Asistencia(){
    }
    
    public Asistencia(String id_actividad, String correo){
    
        this.id_actividad = id_actividad;
        this.correo = correo;
        
    }

    public String getId_actividad() {
        return id_actividad;
    }

    public void setId_actividad(String id_actividad) {
        this.id_actividad = id_actividad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
}
