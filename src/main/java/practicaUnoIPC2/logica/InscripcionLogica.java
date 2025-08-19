/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.logica;

import practicaUnoIPC2.dao.InscripcionDAO;
import practicaUnoIPC2.dao.EventoDAO;
import practicaUnoIPC2.dao.ParticipanteDAO;
import practicaUnoIPC2.dao.PagoDAO;
import practicaUnoIPC2.modelo.Evento;
import practicaUnoIPC2.modelo.Participante;
import practicaUnoIPC2.modelo.Inscripcion;
import practicaUnoIPC2.modelo.Pago;
import java.util.List;


/**
 *
 * @author aguil
 */
public class InscripcionLogica {

    private final InscripcionDAO inscripcionDAO = new InscripcionDAO();
    private final ParticipanteDAO participanteDAO = new ParticipanteDAO();
    private final EventoDAO eventoDAO = new EventoDAO();
    private final PagoDAO pagoDAO = new PagoDAO();

    public boolean inscribir(Inscripcion ins) {
        Participante par = participanteDAO.buscarPorCorreo(ins.getCorreo());
        Evento eve = eventoDAO.buscarPorId(ins.getId_evento());

        if (par == null) {
            System.out.println("Error: el participante no existe");
            return false;
        }
        if (eve == null) {
            System.out.println("Erro: el evento no existe");
            return false;
        }
        if (inscripcionDAO.estaInscrito(ins.getId_evento(), ins.getCorreo())) {
            System.out.println("Error: ya está inscrito");
            return false;
        }
        if (inscripcionDAO.contarInscritos(ins.getId_evento()) >= eve.getCupo_maximo_evento()) {
            System.out.println("Erro: no hay cupo disponible");
            return false;
        }
        return inscripcionDAO.insertar(ins);

    }

    public boolean validarInscripcion(String correo, String idEvento) {
        Pago pago = pagoDAO.buscarPorCorreoYEvento(correo, idEvento);
        Evento eve = eventoDAO.buscarPorId(idEvento);

        if (pago != null && pago.getMonto().compareTo(eve.getCosto()) >= 0) {
            return inscripcionDAO.marcarValidada(idEvento, correo, true);
        } else {
            System.out.println("No se puede validar: pago inexistente");
            return false;
        }
    }

    public List<Inscripcion> listarInscripcionesPorEvento(String idEvento) {
        return inscripcionDAO.obtenerInscripcionesPorEvento(idEvento);
    }
}
