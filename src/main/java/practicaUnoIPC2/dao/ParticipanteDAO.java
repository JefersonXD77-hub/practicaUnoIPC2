/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.dao;

import practicaUnoIPC2.ConexionBD;
import practicaUnoIPC2.modelo.Participante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aguil
 */
public class ParticipanteDAO {

    public List<Participante> obtenerParticipante() {

        List<Participante> lista = new ArrayList<>();
        String sql = "SELECT * FROM Participante";

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql);  ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {
                Participante pa = new Participante();
                pa.setNombreCompleto(resultado.getString("nombre_completo"));
                pa.setTipoParticipante(resultado.getString("tipo_participante"));
                pa.setInstitucion(resultado.getString("institucion"));
                pa.setCorreo(resultado.getString("correo"));
                lista.add(pa);

            }
        } catch (SQLException ex) {

            System.out.println("Error al obtener participante: " + ex.getMessage());

        }
        return lista;

    }

    public boolean insertar(Participante pa) {
        String sql = "INSERT INTO Participante (nombre_completo, tipo_participante, institucion, correo) VALUES (?, ?, ?, ?)";

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql)) {

            consulta.setString(1, pa.getNombreCompleto());
            consulta.setString(2, pa.getTipoParticipante());
            consulta.setString(3, pa.getInstitucion());
            consulta.setString(4, pa.getCorreo());

            return consulta.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al insertar participante: " + ex.getMessage());
        }
        return false;
    }

    public Participante buscarPorCorreo(String correo) {
        String sql = "SELECT * FROM Participante WHERE correo = ?";
        Participante pa = null;

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql)) {

            consulta.setString(1, correo);

            try ( ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {

                }

                {
                    pa = new Participante();
                    pa.setNombreCompleto(resultado.getString("nombre_completo"));
                    pa.setTipoParticipante(resultado.getString("tipo_participante"));
                    pa.setInstitucion(resultado.getString("institucion"));
                    pa.setCorreo(resultado.getString("correo"));

                }
            }

        } catch (SQLException ex) {
            System.out.println("Error al buscar participante: " + ex.getMessage());
        }
        return pa;
    }

}
