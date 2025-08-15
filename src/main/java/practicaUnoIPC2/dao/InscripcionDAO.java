/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.dao;

import practicaUnoIPC2.ConexionBD;
import practicaUnoIPC2.modelo.Inscripcion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aguil
 */
public class InscripcionDAO {

    public List<Inscripcion> obtenerInscripciones() {
        List<Inscripcion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Inscripcion";

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql);  ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {
                Inscripcion ins = new Inscripcion();
                ins.setId_evento(resultado.getString("id_evento"));
                ins.setCorreo(resultado.getString("correo"));
                ins.setTipo_inscripcion(resultado.getString("tipo_inscripcion"));
                lista.add(ins);

            }

        } catch (SQLException ex) {
            System.out.println("Error al obetenr inscripciones: " + ex.getMessage());
        }
        return lista;

    }

    public boolean insertar(Inscripcion ins) {
        String sql = "INSERT INTO Inscripcion (id_evento, correo, tipo_inscripcion) VALUES (?, ?, ?)";

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql)) {

            consulta.setString(1, ins.getId_evento());
            consulta.setString(2, ins.getCorreo());
            consulta.setString(3, ins.getTipo_inscripcion());

            return consulta.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al insertar inscripción: " + ex.getMessage());
        }
        return false;
    }

    public Inscripcion buscar(String idEvento, String correo) {
        String sql = "SELECT *FROM Inscripcion WHERE id_evento = ? AND correo = ?";
        Inscripcion ins = null;

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql)) {

            consulta.setString(1, idEvento);
            consulta.setString(2, correo);

            try ( ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    ins = new Inscripcion();
                    ins.setId_evento(resultado.getString("id_evento"));
                    ins.setCorreo(resultado.getString("correo"));
                    ins.setTipo_inscripcion(resultado.getString("tipo_inscripcion"));
                }

            }

        } catch (SQLException ex) {
            System.out.println("Error al buscar inscripción: " + ex.getMessage());
        }
        return ins;
    }

}
