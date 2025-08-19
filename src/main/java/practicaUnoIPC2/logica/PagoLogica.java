/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.logica;

import java.math.BigDecimal;
import practicaUnoIPC2.dao.EventoDAO;
import practicaUnoIPC2.dao.PagoDAO;
import practicaUnoIPC2.dao.ParticipanteDAO;
import practicaUnoIPC2.modelo.Evento;
import practicaUnoIPC2.modelo.Pago;
import practicaUnoIPC2.modelo.Participante;
import practicaUnoIPC2.enums.MetodoPago;
import java.util.EnumSet;

/**
 *
 * @author aguil
 */
public class PagoLogica {

    private final PagoDAO pagoDAO = new PagoDAO();
    private final ParticipanteDAO participanteDAO = new ParticipanteDAO();
    private final EventoDAO eventoDAO = new EventoDAO();
    private static final EnumSet<MetodoPago> METODOS = EnumSet.of(MetodoPago.EFECTIVO, MetodoPago.TRANSFERENCIA, MetodoPago.TARJETA);

    public boolean registrarPago(Pago pag) {

        if (pag == null) {
            return false;
        }
        if (!METODOS.contains(pag.getMetodo_pago())) {
            System.out.println("Metodo de pago no permitido");
            return false;
        }
        if (pag.getMonto() == null || pag.getMonto().compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Monto inválido.");
            return false;
        }
        Participante par = participanteDAO.buscarPorCorreo(pag.getCorreo());
        if (par == null) {
            System.out.println("EL participante no existe.");
            return false;
        }

        Evento eve = eventoDAO.buscarPorId(pag.getId_evento());
        if (eve == null) {
            System.out.println("El evento no existe.");
            return false;
        }
        if (pagoDAO.buscarPorCorreoYEvento(pag.getCorreo(), pag.getId_evento()) != null) {
            System.out.println("Pago duplicado");
            return false;
        }
        return pagoDAO.insertar(pag);
    }

}
