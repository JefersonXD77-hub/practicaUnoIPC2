/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.logica;

import practicaUnoIPC2.dao.EventoDAO;
import practicaUnoIPC2.modelo.Evento;
import java.util.List;

/**
 *
 * @author aguil
 */
public class EventoLogica {

    private final EventoDAO eventoDAO = new EventoDAO();

    public boolean crearEvento(Evento eve) {
        if (eve.getCupo_maximo_evento() <= 0) {
            System.out.println("Error: el cupo debe ser mayor a 0");
            return false;
        }
        if (eve.getCosto().doubleValue() < 0) {
            System.out.println("Error: el costo no puede ser negativo");
            return false;
        }
        return eventoDAO.insertar(eve);
    }

    public List<Evento> listarEventos(){
    return eventoDAO.obtenerEventos();
    }
    
    public Evento buscarEvento(String id) {
    return eventoDAO.buscarPorId(id);
    }
}
