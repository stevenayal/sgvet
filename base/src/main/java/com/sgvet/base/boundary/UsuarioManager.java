package com.sgvet.base.boundary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
public class UsuarioManager {
    public static void main(String[] args) {
        // Esto inicializa automáticamente la base al levantar el singleton
        Connection conn = DbManager.getInstance().getConnection();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM PERSONA")) {

            System.out.println("Contenido de la tabla PERSONA:");
            while (rs.next()) {
                System.out.printf("ID: %d, Nombre: %s%n", rs.getInt("ID"), rs.getString("NOMBRE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
