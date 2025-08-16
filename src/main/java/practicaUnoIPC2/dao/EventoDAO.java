/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2.dao;

import practicaUnoIPC2.ConexionBD;
import practicaUnoIPC2.modelo.Evento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aguil
 */
public class EventoDAO {

    public List<Evento> obtenerEventos() {
        List<Evento> lista = new ArrayList<>();
        String sql = "SELECT *FROM Evento";

        try ( Connection conectar = ConexionBD.getConnection();  PreparedStatement consulta = conectar.prepareStatement(sql);  ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {

                Evento ev = new Evento();
                ev.setId_evento(resultado.getString("id_evento"));
                ev.setFecha(resultado.getDate("fecha").toLocalDate());
                ev.setTipo_evento(resultado.getString("tipo_evento"));
                ev.setTitulo_evento(resultado.getString("titulo_evento"));
                ev.setUbicacion(resultado.getString("ubicacion"));
                ev.setCupo_maximo_evento(resultado.getInt("cupo_maximo_evento"));
                ev.setCosto(resultado.getBigDecimal("costo"));
                lista.add(ev);
            }

        } catch (SQLException ex) {
            System.out.println("Error al obtener evento: " + ex.getMessage());
        }
        return lista;
    }

    public boolean insertar(Evento ev) {
        String sql = "INSERT INTO Evento (id_evento, fecha, tipo_evento, titulo_evento, ubicacion, cupo_maximo_evento, costo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conectar = ConexionBD.getConnection();  
             PreparedStatement consulta = conectar.prepareStatement(sql)){
            
           
                consulta.setString(1, ev.getId_evento());
                consulta.setDate(2, Date.valueOf(ev.getFecha()));
                consulta.setString(3, ev.getTipo_evento());
                consulta.setString(4, ev.getTitulo_evento());
                consulta.setString(5, ev.getUbicacion());
                consulta.setInt(6, ev.getCupo_maximo_evento());
                consulta.setBigDecimal(7, ev.getCosto());

                return consulta.executeUpdate() > 0;
            } catch (SQLException ex) {
                    System.out.println("Error al insertar evento: " + ex.getMessage());
                    }
            return false;
        }

    }
