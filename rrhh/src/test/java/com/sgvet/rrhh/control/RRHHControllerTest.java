package com.sgvet.rrhh.control;

import com.sgvet.rrhh.entity.RRHH;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.AfterEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para RRHHController
 * Cubre todos los métodos públicos con casos normales, de borde y errores
 * Usa la base de datos real para pruebas de integración
 */
@DisplayName("RRHHController Tests")
class RRHHControllerTest {

    private RRHHController rrhhController;
    private RRHH empleadoValido;
    private RRHH empleadoInvalido;

    @BeforeEach
    void setUp() {
        rrhhController = new RRHHController();
        
        // Limpiar la base de datos antes de cada test
        rrhhController.limpiarTodosLosRRHH();
        
        // Empleado válido para pruebas
        empleadoValido = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234", 
                                 "juan.perez@email.com", "Veterinario", "Cirugía");
        
        // Empleado inválido para pruebas
        empleadoInvalido = new RRHH(2, "", "", "", "", "", "", "");
    }

    @AfterEach
    void tearDown() {
        // Limpiar la base de datos después de cada test
        rrhhController.limpiarTodosLosRRHH();
    }

    @Nested
    @DisplayName("crearRRHH Tests")
    class CrearRRHHTests {

        @Test
        @DisplayName("Debería crear RRHH exitosamente")
        void deberiaCrearRRHHExitosamente() {
            // Act
            Boolean resultado = rrhhController.crearRRHH(empleadoValido);

            // Assert
            assertTrue(resultado, "Debería retornar true al crear RRHH exitosamente");
            
            // Verificar que se creó en la base de datos
            RRHH encontrado = rrhhController.buscarRRHH(1);
            assertNotNull(encontrado, "Debería encontrar el RRHH creado");
            assertEquals("Juan", encontrado.getNombre(), "El nombre debería coincidir");
        }

        @Test
        @DisplayName("Debería retornar false cuando RRHH es null")
        void deberiaRetornarFalseCuandoRRHHEsNull() {
            // Act
            Boolean resultado = rrhhController.crearRRHH(null);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando RRHH es null");
        }

        @Test
        @DisplayName("Debería retornar false cuando ID es null")
        void deberiaRetornarFalseCuandoIDEsNull() {
            // Arrange
            RRHH empleadoConIDNull = new RRHH(null, "Juan", "Pérez", "12345678", 
                                             "555-1234", "juan.perez@email.com", 
                                             "Veterinario", "Cirugía");

            // Act
            Boolean resultado = rrhhController.crearRRHH(empleadoConIDNull);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando ID es null");
        }

        @Test
        @DisplayName("Debería retornar false cuando datos son inválidos")
        void deberiaRetornarFalseCuandoDatosSonInvalidos() {
            // Act
            Boolean resultado = rrhhController.crearRRHH(empleadoInvalido);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando los datos son inválidos");
        }

        @Test
        @DisplayName("Debería retornar false cuando ID ya existe")
        void deberiaRetornarFalseCuandoIDYaExiste() {
            // Arrange - Crear primer empleado
            rrhhController.crearRRHH(empleadoValido);
            
            // Intentar crear otro con el mismo ID
            RRHH empleadoDuplicado = new RRHH(1, "María", "García", "87654321", 
                                             "555-5678", "maria.garcia@email.com", 
                                             "Asistente", "Clínica");

            // Act
            Boolean resultado = rrhhController.crearRRHH(empleadoDuplicado);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el ID ya existe");
            
            // Verificar que solo existe el primer empleado
            RRHH encontrado = rrhhController.buscarRRHH(1);
            assertNotNull(encontrado, "Debería encontrar el primer empleado");
            assertEquals("Juan", encontrado.getNombre(), "Debería mantener el nombre del primer empleado");
        }

        @Test
        @DisplayName("Debería retornar false cuando ID es negativo")
        void deberiaRetornarFalseCuandoIDEsNegativo() {
            // Arrange
            RRHH empleadoConIDNegativo = new RRHH(-1, "Juan", "Pérez", "12345678", 
                                                 "555-1234", "juan.perez@email.com", 
                                                 "Veterinario", "Cirugía");

            // Act
            Boolean resultado = rrhhController.crearRRHH(empleadoConIDNegativo);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el ID es negativo");
        }
    }

    @Nested
    @DisplayName("listarRRHHes Tests")
    class ListarRRHHesTests {

        @Test
        @DisplayName("Debería listar todos los RRHH exitosamente")
        void deberiaListarTodosLosRRHHExitosamente() {
            // Arrange
            RRHH empleado2 = new RRHH(2, "María", "García", "87654321", "555-5678", 
                                     "maria.garcia@email.com", "Asistente", "Clínica");
            rrhhController.crearRRHH(empleadoValido);
            rrhhController.crearRRHH(empleado2);

            // Act
            List<RRHH> resultado = rrhhController.listarRRHHes();

            // Assert
            assertNotNull(resultado, "La lista no debería ser null");
            assertEquals(2, resultado.size(), "Debería retornar 2 empleados");
        }

        @Test
        @DisplayName("Debería retornar lista vacía cuando no hay RRHH")
        void deberiaRetornarListaVaciaCuandoNoHayRRHH() {
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
            rrhhController.crearRRHH(empleadoValido);
            
            // Act
            boolean resultado = rrhhController.eliminarRRHH(1);

            // Assert
            assertTrue(resultado, "Debería retornar true al eliminar exitosamente");
            
            // Verificar que se eliminó
            RRHH encontrado = rrhhController.buscarRRHH(1);
            assertNull(encontrado, "No debería encontrar el RRHH eliminado");
        }

        @Test
        @DisplayName("Debería retornar false cuando no existe el RRHH")
        void deberiaRetornarFalseCuandoNoExisteElRRHH() {
            // Act
            boolean resultado = rrhhController.eliminarRRHH(999);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando no existe el RRHH");
        }

        @Test
        @DisplayName("Debería retornar false cuando ID es negativo")
        void deberiaRetornarFalseCuandoIDEsNegativo() {
            // Act
            boolean resultado = rrhhController.eliminarRRHH(-1);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el ID es negativo");
        }
    }

    @Nested
    @DisplayName("buscarRRHH Tests")
    class BuscarRRHHTests {

        @Test
        @DisplayName("Debería encontrar RRHH por ID")
        void deberiaEncontrarRRHHPorID() {
            // Arrange
            rrhhController.crearRRHH(empleadoValido);

            // Act
            RRHH resultado = rrhhController.buscarRRHH(1);

            // Assert
            assertNotNull(resultado, "Debería encontrar el empleado");
            assertEquals(1, resultado.getId(), "Debería retornar el empleado correcto");
            assertEquals("Juan", resultado.getNombre(), "El nombre debería coincidir");
        }

        @Test
        @DisplayName("Debería retornar null cuando no encuentra RRHH")
        void deberiaRetornarNullCuandoNoEncuentraRRHH() {
            // Act
            RRHH resultado = rrhhController.buscarRRHH(999);

            // Assert
            assertNull(resultado, "Debería retornar null cuando no encuentra el empleado");
        }

        @Test
        @DisplayName("Debería retornar null cuando ID es negativo")
        void deberiaRetornarNullCuandoIDEsNegativo() {
            // Act
            RRHH resultado = rrhhController.buscarRRHH(-1);

            // Assert
            assertNull(resultado, "Debería retornar null cuando el ID es negativo");
        }
    }

    @Nested
    @DisplayName("solicitarVacaciones Tests")
    class SolicitarVacacionesTests {

        @Test
        @DisplayName("Debería solicitar vacaciones exitosamente")
        void deberiaSolicitarVacacionesExitosamente() {
            // Arrange
            rrhhController.crearRRHH(empleadoValido);
            String fechaInicio = "2024-01-15";
            String fechaFin = "2024-01-20";

            // Act
            boolean resultado = rrhhController.solicitarVacaciones(1, fechaInicio, fechaFin);

            // Assert
            assertTrue(resultado, "Debería retornar true al solicitar vacaciones exitosamente");
        }

        @Test
        @DisplayName("Debería retornar false cuando no encuentra empleado para vacaciones")
        void deberiaRetornarFalseCuandoNoEncuentraEmpleadoParaVacaciones() {
            // Arrange
            String fechaInicio = "2024-01-15";
            String fechaFin = "2024-01-20";

            // Act
            boolean resultado = rrhhController.solicitarVacaciones(999, fechaInicio, fechaFin);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando no encuentra el empleado");
        }

        @Test
        @DisplayName("Debería manejar fechas nulas en solicitud de vacaciones")
        void deberiaManejarFechasNulasEnSolicitudDeVacaciones() {
            // Arrange
            rrhhController.crearRRHH(empleadoValido);
            String fechaInicio = null;
            String fechaFin = null;

            // Act
            boolean resultado = rrhhController.solicitarVacaciones(1, fechaInicio, fechaFin);

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
            rrhhController.crearRRHH(empleadoValido);
            String motivo = "Cita médica";
            String fecha = "2024-01-15";

            // Act
            boolean resultado = rrhhController.solicitarPermiso(1, motivo, fecha);

            // Assert
            assertTrue(resultado, "Debería retornar true al solicitar permiso exitosamente");
        }

        @Test
        @DisplayName("Debería retornar false cuando no encuentra empleado para permiso")
        void deberiaRetornarFalseCuandoNoEncuentraEmpleadoParaPermiso() {
            // Arrange
            String motivo = "Cita médica";
            String fecha = "2024-01-15";

            // Act
            boolean resultado = rrhhController.solicitarPermiso(999, motivo, fecha);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando no encuentra el empleado");
        }

        @Test
        @DisplayName("Debería manejar datos nulos en solicitud de permiso")
        void deberiaManejarDatosNulosEnSolicitudDePermiso() {
            // Arrange
            rrhhController.crearRRHH(empleadoValido);
            String motivo = null;
            String fecha = null;

            // Act
            boolean resultado = rrhhController.solicitarPermiso(1, motivo, fecha);

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
            rrhhController.crearRRHH(empleadoValido);
            
            RRHH empleadoActualizado = new RRHH(1, "Juan Carlos", "Pérez", "12345678", 
                                               "555-1234", "juan.carlos@email.com", 
                                               "Veterinario Senior", "Cirugía");
            
            // Act
            boolean resultado = rrhhController.actualizarEmpleado(empleadoActualizado);

            // Assert
            assertTrue(resultado, "Debería retornar true al actualizar exitosamente");
            
            // Verificar que se actualizó
            RRHH encontrado = rrhhController.buscarRRHH(1);
            assertNotNull(encontrado, "Debería encontrar el empleado actualizado");
            assertEquals("Juan Carlos", encontrado.getNombre(), "El nombre debería estar actualizado");
            assertEquals("Veterinario Senior", encontrado.getCargo(), "El cargo debería estar actualizado");
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

            // Act
            boolean resultado = rrhhController.actualizarEmpleado(empleadoInexistente);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el empleado no existe");
        }

        @Test
        @DisplayName("Debería retornar false cuando empleado es inválido")
        void deberiaRetornarFalseCuandoEmpleadoEsInvalido() {
            // Arrange
            rrhhController.crearRRHH(empleadoValido);
            
            RRHH empleadoInvalido = new RRHH(1, "", "", "", "", "", "", "");

            // Act
            boolean resultado = rrhhController.actualizarEmpleado(empleadoInvalido);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el empleado es inválido");
        }

        @Test
        @DisplayName("Debería retornar false cuando ID es negativo")
        void deberiaRetornarFalseCuandoIDEsNegativo() {
            // Arrange
            RRHH empleadoConIDNegativo = new RRHH(-1, "Juan", "Pérez", "12345678", 
                                                 "555-1234", "juan@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = rrhhController.actualizarEmpleado(empleadoConIDNegativo);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el ID es negativo");
        }
    }

    @Nested
    @DisplayName("Utilidades Tests")
    class UtilidadesTests {

        @Test
        @DisplayName("Debería limpiar todos los RRHH exitosamente")
        void deberiaLimpiarTodosLosRRHHExitosamente() {
            // Arrange
            rrhhController.crearRRHH(empleadoValido);
            RRHH empleado2 = new RRHH(2, "María", "García", "87654321", "555-5678", 
                                     "maria.garcia@email.com", "Asistente", "Clínica");
            rrhhController.crearRRHH(empleado2);

            // Act
            boolean resultado = rrhhController.limpiarTodosLosRRHH();

            // Assert
            assertTrue(resultado, "Debería limpiar todos los RRHH exitosamente");
            
            List<RRHH> lista = rrhhController.listarRRHHes();
            assertTrue(lista.isEmpty(), "La lista debería estar vacía");
        }

        @Test
        @DisplayName("Debería obtener siguiente ID correctamente")
        void deberiaObtenerSiguienteIDCorrectamente() {
            // Act
            int siguienteId = rrhhController.obtenerSiguienteId();

            // Assert
            assertEquals(1, siguienteId, "Debería retornar 1 cuando no hay registros");
        }

        @Test
        @DisplayName("Debería obtener siguiente ID después de inserciones")
        void deberiaObtenerSiguienteIDDespuesDeInserciones() {
            // Arrange
            rrhhController.crearRRHH(empleadoValido);
            RRHH empleado2 = new RRHH(5, "María", "García", "87654321", "555-5678", 
                                     "maria.garcia@email.com", "Asistente", "Clínica");
            rrhhController.crearRRHH(empleado2);

            // Act
            int siguienteId = rrhhController.obtenerSiguienteId();

            // Assert
            assertEquals(6, siguienteId, "Debería retornar el siguiente ID disponible");
        }
    }
} 