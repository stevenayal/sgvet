package com.sgvet.rrhh.boundary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sgvet.rrhh.boundary.RRHHDbManager;
import com.sgvet.rrhh.entity.RRHH;    

public class RRHHRepository {
    RRHHDbManager rrhhDbManager = RRHHDbManager.getInstance();

    public RRHHRepository() {
        // Constructor por defecto - no requiere inicialización adicional
    }

    public List<RRHH> listarTodos() {
        List<RRHH> rrhhes = new ArrayList<>();
        String sql = "SELECT * FROM RRHH ORDER BY ID";

        try (Statement stmt = RRHHDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                RRHH c = new RRHH(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getString("CEDULA"),
                        rs.getString("TELEFONO"),
                        rs.getString("CORREO"),
                        rs.getString("CARGO"),
                        rs.getString("ESPECIALIDAD")
                );
                rrhhes.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar RRHH: " + e.getMessage());
            e.printStackTrace();
        }

        return rrhhes;
    }

    public boolean insertar(RRHH rrhh) {
        // Validar que el RRHH no sea null
        if (rrhh == null) {
            System.err.println("Error: No se puede insertar un RRHH null");
            return false;
        }

        // Validar que el ID no sea null
        if (rrhh.getId() == null) {
            System.err.println("Error: No se puede insertar un RRHH con ID null");
            return false;
        }

        // Validar que el ID sea positivo
        if (rrhh.getId() <= 0) {
            System.err.println("Error: El ID debe ser un número positivo");
            return false;
        }

        // Verificar que no exista un RRHH con el mismo ID
        RRHH existente = buscarPorId(rrhh.getId());
        if (existente != null) {
            System.err.println("Error: Ya existe un RRHH con el ID " + rrhh.getId());
            return false;
        }

        String sql = "INSERT INTO RRHH (ID, NOMBRE, APELLIDO, CEDULA, TELEFONO, CORREO, CARGO, ESPECIALIDAD) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = RRHHDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, rrhh.getId());
            ps.setString(2, rrhh.getNombre());
            ps.setString(3, rrhh.getApellido());
            ps.setString(4, rrhh.getCedula());
            ps.setString(5, rrhh.getTelefono());
            ps.setString(6, rrhh.getCorreo());
            ps.setString(7, rrhh.getCargo());
            ps.setString(8, rrhh.getEspecialidad());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().equals("23505")) {
                System.err.println("Error: Ya existe un RRHH con el ID " + rrhh.getId());
            } else {
                System.err.println("Error al insertar RRHH: " + e.getMessage());
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarPorId(int id) {
        // Validar que el ID sea positivo
        if (id <= 0) {
            System.err.println("Error: El ID debe ser un número positivo");
            return false;
        }

        // Verificar que el RRHH existe antes de eliminar
        RRHH existente = buscarPorId(id);
        if (existente == null) {
            System.err.println("Error: No existe un RRHH con el ID " + id);
            return false;
        }

        String sql = "DELETE FROM RRHH WHERE ID = ?";
        try (PreparedStatement ps = RRHHDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar RRHH con ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public RRHH buscarPorId(int id) {
        // Validar que el ID sea positivo
        if (id <= 0) {
            return null;
        }

        String sql = "SELECT * FROM RRHH WHERE ID = ?";
        try (PreparedStatement ps = RRHHDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new RRHH(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getString("CEDULA"),
                        rs.getString("TELEFONO"),
                        rs.getString("CORREO"),
                        rs.getString("CARGO"),
                        rs.getString("ESPECIALIDAD")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar RRHH con ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizar(RRHH rrhh) {
        // Validar que el RRHH no sea null
        if (rrhh == null) {
            System.err.println("Error: No se puede actualizar un RRHH null");
            return false;
        }

        // Validar que el ID no sea null
        if (rrhh.getId() == null) {
            System.err.println("Error: No se puede actualizar un RRHH con ID null");
            return false;
        }

        // Validar que el ID sea positivo
        if (rrhh.getId() <= 0) {
            System.err.println("Error: El ID debe ser un número positivo");
            return false;
        }

        // Verificar que el RRHH existe antes de actualizar
        RRHH existente = buscarPorId(rrhh.getId());
        if (existente == null) {
            System.err.println("Error: No existe un RRHH con el ID " + rrhh.getId());
            return false;
        }

        String sql = "UPDATE RRHH SET NOMBRE=?, APELLIDO=?, CEDULA=?, TELEFONO=?, CORREO=?, CARGO=?, ESPECIALIDAD=? WHERE ID=?";
        try (PreparedStatement ps = RRHHDbManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, rrhh.getNombre());
            ps.setString(2, rrhh.getApellido());
            ps.setString(3, rrhh.getCedula());
            ps.setString(4, rrhh.getTelefono());
            ps.setString(5, rrhh.getCorreo());
            ps.setString(6, rrhh.getCargo());
            ps.setString(7, rrhh.getEspecialidad());
            ps.setInt(8, rrhh.getId());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar RRHH con ID " + rrhh.getId() + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Limpia todos los registros de la tabla RRHH (útil para tests)
     */
    public boolean limpiarTabla() {
        String sql = "DELETE FROM RRHH";
        try (Statement stmt = RRHHDbManager.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.err.println("Error al limpiar tabla RRHH: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene el siguiente ID disponible para inserción
     */
    public int obtenerSiguienteId() {
        String sql = "SELECT MAX(ID) FROM RRHH";
        try (Statement stmt = RRHHDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int maxId = rs.getInt(1);
                return maxId + 1;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener siguiente ID: " + e.getMessage());
            e.printStackTrace();
        }
        return 1; // Si no hay registros, empezar con 1
    }

    /**
     * Verifica si existe un RRHH con el ID especificado
     * @param id El ID a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existePorId(int id) {
        return buscarPorId(id) != null;
    }

    /**
     * Cuenta el número total de registros en la tabla RRHH
     * @return el número de registros
     */
    public int contarRegistros() {
        String sql = "SELECT COUNT(*) FROM RRHH";
        try (Statement stmt = RRHHDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar registros: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}
