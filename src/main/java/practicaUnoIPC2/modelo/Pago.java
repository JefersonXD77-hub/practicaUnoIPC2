/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.modelo;

import java.math.BigDecimal;
import practicaUnoIPC2.enums.MetodoPago;

/**
 *
 * @author aguil
 */
public class Pago {
    
private String correo;
private String id_evento;
private MetodoPago metodo_pago;
private BigDecimal monto;

    public Pago(){
    
    }
    
    public Pago(String correo, String id_evento, MetodoPago metodo_pago, BigDecimal monto){
    this.correo = correo;
    this.id_evento = id_evento;
    this.metodo_pago = metodo_pago;
    this.monto = monto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getId_evento() {
        return id_evento;
    }

    public void setId_evento(String id_evento) {
        this.id_evento = id_evento;
    }

    public MetodoPago getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(MetodoPago metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
}
