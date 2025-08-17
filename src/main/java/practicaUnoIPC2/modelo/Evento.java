/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author aguil
 */
public class Evento {
    
    private String id_evento;
    private LocalDate fecha;
    private String tipo_evento;
    private String titulo_evento;
    private String ubicacion;
    private int cupo_maximo_evento;
    private BigDecimal costo;

    public Evento(){
    
    }
    
    public Evento(String id_evento, LocalDate fecha, String tipo_evento, String titulo_evento, String ubicacion, int cupo_maximo_evento, BigDecimal costo) {
    
        this.id_evento = id_evento;
        this.fecha = fecha;
        this.tipo_evento = tipo_evento;
        this.titulo_evento = titulo_evento;
        this.ubicacion = ubicacion;
        this.cupo_maximo_evento = cupo_maximo_evento;
        this.costo = costo;
    
    }
    
    public String getId_evento() {
        return id_evento;
    }

    public void setId_evento(String id_evento) {
        this.id_evento = id_evento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipo_evento() {
        return tipo_evento;
    }

    public void setTipo_evento(String tipo_evento) {
        this.tipo_evento = tipo_evento;
    }

    public String getTitulo_evento() {
        return titulo_evento;
    }

    public void setTitulo_evento(String titulo_evento) {
        this.titulo_evento = titulo_evento;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion_evento) {
        this.ubicacion = ubicacion_evento;
    }

    public int getCupo_maximo_evento() {
        return cupo_maximo_evento;
    }

    public void setCupo_maximo_evento(int cupo_maximo_evento) {
        this.cupo_maximo_evento = cupo_maximo_evento;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
    
}
