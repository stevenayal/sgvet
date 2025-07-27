package com.sgvet.rrhh.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la entidad RRHH
 * Cubre constructores, getters, setters y métodos
 */
@DisplayName("RRHH Entity Tests")
class RRHHTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Debería crear RRHH con constructor vacío")
        void deberiaCrearRRHHConConstructorVacio() {
            // Act
            RRHH rrhh = new RRHH();

            // Assert
            assertNotNull(rrhh, "El objeto RRHH no debería ser null");
            assertNull(rrhh.getId(), "El ID debería ser null inicialmente");
            assertNull(rrhh.getNombre(), "El nombre debería ser null inicialmente");
            assertNull(rrhh.getApellido(), "El apellido debería ser null inicialmente");
            assertNull(rrhh.getCedula(), "La cédula debería ser null inicialmente");
            assertNull(rrhh.getTelefono(), "El teléfono debería ser null inicialmente");
            assertNull(rrhh.getCorreo(), "El correo debería ser null inicialmente");
            assertNull(rrhh.getCargo(), "El cargo debería ser null inicialmente");
            assertNull(rrhh.getEspecialidad(), "La especialidad debería ser null inicialmente");
        }

        @Test
        @DisplayName("Debería crear RRHH con constructor completo")
        void deberiaCrearRRHHConConstructorCompleto() {
            // Arrange
            Integer id = 1;
            String nombre = "Juan";
            String apellido = "Pérez";
            String cedula = "12345678";
            String telefono = "555-1234";
            String correo = "juan.perez@email.com";
            String cargo = "Veterinario";
            String especialidad = "Cirugía";

            // Act
            RRHH rrhh = new RRHH(id, nombre, apellido, cedula, telefono, correo, cargo, especialidad);

            // Assert
            assertNotNull(rrhh, "El objeto RRHH no debería ser null");
            assertEquals(id, rrhh.getId(), "El ID debería coincidir");
            assertEquals(nombre, rrhh.getNombre(), "El nombre debería coincidir");
            assertEquals(apellido, rrhh.getApellido(), "El apellido debería coincidir");
            assertEquals(cedula, rrhh.getCedula(), "La cédula debería coincidir");
            assertEquals(telefono, rrhh.getTelefono(), "El teléfono debería coincidir");
            assertEquals(correo, rrhh.getCorreo(), "El correo debería coincidir");
            assertEquals(cargo, rrhh.getCargo(), "El cargo debería coincidir");
            assertEquals(especialidad, rrhh.getEspecialidad(), "La especialidad debería coincidir");
        }

        @Test
        @DisplayName("Debería crear RRHH con valores null en constructor")
        void deberiaCrearRRHHConValoresNullEnConstructor() {
            // Act
            RRHH rrhh = new RRHH(null, null, null, null, null, null, null, null);

            // Assert
            assertNotNull(rrhh, "El objeto RRHH no debería ser null");
            assertNull(rrhh.getId(), "El ID debería ser null");
            assertNull(rrhh.getNombre(), "El nombre debería ser null");
            assertNull(rrhh.getApellido(), "El apellido debería ser null");
            assertNull(rrhh.getCedula(), "La cédula debería ser null");
            assertNull(rrhh.getTelefono(), "El teléfono debería ser null");
            assertNull(rrhh.getCorreo(), "El correo debería ser null");
            assertNull(rrhh.getCargo(), "El cargo debería ser null");
            assertNull(rrhh.getEspecialidad(), "La especialidad debería ser null");
        }
    }

    @Nested
    @DisplayName("Getters and Setters Tests")
    class GettersAndSettersTests {

        private RRHH rrhh;

        @org.junit.jupiter.api.BeforeEach
        void setUp() {
            rrhh = new RRHH();
        }

        @Test
        @DisplayName("Debería establecer y obtener ID correctamente")
        void deberiaEstablecerYObtenerIDCorrectamente() {
            // Arrange
            Integer id = 1;

            // Act
            rrhh.setId(id);

            // Assert
            assertEquals(id, rrhh.getId(), "El ID debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener nombre correctamente")
        void deberiaEstablecerYObtenerNombreCorrectamente() {
            // Arrange
            String nombre = "Juan Carlos";

            // Act
            rrhh.setNombre(nombre);

            // Assert
            assertEquals(nombre, rrhh.getNombre(), "El nombre debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener apellido correctamente")
        void deberiaEstablecerYObtenerApellidoCorrectamente() {
            // Arrange
            String apellido = "Pérez García";

            // Act
            rrhh.setApellido(apellido);

            // Assert
            assertEquals(apellido, rrhh.getApellido(), "El apellido debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener cédula correctamente")
        void deberiaEstablecerYObtenerCedulaCorrectamente() {
            // Arrange
            String cedula = "87654321";

            // Act
            rrhh.setCedula(cedula);

            // Assert
            assertEquals(cedula, rrhh.getCedula(), "La cédula debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener teléfono correctamente")
        void deberiaEstablecerYObtenerTelefonoCorrectamente() {
            // Arrange
            String telefono = "555-9876";

            // Act
            rrhh.setTelefono(telefono);

            // Assert
            assertEquals(telefono, rrhh.getTelefono(), "El teléfono debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener correo correctamente")
        void deberiaEstablecerYObtenerCorreoCorrectamente() {
            // Arrange
            String correo = "nuevo.correo@empresa.com";

            // Act
            rrhh.setCorreo(correo);

            // Assert
            assertEquals(correo, rrhh.getCorreo(), "El correo debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener cargo correctamente")
        void deberiaEstablecerYObtenerCargoCorrectamente() {
            // Arrange
            String cargo = "Veterinario Senior";

            // Act
            rrhh.setCargo(cargo);

            // Assert
            assertEquals(cargo, rrhh.getCargo(), "El cargo debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener especialidad correctamente")
        void deberiaEstablecerYObtenerEspecialidadCorrectamente() {
            // Arrange
            String especialidad = "Cirugía Cardíaca";

            // Act
            rrhh.setEspecialidad(especialidad);

            // Assert
            assertEquals(especialidad, rrhh.getEspecialidad(), "La especialidad debería coincidir");
        }

        @Test
        @DisplayName("Debería manejar valores null en setters")
        void deberiaManejarValoresNullEnSetters() {
            // Act
            rrhh.setId(null);
            rrhh.setNombre(null);
            rrhh.setApellido(null);
            rrhh.setCedula(null);
            rrhh.setTelefono(null);
            rrhh.setCorreo(null);
            rrhh.setCargo(null);
            rrhh.setEspecialidad(null);

            // Assert
            assertNull(rrhh.getId(), "El ID debería ser null");
            assertNull(rrhh.getNombre(), "El nombre debería ser null");
            assertNull(rrhh.getApellido(), "El apellido debería ser null");
            assertNull(rrhh.getCedula(), "La cédula debería ser null");
            assertNull(rrhh.getTelefono(), "El teléfono debería ser null");
            assertNull(rrhh.getCorreo(), "El correo debería ser null");
            assertNull(rrhh.getCargo(), "El cargo debería ser null");
            assertNull(rrhh.getEspecialidad(), "La especialidad debería ser null");
        }

        @Test
        @DisplayName("Debería manejar cadenas vacías en setters")
        void deberiaManejarCadenasVaciasEnSetters() {
            // Arrange
            String cadenaVacia = "";

            // Act
            rrhh.setNombre(cadenaVacia);
            rrhh.setApellido(cadenaVacia);
            rrhh.setCedula(cadenaVacia);
            rrhh.setTelefono(cadenaVacia);
            rrhh.setCorreo(cadenaVacia);
            rrhh.setCargo(cadenaVacia);
            rrhh.setEspecialidad(cadenaVacia);

            // Assert
            assertEquals(cadenaVacia, rrhh.getNombre(), "El nombre debería ser cadena vacía");
            assertEquals(cadenaVacia, rrhh.getApellido(), "El apellido debería ser cadena vacía");
            assertEquals(cadenaVacia, rrhh.getCedula(), "La cédula debería ser cadena vacía");
            assertEquals(cadenaVacia, rrhh.getTelefono(), "El teléfono debería ser cadena vacía");
            assertEquals(cadenaVacia, rrhh.getCorreo(), "El correo debería ser cadena vacía");
            assertEquals(cadenaVacia, rrhh.getCargo(), "El cargo debería ser cadena vacía");
            assertEquals(cadenaVacia, rrhh.getEspecialidad(), "La especialidad debería ser cadena vacía");
        }
    }

    @Nested
    @DisplayName("toString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Debería generar toString con todos los campos")
        void deberiaGenerarToStringConTodosLosCampos() {
            // Arrange
            RRHH rrhh = new RRHH(1, "Juan", "Pérez", "12345678", "555-1234", 
                                "juan.perez@email.com", "Veterinario", "Cirugía");

            // Act
            String resultado = rrhh.toString();

            // Assert
            assertNotNull(resultado, "El toString no debería ser null");
            assertTrue(resultado.contains("RRHH{"), "Debería contener el prefijo RRHH{");
            assertTrue(resultado.contains("id=1"), "Debería contener el ID");
            assertTrue(resultado.contains("nombre='Juan'"), "Debería contener el nombre");
            assertTrue(resultado.contains("apellido='Pérez'"), "Debería contener el apellido");
            assertTrue(resultado.contains("cedula='12345678'"), "Debería contener la cédula");
            assertTrue(resultado.contains("telefono='555-1234'"), "Debería contener el teléfono");
            assertTrue(resultado.contains("correo='juan.perez@email.com'"), "Debería contener el correo");
            assertTrue(resultado.contains("cargo='Veterinario'"), "Debería contener el cargo");
            assertTrue(resultado.contains("especialidad='Cirugía'"), "Debería contener la especialidad");
            assertTrue(resultado.endsWith("}"), "Debería terminar con }");
        }

        @Test
        @DisplayName("Debería generar toString con valores null")
        void deberiaGenerarToStringConValoresNull() {
            // Arrange
            RRHH rrhh = new RRHH(null, null, null, null, null, null, null, null);

            // Act
            String resultado = rrhh.toString();

            // Assert
            assertNotNull(resultado, "El toString no debería ser null");
            assertTrue(resultado.contains("RRHH{"), "Debería contener el prefijo RRHH{");
            assertTrue(resultado.contains("id=null"), "Debería contener id=null");
            assertTrue(resultado.contains("nombre='null'"), "Debería contener nombre='null'");
            assertTrue(resultado.contains("apellido='null'"), "Debería contener apellido='null'");
            assertTrue(resultado.contains("cedula='null'"), "Debería contener cedula='null'");
            assertTrue(resultado.contains("telefono='null'"), "Debería contener telefono='null'");
            assertTrue(resultado.contains("correo='null'"), "Debería contener correo='null'");
            assertTrue(resultado.contains("cargo='null'"), "Debería contener cargo='null'");
            assertTrue(resultado.contains("especialidad='null'"), "Debería contener especialidad='null'");
            assertTrue(resultado.endsWith("}"), "Debería terminar con }");
        }

        @Test
        @DisplayName("Debería generar toString con valores vacíos")
        void deberiaGenerarToStringConValoresVacios() {
            // Arrange
            RRHH rrhh = new RRHH(1, "", "", "", "", "", "", "");

            // Act
            String resultado = rrhh.toString();

            // Assert
            assertNotNull(resultado, "El toString no debería ser null");
            assertTrue(resultado.contains("RRHH{"), "Debería contener el prefijo RRHH{");
            assertTrue(resultado.contains("id=1"), "Debería contener el ID");
            assertTrue(resultado.contains("nombre=''"), "Debería contener nombre vacío");
            assertTrue(resultado.contains("apellido=''"), "Debería contener apellido vacío");
            assertTrue(resultado.contains("cedula=''"), "Debería contener cédula vacía");
            assertTrue(resultado.contains("telefono=''"), "Debería contener teléfono vacío");
            assertTrue(resultado.contains("correo=''"), "Debería contener correo vacío");
            assertTrue(resultado.contains("cargo=''"), "Debería contener cargo vacío");
            assertTrue(resultado.contains("especialidad=''"), "Debería contener especialidad vacía");
            assertTrue(resultado.endsWith("}"), "Debería terminar con }");
        }
    }
} 