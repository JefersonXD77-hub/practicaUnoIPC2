/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.dao;

import practicaUnoIPC2.ConexionBD;
import practicaUnoIPC2.modelo.Asistencia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aguil
 */
public class AsistenciaDAO {

    public List<Asistencia> obtenerAsistencias() {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM Asistencia";

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql);  ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {

                Asistencia asis = new Asistencia();
                asis.setId_actividad(resultado.getString("id_actividad"));
                asis.setCorreo(resultado.getString("correo"));
                lista.add(asis);

            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener asistencia: " + ex.getMessage());
        }
        return lista;
    }

    public boolean insertar(Asistencia asis) {

        String sql = "INSERT INTO Asistencia (id_actividad, correo) VALUES (?, ?)";

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql)) {

            consulta.setString(1, asis.getId_actividad());
            consulta.setString(2, asis.getCorreo());

            return consulta.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al insertar asistencia: " + ex.getMessage());
        }
        return false;
    }

    public List<Asistencia> buscarPorCorreo(String correo) {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM Asistencia WHERE correo = ?";

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql)) {

            consulta.setString(1, correo);

            try ( ResultSet resultado = consulta.executeQuery()) {
                while (resultado.next()) {
                    Asistencia asis = new Asistencia();
                    asis.setId_actividad(resultado.getString("id_actividad"));
                    asis.setCorreo(resultado.getString("correo"));
                    lista.add(asis);
                }

            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar asistencia: " + ex.getMessage());
        }
        return lista;

    }
}
