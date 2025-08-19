/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.logica;

import practicaUnoIPC2.enums.*;
import practicaUnoIPC2.modelo.*;
import practicaUnoIPC2.dao.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Consumer;
import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author aguil
 */
public class ProcesadorArchivo {

    private final EventoLogica eventoLogica = new EventoLogica();
    private final ParticipanteLogica participanteLogica = new ParticipanteLogica();
    private final InscripcionLogica inscripcionLogica = new InscripcionLogica();
    private final PagoLogica pagoLogica = new PagoLogica();
    private final ActividadLogica actividadLogica = new ActividadLogica();
    private final AsistenciaLogica asistenciaLogica = new AsistenciaLogica();
    private final CertificadoLogica certificadoLogica = new CertificadoLogica();
    private final ReporteLogica reporteLogica = new ReporteLogica();

    public void procesar(String rutaEntrada, long pausa, String rutaSalida){
        try(BufferedReader lector = Files.newBufferedReader(Paths.get(rutaEntrada))){
            System.out.println("Prosesando archivo ");
            Files.createDirectories(Paths.get(rutaSalida));
            
            String linea;
            while ((linea = lector.readLine()) != null) {
            linea = linea.trim();
            if(linea.equals("")) {
            continue;
            }
            
            String resultado;
            try{
            resultado = ejecutar(linea, rutaSalida);
            
            } catch (Exception e) {
            resultado = "Erro " + e.getMessage();
            }
            System.out.println(linea + " -> " + resultado);
            
            if (pausa > 0) {
            Thread.sleep(pausa);
            }
        }
            
            System.out.println("Fin del procesamiento ");
            
        } catch (Exception e) {
            System.out.println("Error al procesar archivo " + e.getMessage());
            e.printStackTrace();
        }
    
    }
    
    private String ejecutar(String linea, String carpetaOut) {
        if (linea.endsWith(";")) {
            linea = linea.substring(0, linea.length() - 1);
        }

        if (linea.startsWith("REGISTRO_EVENTO(")) return registrarEvento(linea);
        if (linea.startsWith("REGISTRO_PARTICIPANTE(")) return registrarParticipante(linea);
        if (linea.startsWith("INSCRIPCION(")) return inscribir(linea);
        if (linea.startsWith("PAGO(")) return registrarPago(linea);
        if (linea.startsWith("VALIDAR_INSCRIPCION(")) return validarInscripcion(linea);
        if (linea.startsWith("REGISTRO_ACTIVIDAD(")) return registrarActividad(linea);
        if (linea.startsWith("ASISTENCIA(")) return registrarAsistencia(linea);
        if (linea.startsWith("CERTIFICADO(")) return generarCertificado(linea, carpetaOut);
        if (linea.startsWith("REPORTE_PARTICIPANTES(")) return reporteParticipantes(linea, carpetaOut);
        if (linea.startsWith("REPORTE_ACTIVIDADES(")) return reporteActividades(linea, carpetaOut);
        if (linea.startsWith("REPORTE_EVENTOS(")) return reporteEventos(linea, carpetaOut);

        return "Instrucción no reconocida";
    }
    
    private String[] separarArgumentos(String linea) {
        int i1 = linea.indexOf('(');
        int i2 = linea.lastIndexOf(')');
        String contenido = "";
        if (i1 >= 0 && i2 > i1) {
            contenido = linea.substring(i1 + 1, i2);
        }

        List<String> partes = new ArrayList<>();
        StringBuilder actual = new StringBuilder();
        boolean enComillas = false;

        for (int i = 0; i < contenido.length(); i++) {
            char c = contenido.charAt(i);
            if (c == '"') {
                enComillas = !enComillas;
            } else if (c == ',' && !enComillas) {
                partes.add(actual.toString().trim());
                actual.setLength(0);
            } else {
                actual.append(c);
            }
        }
        partes.add(actual.toString().trim());

        return partes.toArray(new String[0]);
    }
    
