package com.sgvet.rrhh.boundary;

import com.sgvet.rrhh.entity.RRHH;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.AfterEach;

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

    @AfterEach
    void tearDown() {
        // Limpiar la tabla después de cada test
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
            assertEquals("Pérez", encontrado.getApellido(), "El apellido debería coincidir");
            assertEquals("12345678", encontrado.getCedula(), "La cédula debería coincidir");
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
            
            // Verificar que solo existe el primer RRHH
            RRHH encontrado = repository.buscarPorId(100);
            assertNotNull(encontrado, "Debería encontrar el primer RRHH");
            assertEquals("Juan", encontrado.getNombre(), "Debería mantener el nombre del primer RRHH");
        }

        @Test
        @DisplayName("Debería retornar false cuando ID es negativo")
        void deberiaRetornarFalseCuandoIDEsNegativo() {
            // Arrange
            RRHH rrhh = new RRHH(-1, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = repository.insertar(rrhh);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando ID es negativo");
        }

        @Test
        @DisplayName("Debería retornar false cuando ID es cero")
        void deberiaRetornarFalseCuandoIDEsCero() {
            // Arrange
            RRHH rrhh = new RRHH(0, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = repository.insertar(rrhh);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando ID es cero");
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
            
            // Verificar que los RRHH están ordenados por ID
            assertEquals(1, resultado.get(0).getId(), "El primer RRHH debería tener ID 1");
            assertEquals(2, resultado.get(1).getId(), "El segundo RRHH debería tener ID 2");
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
            assertEquals("Pérez", resultado.getApellido(), "El apellido debería coincidir");
        }

        @Test
        @DisplayName("Debería retornar null cuando no encuentra RRHH")
        void deberiaRetornarNullCuandoNoEncuentraRRHH() {
            // Act
            RRHH resultado = repository.buscarPorId(999);

            // Assert
            assertNull(resultado, "Debería retornar null cuando no encuentra el RRHH");
        }

        @Test
        @DisplayName("Debería retornar null cuando ID es negativo")
        void deberiaRetornarNullCuandoIDEsNegativo() {
            // Act
            RRHH resultado = repository.buscarPorId(-1);

            // Assert
            assertNull(resultado, "Debería retornar null cuando ID es negativo");
        }

        @Test
        @DisplayName("Debería retornar null cuando ID es cero")
        void deberiaRetornarNullCuandoIDEsCero() {
            // Act
            RRHH resultado = repository.buscarPorId(0);

            // Assert
            assertNull(resultado, "Debería retornar null cuando ID es cero");
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

        @Test
        @DisplayName("Debería retornar false cuando ID es negativo")
        void deberiaRetornarFalseCuandoIDEsNegativo() {
            // Act
            boolean resultado = repository.eliminarPorId(-1);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando ID es negativo");
        }

        @Test
        @DisplayName("Debería retornar false cuando ID es cero")
        void deberiaRetornarFalseCuandoIDEsCero() {
            // Act
            boolean resultado = repository.eliminarPorId(0);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando ID es cero");
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
            assertEquals("juan.carlos@email.com", encontrado.getCorreo(), "El correo debería estar actualizado");
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

        @Test
        @DisplayName("Debería retornar false cuando ID es negativo")
        void deberiaRetornarFalseCuandoIDEsNegativo() {
            // Arrange
            RRHH rrhh = new RRHH(-1, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = repository.actualizar(rrhh);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando ID es negativo");
        }

        @Test
        @DisplayName("Debería retornar false cuando ID es cero")
        void deberiaRetornarFalseCuandoIDEsCero() {
            // Arrange
            RRHH rrhh = new RRHH(0, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = repository.actualizar(rrhh);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando ID es cero");
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

        @Test
        @DisplayName("Debería verificar existencia por ID correctamente")
        void deberiaVerificarExistenciaPorIDCorrectamente() {
            // Arrange
            RRHH rrhh = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");
            repository.insertar(rrhh);

            // Act & Assert
            assertTrue(repository.existePorId(1), "Debería existir el RRHH con ID 1");
            assertFalse(repository.existePorId(999), "No debería existir el RRHH con ID 999");
            assertFalse(repository.existePorId(-1), "No debería existir el RRHH con ID negativo");
        }

        @Test
        @DisplayName("Debería contar registros correctamente")
        void deberiaContarRegistrosCorrectamente() {
            // Act - tabla vacía
            int countVacio = repository.contarRegistros();
            assertEquals(0, countVacio, "Debería contar 0 registros cuando la tabla está vacía");

            // Arrange - agregar registros
            RRHH rrhh1 = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234", 
                                 "juan.perez@email.com", "Veterinario", "Cirugía");
            RRHH rrhh2 = new RRHH(2, "María", "García", "87654321", "555-5678", 
                                 "maria.garcia@email.com", "Asistente", "Clínica");
            
            repository.insertar(rrhh1);
            repository.insertar(rrhh2);

            // Act - tabla con registros
            int countConRegistros = repository.contarRegistros();
            assertEquals(2, countConRegistros, "Debería contar 2 registros");

            // Act - eliminar un registro
            repository.eliminarPorId(1);
            int countDespuesEliminar = repository.contarRegistros();
            assertEquals(1, countDespuesEliminar, "Debería contar 1 registro después de eliminar");
        }
    }
} 