package com.sgvet.rrhh.boundary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sgvet.rrhh.boundary.RRHHDbManager;
import com.sgvet.rrhh.entity.RRHH;    

public class RRHHRepository {
    RRHHDbManager rrhhDbManager = RRHHDbManager.getInstance();

    // Constantes para mensajes de error
    private static final String ERROR_ID_POSITIVO = "Error: El ID debe ser un número positivo";
    private static final String ERROR_RRHH_NULL = "Error: No se puede procesar un RRHH null";
    private static final String ERROR_ID_NULL = "Error: No se puede procesar un RRHH con ID null";
    private static final String ERROR_YA_EXISTE = "Error: Ya existe un RRHH con el ID ";
    private static final String ERROR_NO_EXISTE = "Error: No existe un RRHH con el ID ";

    public RRHHRepository() {
        // Constructor por defecto - no requiere inicialización adicional
    }

    public List<RRHH> listarTodos() {
        List<RRHH> rrhhes = new ArrayList<>();
        String sql = "SELECT * FROM RRHH ORDER BY ID";

        try (Statement stmt = RRHHDbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rrhhes.add(crearRRHHDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            manejarSQLException("Error al listar RRHH", e);
        }

        return rrhhes;
    }

    public boolean insertar(RRHH rrhh) {
        // Validaciones
        if (!validarRRHHParaOperacion(rrhh, "insertar")) {
            return false;
        }

        if (!validarIdPositivo(rrhh.getId())) {
            return false;
        }

        if (existePorId(rrhh.getId())) {
            imprimirError(ERROR_YA_EXISTE + rrhh.getId());
            return false;
        }

        String sql = "INSERT INTO RRHH (ID, NOMBRE, APELLIDO, CEDULA, TELEFONO, CORREO, CARGO, ESPECIALIDAD) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return ejecutarInsertUpdate(sql, rrhh, "insertar");
    }

    public boolean eliminarPorId(int id) {
        if (!validarIdPositivo(id)) {
            return false;
        }

        if (!existePorId(id)) {
            imprimirError(ERROR_NO_EXISTE + id);
            return false;
        }

        String sql = "DELETE FROM RRHH WHERE ID = ?";
        return ejecutarDelete(sql, id);
    }

    public RRHH buscarPorId(int id) {
        if (!validarIdPositivo(id)) {
            return null;
        }

        String sql = "SELECT * FROM RRHH WHERE ID = ?";
        try (PreparedStatement ps = RRHHDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return crearRRHHDesdeResultSet(rs);
            }
        } catch (SQLException e) {
            manejarSQLException("Error al buscar RRHH con ID " + id, e);
        }
        return null;
    }

    public boolean actualizar(RRHH rrhh) {
        // Validaciones
        if (!validarRRHHParaOperacion(rrhh, "actualizar")) {
            return false;
        }

        if (!validarIdPositivo(rrhh.getId())) {
            return false;
        }

        if (!existePorId(rrhh.getId())) {
            imprimirError(ERROR_NO_EXISTE + rrhh.getId());
            return false;
        }

        String sql = "UPDATE RRHH SET NOMBRE=?, APELLIDO=?, CEDULA=?, TELEFONO=?, CORREO=?, CARGO=?, ESPECIALIDAD=? WHERE ID=?";
        return ejecutarUpdate(sql, rrhh);
    }

