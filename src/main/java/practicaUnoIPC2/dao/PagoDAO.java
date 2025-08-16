/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.dao;

import practicaUnoIPC2.ConexionBD;
import practicaUnoIPC2.modelo.Pago;
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
                pag.setMetodo_pago(resultado.getString("metodo_pago"));
                pag.setMonto(resultado.getBigDecimal("monto"));
                lista.add(pag);

            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener pagos: " + ex.getMessage());
        }
        return lista;

    }

    public Pago buscar(String correo, String idEvento) {
        String sql = "SELECT * FROM Pago WHERE correo = ? AND id_envento = ?";
        Pago pag = null;

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql)) {

            consulta.setString(1, correo);
            consulta.setString(2, idEvento);

            try ( ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {

                    pag = new Pago();
                    pag.setCorreo(resultado.getString("correo"));
                    pag.setId_evento(resultado.getString("id_evento"));
                    pag.setMetodo_pago(resultado.getString("metodo_pago"));
                    pag.setMonto(resultado.getBigDecimal("monto"));
                }

            }

        } catch (SQLException ex) {
            System.out.println("Erros con el pago: " + ex.getMessage());
        }
        return pag;

    }

}
