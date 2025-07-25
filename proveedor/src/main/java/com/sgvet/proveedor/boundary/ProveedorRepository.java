package com.sgvet.proveedor.boundary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sgvet.proveedor.boundary.ProveedorDbManager;
import com.sgvet.proveedor.entity.Proveedor;    

public class ProveedorRepository {
    ProveedorDbManager proveedorDbManager = ProveedorDbManager.getInstance();

    public ProveedorRepository() {
    }

    public List<Proveedor> listarTodos() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor";

        try (Statement stmt = ProveedorDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Proveedor c = new Proveedor(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("RAZONSOCIAL"),
                        rs.getString("TELEFONO"),
                        rs.getString("CORREO")
                );
                proveedores.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proveedores;
    }

    public void insertar(Proveedor proveedor) {
        String sql = "INSERT INTO Proveedor (ID, NOMBRE, RAZONSOCIAL, TELEFONO, CORREO) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = ProveedorDbManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, proveedor.getId());
            ps.setString(2, proveedor.getNombre());
            ps.setString(3, proveedor.getRazonSocial());
            ps.setString(4, proveedor.getTelefono());
            ps.setString(5, proveedor.getCorreo());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    proveedor.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
