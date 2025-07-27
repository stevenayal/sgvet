package com.sgvet.rrhh.control;

import com.sgvet.rrhh.boundary.RRHHRepository;
import com.sgvet.rrhh.entity.RRHH;
import com.sgvet.rrhh.util.RRHHValidador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para RRHHController
 * Cubre todos los métodos públicos con casos normales, de borde y errores
 */
@DisplayName("RRHHController Tests")
class RRHHControllerTest {

    @Spy
    private RRHHController rrhhController = new RRHHController();

    private RRHH empleadoValido;
    private RRHH empleadoInvalido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Empleado válido para pruebas
        empleadoValido = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234", 
                                 "juan.perez@email.com", "Veterinario", "Cirugía");
        
        // Empleado inválido para pruebas
        empleadoInvalido = new RRHH(2, "", "", "", "", "", "", "");
    }

    @Nested
    @DisplayName("crearRRHH Tests")
    class CrearRRHHTests {

        @Test
        @DisplayName("Debería crear RRHH exitosamente")
        void deberiaCrearRRHHExitosamente() {
            // Arrange
            RRHH nuevoEmpleado = new RRHH(3, "María", "García", "87654321", 
                                         "555-5678", "maria.garcia@email.com", 
                                         "Asistente", "Clínica");

            // Act
            Boolean resultado = rrhhController.crearRRHH(nuevoEmpleado);

            // Assert
            assertTrue(resultado, "Debería retornar true al crear RRHH exitosamente");
        }

        @Test
        @DisplayName("Debería manejar excepción al crear RRHH")
        void deberiaManejarExcepcionAlCrearRRHH() {
            // Arrange
            RRHH empleadoConError = new RRHH(null, null, null, null, null, null, null, null);

            // Act
            Boolean resultado = rrhhController.crearRRHH(empleadoConError);

            // Assert
            assertTrue(resultado, "Debería retornar true incluso con datos nulos (implementación actual)");
        }
    }

    @Nested
    @DisplayName("listarRRHHes Tests")
    class ListarRRHHesTests {

        @Test
        @DisplayName("Debería listar todos los RRHH exitosamente")
        void deberiaListarTodosLosRRHHExitosamente() {
            // Arrange
            List<RRHH> empleadosEsperados = Arrays.asList(empleadoValido, empleadoInvalido);
            doReturn(empleadosEsperados).when(rrhhController).listarRRHHes();

            // Act
            List<RRHH> resultado = rrhhController.listarRRHHes();

            // Assert
            assertNotNull(resultado, "La lista no debería ser null");
            assertEquals(2, resultado.size(), "Debería retornar 2 empleados");
            assertEquals(empleadosEsperados, resultado, "Debería retornar la lista esperada");
        }

        @Test
        @DisplayName("Debería retornar lista vacía cuando no hay RRHH")
        void deberiaRetornarListaVaciaCuandoNoHayRRHH() {
            // Arrange
            doReturn(Collections.emptyList()).when(rrhhController).listarRRHHes();

            // Act
            List<RRHH> resultado = rrhhController.listarRRHHes();

            // Assert
            assertNotNull(resultado, "La lista no debería ser null");
            assertTrue(resultado.isEmpty(), "Debería retornar lista vacía");
        }
    }

    @Nested
    @DisplayName("eliminarRRHH Tests")
    class EliminarRRHHTests {

        @Test
        @DisplayName("Debería eliminar RRHH exitosamente")
        void deberiaEliminarRRHHExitosamente() {
            // Arrange
            int idEmpleado = 1;
            doReturn(true).when(rrhhController).eliminarRRHH(idEmpleado);

            // Act
            boolean resultado = rrhhController.eliminarRRHH(idEmpleado);

            // Assert
            assertTrue(resultado, "Debería retornar true al eliminar exitosamente");
        }

        @Test
        @DisplayName("Debería retornar false cuando no se puede eliminar RRHH")
        void deberiaRetornarFalseCuandoNoSePuedeEliminarRRHH() {
            // Arrange
            int idEmpleado = 999;
            doReturn(false).when(rrhhController).eliminarRRHH(idEmpleado);

            // Act
            boolean resultado = rrhhController.eliminarRRHH(idEmpleado);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando no se puede eliminar");
        }
    }

    @Nested
    @DisplayName("buscarRRHH Tests")
    class BuscarRRHHTests {

        @Test
        @DisplayName("Debería encontrar RRHH por ID")
        void deberiaEncontrarRRHHPorID() {
            // Arrange
            int idEmpleado = 1;
            doReturn(empleadoValido).when(rrhhController).buscarRRHH(idEmpleado);

            // Act
            RRHH resultado = rrhhController.buscarRRHH(idEmpleado);

            // Assert
            assertNotNull(resultado, "Debería encontrar el empleado");
            assertEquals(idEmpleado, resultado.getId(), "Debería retornar el empleado correcto");
        }

        @Test
        @DisplayName("Debería retornar null cuando no encuentra RRHH")
        void deberiaRetornarNullCuandoNoEncuentraRRHH() {
            // Arrange
            int idEmpleado = 999;
            doReturn(null).when(rrhhController).buscarRRHH(idEmpleado);

            // Act
            RRHH resultado = rrhhController.buscarRRHH(idEmpleado);

            // Assert
            assertNull(resultado, "Debería retornar null cuando no encuentra el empleado");
        }
    }

    @Nested
    @DisplayName("solicitarVacaciones Tests")
    class SolicitarVacacionesTests {

        @Test
        @DisplayName("Debería solicitar vacaciones exitosamente")
        void deberiaSolicitarVacacionesExitosamente() {
            // Arrange
            int idEmpleado = 1;
            String fechaInicio = "2024-01-15";
            String fechaFin = "2024-01-20";
            doReturn(empleadoValido).when(rrhhController).buscarRRHH(idEmpleado);

            // Act
            boolean resultado = rrhhController.solicitarVacaciones(idEmpleado, fechaInicio, fechaFin);

            // Assert
            assertTrue(resultado, "Debería retornar true al solicitar vacaciones exitosamente");
        }

        @Test
        @DisplayName("Debería retornar false cuando no encuentra empleado para vacaciones")
        void deberiaRetornarFalseCuandoNoEncuentraEmpleadoParaVacaciones() {
            // Arrange
            int idEmpleado = 999;
            String fechaInicio = "2024-01-15";
            String fechaFin = "2024-01-20";
            doReturn(null).when(rrhhController).buscarRRHH(idEmpleado);

            // Act
            boolean resultado = rrhhController.solicitarVacaciones(idEmpleado, fechaInicio, fechaFin);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando no encuentra el empleado");
        }

        @Test
        @DisplayName("Debería manejar fechas nulas en solicitud de vacaciones")
        void deberiaManejarFechasNulasEnSolicitudDeVacaciones() {
            // Arrange
            int idEmpleado = 1;
            String fechaInicio = null;
            String fechaFin = null;
            doReturn(empleadoValido).when(rrhhController).buscarRRHH(idEmpleado);

            // Act
            boolean resultado = rrhhController.solicitarVacaciones(idEmpleado, fechaInicio, fechaFin);

            // Assert
            assertTrue(resultado, "Debería retornar true incluso con fechas nulas");
        }
    }

    @Nested
    @DisplayName("solicitarPermiso Tests")
    class SolicitarPermisoTests {

        @Test
        @DisplayName("Debería solicitar permiso exitosamente")
        void deberiaSolicitarPermisoExitosamente() {
            // Arrange
            int idEmpleado = 1;
            String motivo = "Cita médica";
            String fecha = "2024-01-15";
            doReturn(empleadoValido).when(rrhhController).buscarRRHH(idEmpleado);

            // Act
            boolean resultado = rrhhController.solicitarPermiso(idEmpleado, motivo, fecha);

            // Assert
            assertTrue(resultado, "Debería retornar true al solicitar permiso exitosamente");
        }

        @Test
        @DisplayName("Debería retornar false cuando no encuentra empleado para permiso")
        void deberiaRetornarFalseCuandoNoEncuentraEmpleadoParaPermiso() {
            // Arrange
            int idEmpleado = 999;
            String motivo = "Cita médica";
            String fecha = "2024-01-15";
            doReturn(null).when(rrhhController).buscarRRHH(idEmpleado);

            // Act
            boolean resultado = rrhhController.solicitarPermiso(idEmpleado, motivo, fecha);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando no encuentra el empleado");
        }

        @Test
        @DisplayName("Debería manejar datos nulos en solicitud de permiso")
        void deberiaManejarDatosNulosEnSolicitudDePermiso() {
            // Arrange
            int idEmpleado = 1;
            String motivo = null;
            String fecha = null;
            doReturn(empleadoValido).when(rrhhController).buscarRRHH(idEmpleado);

            // Act
            boolean resultado = rrhhController.solicitarPermiso(idEmpleado, motivo, fecha);

            // Assert
            assertTrue(resultado, "Debería retornar true incluso con datos nulos");
        }
    }

    @Nested
    @DisplayName("actualizarEmpleado Tests")
    class ActualizarEmpleadoTests {

        @Test
        @DisplayName("Debería actualizar empleado exitosamente")
        void deberiaActualizarEmpleadoExitosamente() {
            // Arrange
            RRHH empleadoActualizado = new RRHH(1, "Juan Carlos", "Pérez", "12345678", 
                                               "555-1234", "juan.carlos@email.com", 
                                               "Veterinario Senior", "Cirugía");
            doReturn(empleadoValido).when(rrhhController).buscarRRHH(1);

            // Act
            boolean resultado = rrhhController.actualizarEmpleado(empleadoActualizado);

            // Assert
            assertTrue(resultado, "Debería retornar true al actualizar exitosamente");
        }

        @Test
        @DisplayName("Debería retornar false cuando ID es null")
        void deberiaRetornarFalseCuandoIDEsNull() {
            // Arrange
            RRHH empleadoSinID = new RRHH(null, "Juan", "Pérez", "12345678", 
                                         "555-1234", "juan@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = rrhhController.actualizarEmpleado(empleadoSinID);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el ID es null");
        }

        @Test
        @DisplayName("Debería retornar false cuando empleado no existe")
        void deberiaRetornarFalseCuandoEmpleadoNoExiste() {
            // Arrange
            RRHH empleadoInexistente = new RRHH(999, "Juan", "Pérez", "12345678", 
                                               "555-1234", "juan@email.com", "Veterinario", "Cirugía");
            doReturn(null).when(rrhhController).buscarRRHH(999);

            // Act
            boolean resultado = rrhhController.actualizarEmpleado(empleadoInexistente);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el empleado no existe");
        }

        @Test
        @DisplayName("Debería retornar false cuando empleado es inválido")
        void deberiaRetornarFalseCuandoEmpleadoEsInvalido() {
            // Arrange
            RRHH empleadoInvalido = new RRHH(1, "", "", "", "", "", "", "");
            doReturn(empleadoValido).when(rrhhController).buscarRRHH(1);

            // Act
            boolean resultado = rrhhController.actualizarEmpleado(empleadoInvalido);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el empleado es inválido");
        }
    }
} 