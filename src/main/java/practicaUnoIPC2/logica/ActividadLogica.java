/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.logica;

import practicaUnoIPC2.dao.*;
import practicaUnoIPC2.modelo.*;
import practicaUnoIPC2.enums.TipoInscripcion;

/**
 *
 * @author aguil
 */
public class ActividadLogica {

    private final ActividadDAO actividadDAO = new ActividadDAO();
    private final EventoDAO eventoDAO = new EventoDAO();
    private final InscripcionDAO inscripcionDAO = new InscripcionDAO();

    public boolean crearActividad(Actividad act) {
        if (act == null) {
            return false;
        }

        if (actividadDAO.buscarPorId(act.getId_actividad()) != null) {
            System.out.println("ID de actividad duplicado.");
            return false;
        }

        Evento eve = eventoDAO.buscarPorId(act.getId_evento());
        if (eve == null) {
            System.out.println("Evento no existe.");
            return false;
        }

         if (!Validador.noVacio(act.getTitulo_actividad()) || !Validador.lenMax(act.getTitulo_actividad(), 200)) {
            System.out.println("Título obligatorio menor a 200 caracteres."); 
            return false;
        }
        
        if (act.getHora_fin() == null || act.getHora_inicio() == null || !act.getHora_fin().isAfter(act.getHora_inicio())) {
            System.out.println("Rango de horas inválido.");
            return false;
        }
        if (act.getCupo_maximo() <= 0) {
            System.out.println("Cupo de actividad debe ser > 0.");
            return false;
        }

        Inscripcion insEncargado = inscripcionDAO.buscar(act.getId_evento(), act.getCorreo_no_asistente());
        if (insEncargado == null || insEncargado.getTipo_inscripcion() == TipoInscripcion.ASISTENTE) {
            System.out.println("Encargado debe estar inscrito y no ser ASISTENTE.");
            return false;
        }
        return actividadDAO.insertar(act);
    }

}
 