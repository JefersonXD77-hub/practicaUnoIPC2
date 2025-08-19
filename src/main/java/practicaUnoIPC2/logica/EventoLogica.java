/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.logica;

import java.math.BigDecimal;
import practicaUnoIPC2.dao.EventoDAO;
import practicaUnoIPC2.modelo.Evento;
import practicaUnoIPC2.enums.TipoEvento;
import java.util.List;

/**
 *
 * @author aguil
 */
public class EventoLogica {

    private final EventoDAO eventoDAO = new EventoDAO();

    public boolean crearEvento(Evento eve) {
        if (eve == null) {
            return false;
        }

        if (!Validador.noVacio(eve.getId_evento())) {
            System.out.println("ID de evento obligatorio.");
            return false;
        }

        if (eventoDAO.buscarPorId(eve.getId_evento()) != null) {
            System.out.println("ID de evento duplicado.");
            return false;
        }

        if (eve.getFecha() == null) {
            System.out.println("Fecha de evento obligatoria.");
            return false;
        }
        if (eve.getTipo_evento() == null || !(eve.getTipo_evento() instanceof TipoEvento)) {
            System.out.println("Tipo de evento inválido.");
            return false;
        }

        if (!Validador.noVacio(eve.getTitulo_evento())) {
            System.out.println("Título obligatorio.");
            return false;
        }

        if (!Validador.noVacio(eve.getUbicacion()) || !Validador.lenMax(eve.getUbicacion(), 150)) {
            System.out.println("Ubicación obligatoria y ≤ 150 car.");
            return false;
        }

        if (eve.getCupo_maximo_evento() <= 0) {
            System.out.println("Error: el cupo debe ser mayor a 0");
            return false;
        }
       BigDecimal costo = eve.getCosto();
        if (costo == null || costo.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Costo inválido , es negativo o nulo");
            return false;
        }
        return eventoDAO.insertar(eve);
    }

    public List<Evento> listarEventos() {
        return eventoDAO.obtenerEventos();
    }

    public Evento buscarEvento(String id) {
        return eventoDAO.buscarPorId(id);
    }
}
