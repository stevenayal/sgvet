package com.sgvet.usuario.boundary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.sgvet.base.boundary.BaseDbManager;
public class UsuarioManager {
    public static void main(String[] args) {
        // Esto inicializa automáticamente la base al levantar el singleton
        Connection conn = BaseDbManager.getInstance().getConnection();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIO")) {

            System.out.println("Contenido de la tabla USUARIO:");
            while (rs.next()) {
                System.out.printf("ID: %d, Nombre: %s%n", rs.getInt("ID"), rs.getString("NOMBRE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
