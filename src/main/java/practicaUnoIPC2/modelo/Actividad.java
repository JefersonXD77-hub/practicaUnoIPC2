/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.modelo;

import java.time.LocalTime;
import practicaUnoIPC2.enums.TipoActividad;

/**
 *
 * @author aguil
 */
public class Actividad {

    private String id_actividad;
    private String id_evento;
    private String titulo_actividad;
    private TipoActividad tipo_actividad;
    private String correo_no_asistente;
    private LocalTime hora_inicio;
    private LocalTime hora_fin;
    private int cupo_maximo;

    public Actividad(){
    
    }
    
    public  Actividad(String id_actividad, String id_evento, String titulo_actividad, TipoActividad tipo_actividad, String correo_no_asistente, LocalTime hora_inicio, LocalTime hora_fin, int cupo_maximo) {

        this.id_actividad = id_actividad;
        this.id_evento = id_evento;
        this.titulo_actividad = titulo_actividad;
        this.tipo_actividad = tipo_actividad;
        this.correo_no_asistente = correo_no_asistente;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.cupo_maximo = cupo_maximo;

    }

    public String getId_actividad() {
        return id_actividad;
    }

    public void setId_actividad(String id_actividad) {
        this.id_actividad = id_actividad;
    }

    public String getId_evento() {
        return id_evento;
    }

    public void setId_evento(String id_evento) {
        this.id_evento = id_evento;
    }

    public String getTitulo_actividad() {
        return titulo_actividad;
    }

    public void setTitulo_actividad(String titulo_actividad) {
        this.titulo_actividad = titulo_actividad;
    }

    public TipoActividad getTipo_actividad() {
        return tipo_actividad;
    }

    public void setTipo_actividad(TipoActividad tipo_actividad) {
        this.tipo_actividad = tipo_actividad;
    }

    public String getCorreo_no_asistente() {
        return correo_no_asistente;
    }

    public void setCorreo_no_asistente(String correo_no_asistente) {
        this.correo_no_asistente = correo_no_asistente;
    }

    public LocalTime getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(LocalTime hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public LocalTime getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(LocalTime hora_fin) {
        this.hora_fin = hora_fin;
    }

    public int getCupo_maximo() {
        return cupo_maximo;
    }

    public void setCupo_maximo(int cupo_maximo) {
        this.cupo_maximo = cupo_maximo;
    }

}
