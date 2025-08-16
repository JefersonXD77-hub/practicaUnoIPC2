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
public class Participante {

    private String nombreCompleto;
    private String tipoParticipante;
    private String institucion;
    private String correo;

    public Participante() {

    }

    public Participante(String nombreCompleto, String tipoParticipante, String institucion, String correo){
    this.nombreCompleto = nombreCompleto; 
    this.tipoParticipante = tipoParticipante;
    this.institucion = institucion;
    this.correo = correo;
    
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTipoParticipante() {
        return tipoParticipante;
    }

    public void setTipoParticipante(String tipoParticipante) {
        this.tipoParticipante = tipoParticipante;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
}
