/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.logica;

import practicaUnoIPC2.dao.*;
import practicaUnoIPC2.enums.*;
import practicaUnoIPC2.modelo.*;
import practicaUnoIPC2.modelo.Participante;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author aguil
 */
public class ReporteLogica {

    private final ParticipanteDAO participanteDAO = new ParticipanteDAO();
    private final InscripcionDAO inscripcionDAO = new InscripcionDAO();
    private final EventoDAO eventoDAO = new EventoDAO();
    private final ActividadDAO actividadDAO = new ActividadDAO();
    private final AsistenciaDAO asistenciaDAO = new AsistenciaDAO();
    private final PagoDAO pagoDAO = new PagoDAO();

    public boolean reporteParticipantes(String direc, String idEvento, String tipoParticipante, String institucion) {

        try {
            Files.createDirectories(Paths.get(direc));
            Evento eve = eventoDAO.buscarPorId(idEvento);
            if (eve == null) {
                System.out.println("Evento no existe ");
                return false;
            }

            TipoParticipante filtroTipo = Validador.safeEnum(TipoParticipante.class, emptyToNull(tipoParticipante));
            String filtroInst = emptyToNull(institucion);

            List<Inscripcion> inscripciones = inscripcionDAO.obtenerInscripcionesPorEvento(idEvento);

            Map<String, Participante> mapeo = participanteDAO.obtenerParticipante().stream().collect(Collectors.toMap(Participante::getCorreo, p -> p, (a, b) -> a
            ));

            StringBuilder html = new StringBuilder();
            html.append("""
                <!DOCTYPE html><html><head><meta charset="UTF-8"><title>Reporte Participantes</title>
                <style>table{border-collapse:collapse;width:100%;} td,th{border:1px solid #ccc;padding:6px;}</style>
                </head><body>
                <h2>Participantes de evento %s - %s</h2>
                <table>
                <tr><th>Correo</th><th>Tipo</th><th>Nombre completo</th><th>Institución</th><th>Validada</th></tr>
            """.formatted(eve.getId_evento(), eve.getTitulo_evento()));

            for (Inscripcion ins : inscripciones) {
                Participante p = mapeo.get(ins.getCorreo());
                if (p == null) {
                    continue;
                }

                if (filtroTipo != null && p.getTipoParticipante() != filtroTipo) {
                    continue;
                }
                if (filtroInst != null && (p.getInstitucion() == null || !p.getInstitucion().equalsIgnoreCase(filtroInst))) {
                    continue;
                }

                html.append("<tr>")
                        .append(td(p.getCorreo()))
                        .append(td(p.getTipoParticipante().name()))
                        .append(td(p.getNombreCompleto()))
                        .append(td(p.getInstitucion()))
                        .append(td(Boolean.TRUE.equals(ins.isValidada()) ? "Sí" : "No"))
                        .append("</tr>");
            }
            html.append("</table></body></html>");

            Path f = Paths.get(direc, "REPORTE_PARTICIPANTES_" + idEvento + ".html");
            Files.writeString(f, html.toString());
            return true;

        } catch (IOException e) {
            System.out.println("Error reporte participantes: " + e.getMessage());
            return false;
        }

    }

    public boolean reporteActividades(String direc, String idEvento, String tipoActividad, String correo) {
        try {
            Files.createDirectories(Paths.get(direc));
            Evento ev = eventoDAO.buscarPorId(idEvento);
            if (ev == null) {
                System.out.println("Evento no existe");
                return false;
            }

            TipoActividad filtroTipo = Validador.safeEnum(TipoActividad.class, emptyToNull(tipoActividad));
            String filtroEnc = emptyToNull(correo);

            List<Actividad> actividades = actividadDAO.obtenerActividad().stream()
                    .filter(a -> idEvento.equals(a.getId_evento()))
                    .collect(Collectors.toList());

            StringBuilder html = new StringBuilder();
            html.append("""
                <!DOCTYPE html><html><head><meta charset="UTF-8"><title>Reporte Actividades</title>
                <style>table{border-collapse:collapse;width:100%;} td,th{border:1px solid #ccc;padding:6px;}</style>
                </head><body>
                <h2>Actividades del evento %s - %s</h2>
                <table>
                <tr>
                  <th>Código Actividad</th><th>Código Evento</th><th>Título</th>
                  <th>Encargado</th><th>Hora inicio</th><th>Hora fin</th>
                  <th>Cupo Máx</th><th>Asistentes</th>
                </tr>
            """.formatted(ev.getId_evento(), ev.getTitulo_evento()));

            for (Actividad a : actividades) {
                if (filtroTipo != null && a.getTipo_actividad() != filtroTipo) {
                    continue;
                }
                if (filtroEnc != null && !filtroEnc.equalsIgnoreCase(a.getCorreo_no_asistente())) {
                    continue;
                }

                int asistentes = asistenciaDAO.contarAsistentes(a.getId_actividad());

                html.append("<tr>")
                        .append(td(a.getId_actividad()))
                        .append(td(a.getId_evento()))
                        .append(td(a.getTitulo_actividad()))
                        .append(td(a.getCorreo_no_asistente()))
                        .append(td(a.getHora_inicio().toString()))
                        .append(td(a.getHora_fin().toString()))
                        .append(td(String.valueOf(a.getCupo_maximo())))
                        .append(td(String.valueOf(asistentes)))
                        .append("</tr>");
            }
            html.append("</table></body></html>");

            Path f = Paths.get(direc, "REPORTE_ACTIVIDADES_" + idEvento + ".html");
            Files.writeString(f, html.toString());
            return true;

        } catch (IOException e) {
            System.out.println("Error reporte actividades: " + e.getMessage());
            return false;
        }
    }

