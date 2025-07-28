package com.sgvet.rrhh.control;

import com.sgvet.rrhh.boundary.RRHHRepository;
import com.sgvet.rrhh.entity.RRHH;
import com.sgvet.rrhh.util.RRHHValidador;

import java.util.List;

public class RRHHController {

    private RRHHRepository rrhhRepository = new RRHHRepository();

    public Boolean crearRRHH(RRHH RRHH) {
        try {
            // Validar que el RRHH no sea null y tenga ID válido
            if (RRHH == null || RRHH.getId() == null) {
                System.err.println("Error: No se puede crear un RRHH con ID null");
                return false;
            }

            // Validar que los datos del empleado sean válidos
            if (!RRHHValidador.validarEmpleado(RRHH)) {
                System.err.println("Error: Los datos del empleado no son válidos");
                return false;
            }

            // Verificar que no exista un RRHH con el mismo ID
            RRHH existente = rrhhRepository.buscarPorId(RRHH.getId());
            if (existente != null) {
                System.err.println("Error: Ya existe un RRHH con el ID " + RRHH.getId());
                return false;
            }

            // Intentar insertar en la base de datos
            boolean resultado = rrhhRepository.insertar(RRHH);
            if (resultado) {
                System.out.println("RRHH creado exitosamente: " + RRHH.getNombre() + " " + RRHH.getApellido());
            } else {
                System.err.println("Error: No se pudo insertar el RRHH en la base de datos");
            }
            return resultado;
        } catch (Exception e) {
            System.err.println("Error al crear RRHH: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<RRHH> listarRRHHes() {
        return rrhhRepository.listarTodos();
    }

    // Versión final: devuelve boolean, elimina por id
    public boolean eliminarRRHH(int id) {
        try {
            // Verificar que el RRHH existe antes de eliminar
            RRHH existente = rrhhRepository.buscarPorId(id);
            if (existente == null) {
                System.err.println("Error: No existe un RRHH con el ID " + id);
                return false;
            }
            
            boolean resultado = rrhhRepository.eliminarPorId(id);
            if (resultado) {
                System.out.println("RRHH eliminado exitosamente: " + existente.getNombre() + " " + existente.getApellido());
            } else {
                System.err.println("Error: No se pudo eliminar el RRHH con ID " + id);
            }
            return resultado;
        } catch (Exception e) {
            System.err.println("Error al eliminar RRHH: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public RRHH buscarRRHH(int id) {
        try {
            return rrhhRepository.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Error al buscar RRHH con ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean solicitarVacaciones(int id, String fechaInicio, String fechaFin) {
        RRHH rrhh = buscarRRHH(id);
        if (rrhh != null) {
            System.out.println("Solicitud de vacaciones:");
            System.out.println("RRHH ID: " + id);
            System.out.println("Desde: " + fechaInicio + " Hasta: " + fechaFin);
            // Aquí podrías guardar en base de datos o lista en memoria
            return true;
        }
        return false;
    }

    public boolean solicitarPermiso(int id, String motivo, String fecha) {
        RRHH rrhh = buscarRRHH(id);
        if (rrhh != null) {
            System.out.println("Solicitud de permiso:");
            System.out.println("RRHH ID: " + id);
            System.out.println("Motivo: " + motivo);
            System.out.println("Fecha: " + fecha);
            // Aquí podrías guardar en base de datos o lista en memoria
            return true;
        }
        return false;
    }

    /**
     * Actualiza los datos personales de un empleado existente
     * @param empleadoActualizado El empleado con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizarEmpleado(RRHH empleadoActualizado) {
        try {
            // Validar que el empleado exista
            if (empleadoActualizado.getId() == null) {
                System.err.println("Error: El ID del empleado es requerido para la actualización");
                return false;
            }
            
            // Validar los datos del empleado
            if (!RRHHValidador.validarEmpleado(empleadoActualizado)) {
                System.err.println("Error: Los datos del empleado no son válidos");
                return false;
            }
            
            // Buscar el empleado existente
            RRHH empleadoExistente = rrhhRepository.buscarPorId(empleadoActualizado.getId());
            if (empleadoExistente == null) {
                System.err.println("Error: No se encontró el empleado con ID " + empleadoActualizado.getId());
                return false;
            }
            
            // Actualizar en la base de datos
            boolean resultado = rrhhRepository.actualizar(empleadoActualizado);
            if (resultado) {
                System.out.println("Empleado actualizado exitosamente: " + empleadoActualizado.getNombre() + " " + empleadoActualizado.getApellido());
            } else {
                System.err.println("Error: No se pudo actualizar el empleado en la base de datos");
            }
            return resultado;
            
        } catch (Exception e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Limpia todos los registros de RRHH (útil para tests)
     * @return true si la limpieza fue exitosa, false en caso contrario
     */
    public boolean limpiarTodosLosRRHH() {
        try {
            boolean resultado = rrhhRepository.limpiarTabla();
            if (resultado) {
                System.out.println("Todos los registros de RRHH han sido eliminados");
            } else {
                System.err.println("Error: No se pudo limpiar la tabla de RRHH");
            }
            return resultado;
        } catch (Exception e) {
            System.err.println("Error al limpiar registros de RRHH: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene el siguiente ID disponible para inserción
     * @return el siguiente ID disponible
     */
    public int obtenerSiguienteId() {
        try {
            return rrhhRepository.obtenerSiguienteId();
        } catch (Exception e) {
            System.err.println("Error al obtener siguiente ID: " + e.getMessage());
            e.printStackTrace();
            return 1; // Valor por defecto
        }
    }
}
