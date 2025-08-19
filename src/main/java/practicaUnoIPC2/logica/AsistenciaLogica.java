/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.logica;

import practicaUnoIPC2.dao.*;
import practicaUnoIPC2.modelo.*;

/**
 *
 * @author aguil
 */
public class AsistenciaLogica {

    private final AsistenciaDAO asistenciaDAO = new AsistenciaDAO();
    private final ActividadDAO actividadDAO = new ActividadDAO();
     private final InscripcionDAO inscripcionDAO = new InscripcionDAO();

    public boolean registrarAsistencia(String idActividad, String correo) {

        Actividad act = actividadDAO.buscarPorId(idActividad);
        if (act == null) {
            System.out.println("La actividad no existe");
            return false;
        }

          Inscripcion ins = inscripcionDAO.buscar(act.getId_evento(), correo);
        if (ins == null) {
            System.out.println("El participante no está inscrito en el evento de la actividad.");
            return false;
        }
        
        if (asistenciaDAO.existe(idActividad, correo)) {

            System.out.println("Asistencia duplicada");
            return false;
        }
        
        int actuales = asistenciaDAO.contarAsistentes(idActividad);
        if (actuales >= act.getCupo_maximo()) {
            System.out.println("CUpo de la actividad lleno.");
            return false;
        }

        Asistencia asis = new Asistencia();
        asis.setId_actividad(idActividad);
        asis.setCorreo(correo);
        return asistenciaDAO.insertar(asis);
    }
}
