/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.logica;

import practicaUnoIPC2.dao.EventoDAO;
import practicaUnoIPC2.dao.InscripcionDAO;
import practicaUnoIPC2.modelo.Evento;
import practicaUnoIPC2.modelo.Inscripcion;
import java.time.format.DateTimeFormatter;
import java.nio.file.*;
import java.io.IOException;

/**
 *
 * @author aguil
 */
public class CertificadoLogica {    //cambiosGuardados

    private final InscripcionDAO inscripcionDAO = new InscripcionDAO();
    private final EventoDAO eventoDAO = new EventoDAO();

    public String generarCertificadoHTML(String correo, String idEvento) {

        Evento eve = eventoDAO.buscarPorId(idEvento);
        if (eve == null) {
            return "Error, el evento no existe.";
        }

        Inscripcion ins = inscripcionDAO.buscar(idEvento, correo);
        if (ins == null || !Boolean.TRUE.equals(ins.isValidada())) {
            return "Error, inscripcion no existente o no validada.";
        }
        int asistencias = inscripcionDAO.contarAsistenciasEnEvento(idEvento, correo);
        if (asistencias <= 0) {
            return "Error, no hay asistencias registradas en el evento.";
        }

        String fecha = eve.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return """
              <!DOCTYPE html>
                              <html><head><meta charset="UTF-8"><title>Certificado</title></head>
                              <body style="font-family: Arial, sans-serif;">
                                <h1 style="text-align:center;">Certificado de Participación</h1>
                                <p>Se certifica que <b>%s</b> participó en el evento <b>%s</b> (%s) el día %s.</p>
                                <p>Asistencias registradas: %d</p>
                              </body></html>
                              """.formatted(correo, eve.getTitulo_evento(), eve.getId_evento(), fecha, asistencias);

    }

    public boolean guardarCertificado(String direc, String correo, String idEvento) {
        String html = generarCertificadoHTML(correo, idEvento);
        if (html.startsWith("Error")) {
            System.out.println(html);
            return false;
        }
        try {
            Files.createDirectories(Paths.get(direc));
            Path file = Paths.get(direc, "CERT_" + idEvento + "_" + correo.replaceAll("[^a-zA-Z0-9._-]", "_") + ".html");
            Files.writeString(file, html);
            return true;
        } catch (IOException ex) {
            System.out.println("Error al guardar certificado: " + ex.getMessage());
            return false;
        }
    }

}
