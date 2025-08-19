/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.logica;

import practicaUnoIPC2.dao.ParticipanteDAO;
import practicaUnoIPC2.modelo.Participante;
import practicaUnoIPC2.enums.TipoParticipante;
import java.util.List;
import java.util.EnumSet;

/**
 *
 * @author aguil
 */
public class ParticipanteLogica {

    private final ParticipanteDAO participanteDAO = new ParticipanteDAO();
    private static final EnumSet<TipoParticipante> TIPOS = EnumSet.of(TipoParticipante.ESTUDIANTE, TipoParticipante.PROFESIONAL, TipoParticipante.INVITADO);

    public boolean crearParticipante(Participante par) {

        if (par == null) {
            return false;
        }
        if (!Validador.noVacio(par.getNombreCompleto()) || par.getNombreCompleto().length() > 45) {
            System.out.println("Nombre invalido, vacio o mayor a 45 caracteres");
            return false;
        }

        if (!TIPOS.contains(par.getTipoParticipante())) {
            System.out.println("Tipo de participante no permitido.");
            return false;
        }

        if (!Validador.noVacio(par.getInstitucion()) || par.getInstitucion().length() > 150) {
            System.out.println("Institucion invalida, está vacia o es mayor a 150 caracteres.");
            return false;
        }
        if (participanteDAO.buscarPorCorreo(par.getCorreo()) != null) {
            System.out.println("Correo ya registrado. ");
            return false;
        }
        return participanteDAO.insertar(par);
    }

    public Participante buscarPorCorreo(String correo) {
        return participanteDAO.buscarPorCorreo(correo);
    }

    public List<Participante> listarParticipantes() {
        return participanteDAO.obtenerParticipante();
    }
}
