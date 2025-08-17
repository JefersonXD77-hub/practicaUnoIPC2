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
public class Inscripcion {
    
    private String id_evento;
    private String correo;
    private String tipo_inscripcion;
    private boolean validada;
    
    public Inscripcion() {
    
    }
    
    public Inscripcion(String id_evento, String correo, String tipo_inscripcion){
 
        this.id_evento = id_evento;
        this.correo = correo;
        this.tipo_inscripcion = tipo_inscripcion;
        
    }

    public String getId_evento() {
        return id_evento;
    }

    public void setId_evento(String id_evento) {
        this.id_evento = id_evento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo_inscripcion() {
        return tipo_inscripcion;
    }

    public void setTipo_inscripcion(String tipo_inscripcion) {
        this.tipo_inscripcion = tipo_inscripcion;
    }
}
