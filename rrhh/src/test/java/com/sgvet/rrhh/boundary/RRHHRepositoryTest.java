package com.sgvet.rrhh.boundary;

import com.sgvet.rrhh.entity.RRHH;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para RRHHRepository
 * Cubre operaciones CRUD con manejo adecuado de la base de datos
 */
@DisplayName("RRHHRepository Tests")
class RRHHRepositoryTest {

    private RRHHRepository repository;

    @BeforeEach
    void setUp() {
        repository = new RRHHRepository();
        // Limpiar la tabla antes de cada test para evitar conflictos de IDs
        repository.limpiarTabla();
    }

    @Nested
    @DisplayName("Insertar Tests")
    class InsertarTests {

        @Test
        @DisplayName("Debería insertar RRHH exitosamente")
        void deberiaInsertarRRHHExitosamente() {
            // Arrange
            RRHH rrhh = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = repository.insertar(rrhh);

            // Assert
            assertTrue(resultado, "Debería insertar exitosamente");
            
            // Verificar que se insertó correctamente
            RRHH encontrado = repository.buscarPorId(1);
            assertNotNull(encontrado, "Debería encontrar el RRHH insertado");
            assertEquals("Juan", encontrado.getNombre(), "El nombre debería coincidir");
        }

        @Test
        @DisplayName("Debería retornar false cuando ID es null")
        void deberiaRetornarFalseCuandoIDEsNull() {
            // Arrange
            RRHH rrhh = new RRHH(null, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = repository.insertar(rrhh);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando ID es null");
        }

        @Test
        @DisplayName("Debería retornar false cuando RRHH es null")
        void deberiaRetornarFalseCuandoRRHHEsNull() {
            // Act
            boolean resultado = repository.insertar(null);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando RRHH es null");
        }

        @Test
        @DisplayName("Debería retornar false cuando ID ya existe")
        void deberiaRetornarFalseCuandoIDYaExiste() {
            // Arrange
            RRHH rrhh1 = new RRHH(100, "Juan", "Pérez", "12345678", "555-1234", 
                                 "juan.perez@email.com", "Veterinario", "Cirugía");
            RRHH rrhh2 = new RRHH(100, "María", "García", "87654321", "555-5678", 
                                 "maria.garcia@email.com", "Asistente", "Clínica");

            // Act
            boolean resultado1 = repository.insertar(rrhh1);
            boolean resultado2 = repository.insertar(rrhh2);

            // Assert
            assertTrue(resultado1, "Primera inserción debería ser exitosa");
            assertFalse(resultado2, "Segunda inserción con mismo ID debería fallar");
        }
    }

    @Nested
    @DisplayName("Listar Tests")
    class ListarTests {

        @Test
        @DisplayName("Debería listar todos los RRHH")
        void deberiaListarTodosLosRRHH() {
            // Arrange
            RRHH rrhh1 = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234", 
                                 "juan.perez@email.com", "Veterinario", "Cirugía");
            RRHH rrhh2 = new RRHH(2, "María", "García", "87654321", "555-5678", 
                                 "maria.garcia@email.com", "Asistente", "Clínica");
            
            repository.insertar(rrhh1);
            repository.insertar(rrhh2);

            // Act
            List<RRHH> resultado = repository.listarTodos();

            // Assert
            assertNotNull(resultado, "La lista no debería ser null");
            assertEquals(2, resultado.size(), "Debería retornar 2 RRHH");
        }

        @Test
        @DisplayName("Debería retornar lista vacía cuando no hay RRHH")
        void deberiaRetornarListaVaciaCuandoNoHayRRHH() {
            // Act
            List<RRHH> resultado = repository.listarTodos();

            // Assert
            assertNotNull(resultado, "La lista no debería ser null");
            assertTrue(resultado.isEmpty(), "Debería retornar lista vacía");
        }
    }

    @Nested
    @DisplayName("Buscar Tests")
    class BuscarTests {

        @Test
        @DisplayName("Debería encontrar RRHH por ID")
        void deberiaEncontrarRRHHPorID() {
            // Arrange
            RRHH rrhh = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");
            repository.insertar(rrhh);

            // Act
            RRHH resultado = repository.buscarPorId(1);

            // Assert
            assertNotNull(resultado, "Debería encontrar el RRHH");
            assertEquals(1, resultado.getId(), "El ID debería coincidir");
            assertEquals("Juan", resultado.getNombre(), "El nombre debería coincidir");
        }

