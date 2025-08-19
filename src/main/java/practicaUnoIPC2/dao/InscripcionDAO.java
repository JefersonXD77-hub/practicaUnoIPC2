/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.dao;

import practicaUnoIPC2.ConexionBD;
import practicaUnoIPC2.modelo.Inscripcion;
import practicaUnoIPC2.enums.TipoInscripcion;
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
                ins.setTipo_inscripcion(TipoInscripcion.valueOf(resultado.getString("tipo_inscripcion")));
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
            consulta.setString(3, ins.getTipo_inscripcion().name());
            return consulta.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al insertar inscripción: " + ex.getMessage());
        }
        return false;
    }

    public boolean marcarValidada(String idEvento, String correo, boolean valor) {
        String sql = "UPDATE Inscripcion SET validada=? WHERE id_evento=? AND correo=?";
        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql)) {
            consulta.setBoolean(1, valor);
            consulta.setString(2, idEvento);
            consulta.setString(3, correo);
            consulta.executeUpdate();
            return consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al validar inscripcion : " + e.getMessage());
            return false;
        }

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
                    ins.setTipo_inscripcion(TipoInscripcion.valueOf(resultado.getString("tipo_inscripcion")));
                }

            }

        } catch (SQLException ex) {
            System.out.println("Error al buscar inscripción: " + ex.getMessage());
        }
        return ins;
    }

    public boolean estaInscrito(String idEvento, String correo) {
    String sql = "SELECT COUNT(*) FROM Inscripcion WHERE id_evento = ? AND correo = ?";
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, idEvento);
        ps.setString(2, correo);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    } catch (SQLException e) {
        System.out.println("Error en estaInscrito: " + e.getMessage());
    }
    return false;
}

public int contarInscritos(String idEvento) {
    String sql = "SELECT COUNT(*) FROM Inscripcion WHERE id_evento = ?";
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, idEvento);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error en contarInscritos: " + e.getMessage());
    }
    return 0;
}

public List<Inscripcion> obtenerInscripcionesPorEvento(String idEvento) {
    List<Inscripcion> lista = new ArrayList<>();
    String sql = "SELECT * FROM Inscripcion WHERE id_evento = ?";
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, idEvento);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Inscripcion ins = new Inscripcion();
                ins.setId_evento(rs.getString("id_evento"));
                ins.setCorreo(rs.getString("correo"));
                ins.setTipo_inscripcion(
                    TipoInscripcion.valueOf(rs.getString("tipo_inscripcion"))
                );
                lista.add(ins);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error en obtenerInscripcionesPorEvento: " + e.getMessage());
    }
    return lista;
}
  
}