    private String registrarEvento(String linea) {
        String[] args = separarArgumentos(linea);
        if (args.length != 6) {
            return "Error en argumentos";
        }

        Evento eve = new Evento();
        eve.setId_evento(args[0]);
        eve.setFecha(Validador.parseFechaDDMMYYYY(args[1]));
        eve.setTipo_evento(Validador.safeEnum(TipoEvento.class, args[2]));
        eve.setTitulo_evento(args[3]);
        eve.setUbicacion(args[4]);
        eve.setCupo_maximo_evento(Integer.parseInt(args[5]));
        eve.setCosto(BigDecimal.ZERO);

        if (eventoLogica.crearEvento(eve)) {
            return "OK";
        } else {
            return "FALLO";
        }
    }
    
   private String registrarParticipante(String linea) {
        String[] args = separarArgumentos(linea);
        if (args.length != 4) return "Error en argumentos";

        Participante p = new Participante();
        p.setNombreCompleto(args[0]);
        p.setTipoParticipante(Validador.safeEnum(TipoParticipante.class, args[1]));
        p.setInstitucion(args[2]);
        p.setCorreo(args[3]);

        return participanteLogica.crearParticipante(p) ? "OK" : "FALLO";
    }

    private String inscribir(String linea) {
        String[] args = separarArgumentos(linea);
        if (args.length != 3) return "Error en argumentos";

        Inscripcion ins = new Inscripcion();
        ins.setCorreo(args[0]);
        ins.setId_evento(args[1]);
        ins.setTipo_inscripcion(Validador.safeEnum(TipoInscripcion.class, args[2]));

        return inscripcionLogica.inscribir(ins) ? "OK" : "FALLO";
    }
    
    private String registrarPago(String linea) {
        String[] args = separarArgumentos(linea);
        if (args.length != 4) return "Error en argumentos";

        Pago p = new Pago();
        p.setCorreo(args[0]);
        p.setId_evento(args[1]);
        p.setMetodo_pago(Validador.safeEnum(MetodoPago.class, args[2]));
        p.setMonto(new BigDecimal(args[3]));

        return pagoLogica.registrarPago(p) ? "OK" : "FALLO";
    }

    private String validarInscripcion(String linea) {
        String[] args = separarArgumentos(linea);
        if (args.length != 2) return "Error en argumentos";
        return inscripcionLogica.validarInscripcion(args[0], args[1]) ? "OK" : "FALLO";
    }

    private String registrarActividad(String linea) {
        String[] args = separarArgumentos(linea);
        if (args.length != 8) return "Error en argumentos";

        Actividad act = new Actividad();
        act.setId_actividad(args[0]);
        act.setId_evento(args[1]);
        act.setTipo_actividad(Validador.safeEnum(TipoActividad.class, args[2]));
        act.setTitulo_actividad(args[3]);
        act.setCorreo_no_asistente(args[4]);
        act.setHora_inicio(Validador.parseHoraHHmm(args[5]));
        act.setHora_fin(Validador.parseHoraHHmm(args[6]));
        act.setCupo_maximo(Integer.parseInt(args[7]));

        return actividadLogica.crearActividad(act) ? "OK" : "FALLO";
    }

    private String registrarAsistencia(String linea) {
        String[] args = separarArgumentos(linea);
        if (args.length != 2) return "Error en argumentos";
        return asistenciaLogica.registrarAsistencia(args[1], args[0]) ? "OK" : "FALLO";
    }

    private String generarCertificado(String linea, String outDir) {
        String[] args = separarArgumentos(linea);
        if (args.length != 2) return "Error en argumentos";
        return certificadoLogica.guardarCertificado(outDir, args[0], args[1]) ? "OK" : "FALLO";
    }

    private String reporteParticipantes(String linea, String outDir) {
        String[] args = separarArgumentos(linea);
        if (args.length != 3) return "Error en argumentos";
        return reporteLogica.reporteParticipantes(outDir, args[0], args[1], args[2]) ? "OK" : "FALLO";
    }

    private String reporteActividades(String linea, String outDir) {
        String[] args = separarArgumentos(linea);
        if (args.length != 3) return "Error en argumentos";
        return reporteLogica.reporteActividades(outDir, args[0], args[1], args[2]) ? "OK" : "FALLO";
    }

    private String reporteEventos(String linea, String outDir) {
        String[] args = separarArgumentos(linea);
        if (args.length != 5) return "Error en argumentos";
        return reporteLogica.reporteEventos(outDir, args[0], args[1], args[2], args[3], args[4]) ? "OK" : "FALLO";
    }
}