    /**
     * Limpia todos los registros de la tabla RRHH (útil para tests)
     */
    public boolean limpiarTabla() {
        String sql = "DELETE FROM RRHH";
        return ejecutarStatement(sql, "Error al limpiar tabla RRHH");
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
            manejarSQLException("Error al obtener siguiente ID", e);
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
            manejarSQLException("Error al contar registros", e);
        }
        return 0;
    }

    // ========== MÉTODOS PRIVADOS PARA ELIMINAR DUPLICACIÓN ==========

    /**
     * Valida que un RRHH no sea null para operaciones de inserción/actualización
     */
    private boolean validarRRHHParaOperacion(RRHH rrhh, String operacion) {
        if (rrhh == null) {
            imprimirError("Error: No se puede " + operacion + " un RRHH null");
            return false;
        }

        if (rrhh.getId() == null) {
            imprimirError("Error: No se puede " + operacion + " un RRHH con ID null");
            return false;
        }

        return true;
    }

    /**
     * Valida que un ID sea positivo
     */
    private boolean validarIdPositivo(Integer id) {
        if (id == null || id <= 0) {
            imprimirError(ERROR_ID_POSITIVO);
            return false;
        }
        return true;
    }

    /**
     * Crea un objeto RRHH desde un ResultSet
     */
    private RRHH crearRRHHDesdeResultSet(ResultSet rs) throws SQLException {
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

    /**
     * Maneja SQLException de forma consistente
     */
    private void manejarSQLException(String mensaje, SQLException e) {
        System.err.println(mensaje + ": " + e.getMessage());
        e.printStackTrace();
    }

    /**
     * Imprime un mensaje de error
     */
    private void imprimirError(String mensaje) {
        System.err.println(mensaje);
    }

    /**
     * Ejecuta una operación INSERT o UPDATE con PreparedStatement
     */
    private boolean ejecutarInsertUpdate(String sql, RRHH rrhh, String operacion) {
        try (PreparedStatement ps = RRHHDbManager.getConnection().prepareStatement(sql)) {
            if ("insertar".equals(operacion)) {
                establecerParametrosInsert(ps, rrhh);
            } else {
                establecerParametrosUpdate(ps, rrhh);
            }
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().equals("23505")) {
                imprimirError(ERROR_YA_EXISTE + rrhh.getId());
            } else {
                manejarSQLException("Error al " + operacion + " RRHH con ID " + rrhh.getId(), e);
            }
            return false;
        }
    }

    /**
     * Ejecuta una operación UPDATE específica
     */
    private boolean ejecutarUpdate(String sql, RRHH rrhh) {
        try (PreparedStatement ps = RRHHDbManager.getConnection().prepareStatement(sql)) {
            establecerParametrosUpdate(ps, rrhh);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            manejarSQLException("Error al actualizar RRHH con ID " + rrhh.getId(), e);
            return false;
        }
    }

    /**
     * Ejecuta una operación DELETE
     */
    private boolean ejecutarDelete(String sql, int id) {
        try (PreparedStatement ps = RRHHDbManager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            manejarSQLException("Error al eliminar RRHH con ID " + id, e);
            return false;
        }
    }

    /**
     * Ejecuta una operación con Statement
     */
    private boolean ejecutarStatement(String sql, String mensajeError) {
        try (Statement stmt = RRHHDbManager.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            manejarSQLException(mensajeError, e);
            return false;
        }
    }

    /**
     * Establece los parámetros para una operación INSERT
     */
    private void establecerParametrosInsert(PreparedStatement ps, RRHH rrhh) throws SQLException {
        ps.setInt(1, rrhh.getId());
        ps.setString(2, rrhh.getNombre());
        ps.setString(3, rrhh.getApellido());
        ps.setString(4, rrhh.getCedula());
        ps.setString(5, rrhh.getTelefono());
        ps.setString(6, rrhh.getCorreo());
        ps.setString(7, rrhh.getCargo());
        ps.setString(8, rrhh.getEspecialidad());
    }

    /**
     * Establece los parámetros para una operación UPDATE
     */
    private void establecerParametrosUpdate(PreparedStatement ps, RRHH rrhh) throws SQLException {
        ps.setString(1, rrhh.getNombre());
        ps.setString(2, rrhh.getApellido());
        ps.setString(3, rrhh.getCedula());
        ps.setString(4, rrhh.getTelefono());
        ps.setString(5, rrhh.getCorreo());
        ps.setString(6, rrhh.getCargo());
        ps.setString(7, rrhh.getEspecialidad());
        ps.setInt(8, rrhh.getId());
    }
}
