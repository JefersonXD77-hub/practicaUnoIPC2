/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.dao;

import practicaUnoIPC2.ConexionBD;
import practicaUnoIPC2.modelo.Pago;
import practicaUnoIPC2.enums.MetodoPago;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aguil
 */
public class PagoDAO {

    public List<Pago> obtenerPagos() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT *FROM Pago";

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql);  ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {
                Pago pag = new Pago();
                pag.setCorreo(resultado.getString("correo"));
                pag.setId_evento(resultado.getString("id_evento"));
                pag.setMetodo_pago(MetodoPago.valueOf(resultado.getString("metodo_pago")));
                pag.setMonto(resultado.getBigDecimal("monto"));
                lista.add(pag);

            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener pagos: " + ex.getMessage());
        }
        return lista;

    }

    public Pago buscar(String correo, String idEvento) {
        String sql = "SELECT * FROM Pago WHERE correo = ? AND id_evento = ?";
        Pago pag = null;

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql)) {

            consulta.setString(1, correo);
            consulta.setString(2, idEvento);

            try ( ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {

                    pag = new Pago();
                    pag.setCorreo(resultado.getString("correo"));
                    pag.setId_evento(resultado.getString("id_evento"));
                    pag.setMetodo_pago(MetodoPago.valueOf(resultado.getString("metodo_pago")));
                    pag.setMonto(resultado.getBigDecimal("monto"));
                }

            }

        } catch (SQLException ex) {
            System.out.println("Erros con el pago: " + ex.getMessage());
        }
        return pag;

    }

    public Pago buscarPorCorreoYEvento(String correo, String idEvento) {
    String sql = "SELECT * FROM Pago WHERE correo = ? AND id_evento = ?";
    Pago pago = null;
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, correo);
        ps.setString(2, idEvento);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                pago = new Pago();
                pago.setCorreo(rs.getString("correo"));
                pago.setId_evento(rs.getString("id_evento"));
                pago.setMetodo_pago(MetodoPago.valueOf(rs.getString("metodo_pago")));
                pago.setMonto(rs.getBigDecimal("monto"));
            }
        }
    } catch (SQLException e) {
        System.out.println("Error en buscarPorCorreoYEvento: " + e.getMessage());
    }
    return pago;
}
public boolean insertar(Pago pag) {
    String sql = "INSERT INTO Pago (correo, id_evento, metodo_pago, monto) VALUES (?, ?, ?, ?)";
    try (Connection conectar = ConexionBD.getConnection();
         PreparedStatement consulta = conectar.prepareStatement(sql)) {

        consulta.setString(1, pag.getCorreo());
        consulta.setString(2, pag.getId_evento());
        consulta.setString(3, pag.getMetodo_pago().name()); // usamos name() porque es un enum
        consulta.setBigDecimal(4, pag.getMonto());

        return consulta.executeUpdate() > 0; // si insertó al menos 1 fila devuelve true

    } catch (SQLException e) {
        System.out.println("Error al insertar pago: " + e.getMessage());
    }
    return false;
}

    
}