    public boolean reporteEventos(String outDir, String tipoEvtOpt, String fIniOpt, String fFinOpt,
            String cupoIniOpt, String cupoFinOpt) {
        try {
            Files.createDirectories(Paths.get(outDir));

            TipoEvento tipo = Validador.safeEnum(TipoEvento.class, emptyToNull(tipoEvtOpt));
            LocalDate fIni = Validador.parseFechaDDMMYYYY(emptyToNull(fIniOpt));
            LocalDate fFin = Validador.parseFechaDDMMYYYY(emptyToNull(fFinOpt));
            Integer cIni = parseIntOrNull(emptyToNull(cupoIniOpt));
            Integer cFin = parseIntOrNull(emptyToNull(cupoFinOpt));

            // Validaciones de filtros
            if (tipo == null && fIni == null && fFin == null && cIni == null && cFin == null) {
                System.out.println("Debe indicar al menos un filtro para REPORTE_EVENTOS.");
                return false;
            }
            if ((fIni == null) != (fFin == null)) {
                System.out.println("Si filtra por fecha, indique fecha inicial y final.");
                return false;
            }
            if ((cIni == null) != (cFin == null)) {
                System.out.println("Si filtra por cupo, indique rango inicial y final.");
                return false;
            }

            List<Evento> eventos = eventoDAO.obtenerEventos().stream()
                    .filter(e -> tipo == null || e.getTipo_evento() == tipo)
                    .filter(e -> {
                        if (fIni != null && fFin != null) {
                            return !e.getFecha().isBefore(fIni) && !e.getFecha().isAfter(fFin);
                        }
                        return true;
                    })
                    .filter(e -> {
                        if (cIni != null && cFin != null) {
                            return e.getCupo_maximo_evento() >= cIni && e.getCupo_maximo_evento() <= cFin;
                        }
                        return true;
                    })
                    .collect(Collectors.toList());

            StringBuilder html = new StringBuilder();
            html.append("""
                <!DOCTYPE html><html><head><meta charset="UTF-8"><title>Reporte Eventos</title>
                <style>table{border-collapse:collapse;width:100%;} td,th{border:1px solid #ccc;padding:6px;}</style>
                </head><body>
                <h2>Reporte de Eventos</h2>
            """);

            for (Evento e : eventos) {
                List<Inscripcion> inscripciones = inscripcionDAO.obtenerInscripcionesPorEvento(e.getId_evento());

                int validadas = 0, noValidadas = 0;
                BigDecimal total = BigDecimal.ZERO;

                StringBuilder tb = new StringBuilder();
                tb.append("""
                    <table>
                    <tr>
                      <th>Código Evento</th><th>Fecha</th><th>Título</th><th>Tipo</th><th>Ubicación</th><th>Cupo Máx</th>
                    </tr>
                    <tr>
                      <td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%d</td>
                    </tr>
                    </table>
                    <br/>
                    <table>
                      <tr><th>Correo</th><th>Nombre</th><th>Tipo Participante</th><th>Método Pago</th><th>Monto Pagado</th><th>Validada</th></tr>
                """.formatted(
                        e.getId_evento(), e.getFecha(), e.getTitulo_evento(),
                        e.getTipo_evento().name(), e.getUbicacion(), e.getCupo_maximo_evento()
                ));

                for (Inscripcion ins : inscripciones) {
                    Participante p = participanteDAO.buscarPorCorreo(ins.getCorreo());
                    Pago pago = pagoDAO.buscarPorCorreoYEvento(ins.getCorreo(), e.getId_evento());

                    boolean validada = Boolean.TRUE.equals(ins.isValidada());
                    if (validada) {
                        validadas++;
                    } else {
                        noValidadas++;
                    }

                    BigDecimal monto = (pago != null && pago.getMonto() != null) ? pago.getMonto() : BigDecimal.ZERO;
                    if (pago != null) {
                        total = total.add(monto);
                    }

                    tb.append("<tr>")
                            .append(td(ins.getCorreo()))
                            .append(td(p != null ? p.getNombreCompleto() : ""))
                            .append(td(p.getTipoParticipante() != null ? p.getTipoParticipante().name() : ""))
                            .append(td(pago != null ? pago.getMetodo_pago().name() : ""))
                            .append(td(monto.toPlainString()))
                            .append(td(validada ? "Sí" : "No"))
                            .append("</tr>");
                }
                tb.append("</table>")
                        .append("<p><b>MONTO TOTAL:</b> Q.").append(total.toPlainString()).append("</p>")
                        .append("<p><b>PARTICIPANTES VALIDADOS:</b> ").append(validadas).append("</p>")
                        .append("<p><b>PARTICIPANTES NO VALIDADOS:</b> ").append(noValidadas).append("</p>")
                        .append("<hr/>");

                html.append(tb);
            }

            html.append("</body></html>");

            Path f = Paths.get(outDir, "REPORTE_EVENTOS.html");
            Files.writeString(f, html.toString());
            return true;

        } catch (IOException ex) {
            System.out.println("Error reporte eventos: " + ex.getMessage());
            return false;
        }
    }

    // ==== HELPERS ====
    private static String td(String s) {
        return "<td>" + (s == null ? "" : escape(s)) + "</td>";
    }

    private static String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private static String emptyToNull(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private static Integer parseIntOrNull(String s) {
        if (s == null) {
            return null;
        }
        try {
            return Integer.valueOf(s.trim());
        } catch (Exception e) {
            return null;
        }

    }

}
