/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.dao;

import practicaUnoIPC2.ConexionBD;
import practicaUnoIPC2.modelo.Actividad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aguil
 */
public class ActividadDAO {

    public List<Actividad> obtenerActividad() {
        List<Actividad> lista = new ArrayList<>();
        String sql = "SELECT * FROM Actividd";

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql);  ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {
                Actividad act = new Actividad();
                act.setId_actividad(resultado.getString("id_actividad"));
                act.setId_evento(resultado.getString("id_evento"));
                act.setTitulo_actividad(resultado.getString("titulo_actividad"));
                act.setTipo_actividad(resultado.getString("tipo_actividad"));
                act.setCorreo_no_asistente(resultado.getString("correo_no_asistente"));
                act.setHora_inicio(resultado.getTime("hora_inicio").toLocalTime());
                act.setHora_fin(resultado.getTime("hora_fin").toLocalTime());
                act.setCupo_maximo(resultado.getInt("cupo_maximo"));
                lista.add(act);
            }

        } catch (SQLException ex) {
            System.out.println("Error al obtener actividades: " + ex.getMessage());
        }

        return lista;
    }

    public boolean insertar(Actividad act) {
        String sql = "INSERT INTO Actividad (id_actividad, id_evento, titulo_actividad, tipo_actividad, correo_no_asistente, hora_inicio, hora_fin, cupo_maximo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql)) {

            consulta.setString(1, act.getId_actividad());
            consulta.setString(2, act.getId_evento());
            consulta.setString(3, act.getTitulo_actividad());
            consulta.setString(4, act.getTipo_actividad());
            consulta.setString(5, act.getCorreo_no_asistente());
            consulta.setTime(6, Time.valueOf(act.getHora_inicio()));
            consulta.setTime(7, Time.valueOf(act.getHora_fin()));

            consulta.setInt(8, act.getCupo_maximo());

            return consulta.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al insertar actividad: " + ex.getMessage());
        }
        return false;
    }

    public Actividad buscarPorId(String idActividad) {
        String sql = "SELECT * FROM Actividad WHERE id_actividad = ?";
        Actividad act = null;

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql)) {

            consulta.setString(1, idActividad);

            try ( ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    act = new Actividad();
                    act.setId_actividad(resultado.getString("id_actividad"));
                    act.setId_evento(resultado.getString("id_evento"));
                    act.setTitulo_actividad(resultado.getString("titulo_actividad"));
                    act.setTipo_actividad(resultado.getString("tipo_actividad"));
                    act.setCorreo_no_asistente(resultado.getString("correo_no_asistente"));
                    act.setHora_inicio(resultado.getTime("hora_inicio").toLocalTime());
                    act.setHora_fin(resultado.getTime("hora_fin").toLocalTime());
                    act.setCupo_maximo(resultado.getInt("cupo_maximo"));
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error al buscar actividad: " + ex.getMessage());
        }
        return act;
    }

}