        @Test
        @DisplayName("Debería retornar null cuando no encuentra RRHH")
        void deberiaRetornarNullCuandoNoEncuentraRRHH() {
            // Act
            RRHH resultado = repository.buscarPorId(999);

            // Assert
            assertNull(resultado, "Debería retornar null cuando no encuentra el RRHH");
        }
    }

    @Nested
    @DisplayName("Eliminar Tests")
    class EliminarTests {

        @Test
        @DisplayName("Debería eliminar RRHH exitosamente")
        void deberiaEliminarRRHHExitosamente() {
            // Arrange
            RRHH rrhh = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");
            repository.insertar(rrhh);

            // Act
            boolean resultado = repository.eliminarPorId(1);

            // Assert
            assertTrue(resultado, "Debería eliminar exitosamente");
            
            // Verificar que se eliminó
            RRHH encontrado = repository.buscarPorId(1);
            assertNull(encontrado, "No debería encontrar el RRHH eliminado");
        }

        @Test
        @DisplayName("Debería retornar false cuando no existe el ID")
        void deberiaRetornarFalseCuandoNoExisteElID() {
            // Act
            boolean resultado = repository.eliminarPorId(999);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando no existe el ID");
        }
    }

    @Nested
    @DisplayName("Actualizar Tests")
    class ActualizarTests {

        @Test
        @DisplayName("Debería actualizar RRHH exitosamente")
        void deberiaActualizarRRHHExitosamente() {
            // Arrange
            RRHH rrhh = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");
            repository.insertar(rrhh);

            RRHH rrhhActualizado = new RRHH(1, "Juan Carlos", "Pérez", "12345678", "555-1234", 
                                           "juan.carlos@email.com", "Veterinario Senior", "Cirugía");

            // Act
            boolean resultado = repository.actualizar(rrhhActualizado);

            // Assert
            assertTrue(resultado, "Debería actualizar exitosamente");
            
            // Verificar que se actualizó
            RRHH encontrado = repository.buscarPorId(1);
            assertNotNull(encontrado, "Debería encontrar el RRHH actualizado");
            assertEquals("Juan Carlos", encontrado.getNombre(), "El nombre debería estar actualizado");
            assertEquals("Veterinario Senior", encontrado.getCargo(), "El cargo debería estar actualizado");
        }

        @Test
        @DisplayName("Debería retornar false cuando ID es null")
        void deberiaRetornarFalseCuandoIDEsNull() {
            // Arrange
            RRHH rrhh = new RRHH(null, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = repository.actualizar(rrhh);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando ID es null");
        }

        @Test
        @DisplayName("Debería retornar false cuando RRHH es null")
        void deberiaRetornarFalseCuandoRRHHEsNull() {
            // Act
            boolean resultado = repository.actualizar(null);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando RRHH es null");
        }

        @Test
        @DisplayName("Debería retornar false cuando no existe el RRHH")
        void deberiaRetornarFalseCuandoNoExisteElRRHH() {
            // Arrange
            RRHH rrhh = new RRHH(999, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = repository.actualizar(rrhh);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando no existe el RRHH");
        }
    }

    @Nested
    @DisplayName("Utilidades Tests")
    class UtilidadesTests {

        @Test
        @DisplayName("Debería limpiar tabla exitosamente")
        void deberiaLimpiarTablaExitosamente() {
            // Arrange
            RRHH rrhh = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");
            repository.insertar(rrhh);

            // Act
            boolean resultado = repository.limpiarTabla();

            // Assert
            assertTrue(resultado, "Debería limpiar la tabla exitosamente");
            
            List<RRHH> lista = repository.listarTodos();
            assertTrue(lista.isEmpty(), "La tabla debería estar vacía");
        }

        @Test
        @DisplayName("Debería obtener siguiente ID correctamente")
        void deberiaObtenerSiguienteIDCorrectamente() {
            // Act
            int siguienteId = repository.obtenerSiguienteId();

            // Assert
            assertEquals(1, siguienteId, "Debería retornar 1 cuando la tabla está vacía");
        }

        @Test
        @DisplayName("Debería obtener siguiente ID después de inserciones")
        void deberiaObtenerSiguienteIDDespuesDeInserciones() {
            // Arrange
            RRHH rrhh1 = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234", 
                                 "juan.perez@email.com", "Veterinario", "Cirugía");
            RRHH rrhh2 = new RRHH(5, "María", "García", "87654321", "555-5678", 
                                 "maria.garcia@email.com", "Asistente", "Clínica");
            
            repository.insertar(rrhh1);
            repository.insertar(rrhh2);

            // Act
            int siguienteId = repository.obtenerSiguienteId();

            // Assert
            assertEquals(6, siguienteId, "Debería retornar el siguiente ID disponible");
        }
    }
} 