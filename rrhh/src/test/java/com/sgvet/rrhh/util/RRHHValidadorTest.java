package com.sgvet.rrhh.util;

import com.sgvet.rrhh.entity.RRHH;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para RRHHValidador
 * Cubre todos los métodos estáticos con casos normales, de borde y errores
 */
@DisplayName("RRHHValidador Tests")
class RRHHValidadorTest {

    @Nested
    @DisplayName("validarCamposObligatorios Tests")
    class ValidarCamposObligatoriosTests {

        @Test
        @DisplayName("Debería validar empleado con todos los campos obligatorios")
        void deberiaValidarEmpleadoConTodosLosCamposObligatorios() {
            // Arrange
            RRHH empleadoValido = new RRHH(1, "Juan", "Pérez", "12345678", 
                                          "555-1234", "juan@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = RRHHValidador.validarCamposObligatorios(empleadoValido);

            // Assert
            assertTrue(resultado, "Debería retornar true para empleado con todos los campos obligatorios");
        }

        @Test
        @DisplayName("Debería retornar false cuando empleado es null")
        void deberiaRetornarFalseCuandoEmpleadoEsNull() {
            // Arrange
            RRHH empleadoNull = null;

            // Act
            boolean resultado = RRHHValidador.validarCamposObligatorios(empleadoNull);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el empleado es null");
        }

        @Test
        @DisplayName("Debería retornar false cuando nombre es null")
        void deberiaRetornarFalseCuandoNombreEsNull() {
            // Arrange
            RRHH empleadoSinNombre = new RRHH(1, null, "Pérez", "12345678", 
                                             "555-1234", "juan@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = RRHHValidador.validarCamposObligatorios(empleadoSinNombre);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el nombre es null");
        }

        @Test
        @DisplayName("Debería retornar false cuando nombre está vacío")
        void deberiaRetornarFalseCuandoNombreEstaVacio() {
            // Arrange
            RRHH empleadoNombreVacio = new RRHH(1, "", "Pérez", "12345678", 
                                               "555-1234", "juan@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = RRHHValidador.validarCamposObligatorios(empleadoNombreVacio);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el nombre está vacío");
        }

        @Test
        @DisplayName("Debería retornar false cuando nombre solo tiene espacios")
        void deberiaRetornarFalseCuandoNombreSoloTieneEspacios() {
            // Arrange
            RRHH empleadoNombreEspacios = new RRHH(1, "   ", "Pérez", "12345678", 
                                                  "555-1234", "juan@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = RRHHValidador.validarCamposObligatorios(empleadoNombreEspacios);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el nombre solo tiene espacios");
        }

        @Test
        @DisplayName("Debería retornar false cuando apellido es null")
        void deberiaRetornarFalseCuandoApellidoEsNull() {
            // Arrange
            RRHH empleadoSinApellido = new RRHH(1, "Juan", null, "12345678", 
                                               "555-1234", "juan@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = RRHHValidador.validarCamposObligatorios(empleadoSinApellido);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el apellido es null");
        }

        @Test
        @DisplayName("Debería retornar false cuando cédula es null")
        void deberiaRetornarFalseCuandoCedulaEsNull() {
            // Arrange
            RRHH empleadoSinCedula = new RRHH(1, "Juan", "Pérez", null, 
                                             "555-1234", "juan@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = RRHHValidador.validarCamposObligatorios(empleadoSinCedula);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando la cédula es null");
        }

        @Test
        @DisplayName("Debería retornar false cuando cargo es null")
        void deberiaRetornarFalseCuandoCargoEsNull() {
            // Arrange
            RRHH empleadoSinCargo = new RRHH(1, "Juan", "Pérez", "12345678", 
                                            "555-1234", "juan@email.com", null, "Cirugía");

            // Act
            boolean resultado = RRHHValidador.validarCamposObligatorios(empleadoSinCargo);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el cargo es null");
        }
    }

    @Nested
    @DisplayName("validarFormatoCorreo Tests")
    class ValidarFormatoCorreoTests {

        @Test
        @DisplayName("Debería validar correo con formato correcto")
        void deberiaValidarCorreoConFormatoCorrecto() {
            // Arrange
            String correoValido = "juan.perez@email.com";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCorreo(correoValido);

            // Assert
            assertTrue(resultado, "Debería retornar true para correo con formato correcto");
        }

        @Test
        @DisplayName("Debería validar correo con subdominio")
        void deberiaValidarCorreoConSubdominio() {
            // Arrange
            String correoSubdominio = "juan.perez@empresa.co.uk";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCorreo(correoSubdominio);

            // Assert
            assertTrue(resultado, "Debería retornar true para correo con subdominio");
        }

        @Test
        @DisplayName("Debería validar correo con números")
        void deberiaValidarCorreoConNumeros() {
            // Arrange
            String correoConNumeros = "juan123@email.com";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCorreo(correoConNumeros);

            // Assert
            assertTrue(resultado, "Debería retornar true para correo con números");
        }

        @Test
        @DisplayName("Debería retornar true cuando correo es null")
        void deberiaRetornarTrueCuandoCorreoEsNull() {
            // Arrange
            String correoNull = null;

            // Act
            boolean resultado = RRHHValidador.validarFormatoCorreo(correoNull);

            // Assert
            assertTrue(resultado, "Debería retornar true cuando el correo es null (opcional)");
        }

        @Test
        @DisplayName("Debería retornar true cuando correo está vacío")
        void deberiaRetornarTrueCuandoCorreoEstaVacio() {
            // Arrange
            String correoVacio = "";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCorreo(correoVacio);

            // Assert
            assertTrue(resultado, "Debería retornar true cuando el correo está vacío (opcional)");
        }

        @Test
        @DisplayName("Debería retornar false cuando correo no tiene @")
        void deberiaRetornarFalseCuandoCorreoNoTieneArroba() {
            // Arrange
            String correoSinArroba = "juan.perez.email.com";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCorreo(correoSinArroba);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el correo no tiene @");
        }

        @Test
        @DisplayName("Debería retornar false cuando correo no tiene dominio")
        void deberiaRetornarFalseCuandoCorreoNoTieneDominio() {
            // Arrange
            String correoSinDominio = "juan.perez@";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCorreo(correoSinDominio);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el correo no tiene dominio");
        }

        @Test
        @DisplayName("Debería retornar false cuando correo tiene caracteres inválidos")
        void deberiaRetornarFalseCuandoCorreoTieneCaracteresInvalidos() {
            // Arrange
            String correoInvalido = "juan.perez@email*.com";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCorreo(correoInvalido);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el correo tiene caracteres inválidos");
        }
    }

    @Nested
    @DisplayName("validarFormatoTelefono Tests")
    class ValidarFormatoTelefonoTests {

        @Test
        @DisplayName("Debería validar teléfono con formato correcto")
        void deberiaValidarTelefonoConFormatoCorrecto() {
            // Arrange
            String telefonoValido = "555-1234";

            // Act
            boolean resultado = RRHHValidador.validarFormatoTelefono(telefonoValido);

            // Assert
            assertTrue(resultado, "Debería retornar true para teléfono con formato correcto");
        }

        @Test
        @DisplayName("Debería validar teléfono sin guiones")
        void deberiaValidarTelefonoSinGuiones() {
            // Arrange
            String telefonoSinGuiones = "5551234";

            // Act
            boolean resultado = RRHHValidador.validarFormatoTelefono(telefonoSinGuiones);

            // Assert
            assertTrue(resultado, "Debería retornar true para teléfono sin guiones");
        }

        @Test
        @DisplayName("Debería validar teléfono con múltiples guiones")
        void deberiaValidarTelefonoConMultiplesGuiones() {
            // Arrange
            String telefonoMultiplesGuiones = "555-123-4567";

            // Act
            boolean resultado = RRHHValidador.validarFormatoTelefono(telefonoMultiplesGuiones);

            // Assert
            assertTrue(resultado, "Debería retornar true para teléfono con múltiples guiones");
        }

        @Test
        @DisplayName("Debería retornar true cuando teléfono es null")
        void deberiaRetornarTrueCuandoTelefonoEsNull() {
            // Arrange
            String telefonoNull = null;

            // Act
            boolean resultado = RRHHValidador.validarFormatoTelefono(telefonoNull);

            // Assert
            assertTrue(resultado, "Debería retornar true cuando el teléfono es null (opcional)");
        }

        @Test
        @DisplayName("Debería retornar true cuando teléfono está vacío")
        void deberiaRetornarTrueCuandoTelefonoEstaVacio() {
            // Arrange
            String telefonoVacio = "";

            // Act
            boolean resultado = RRHHValidador.validarFormatoTelefono(telefonoVacio);

            // Assert
            assertTrue(resultado, "Debería retornar true cuando el teléfono está vacío (opcional)");
        }

        @Test
        @DisplayName("Debería retornar false cuando teléfono es muy corto")
        void deberiaRetornarFalseCuandoTelefonoEsMuyCorto() {
            // Arrange
            String telefonoCorto = "123";

            // Act
            boolean resultado = RRHHValidador.validarFormatoTelefono(telefonoCorto);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el teléfono es muy corto");
        }

        @Test
        @DisplayName("Debería retornar false cuando teléfono es muy largo")
        void deberiaRetornarFalseCuandoTelefonoEsMuyLargo() {
            // Arrange
            String telefonoLargo = "12345678901234567890";

            // Act
            boolean resultado = RRHHValidador.validarFormatoTelefono(telefonoLargo);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el teléfono es muy largo");
        }

        @Test
        @DisplayName("Debería retornar false cuando teléfono tiene letras")
        void deberiaRetornarFalseCuandoTelefonoTieneLetras() {
            // Arrange
            String telefonoConLetras = "555-ABC-1234";

            // Act
            boolean resultado = RRHHValidador.validarFormatoTelefono(telefonoConLetras);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el teléfono tiene letras");
        }
    }

    @Nested
    @DisplayName("validarFormatoCedula Tests")
    class ValidarFormatoCedulaTests {

        @Test
        @DisplayName("Debería validar cédula con formato correcto")
        void deberiaValidarCedulaConFormatoCorrecto() {
            // Arrange
            String cedulaValida = "12345678";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCedula(cedulaValida);

            // Assert
            assertTrue(resultado, "Debería retornar true para cédula con formato correcto");
        }

        @Test
        @DisplayName("Debería validar cédula con 7 dígitos")
        void deberiaValidarCedulaCon7Digitos() {
            // Arrange
            String cedula7Digitos = "1234567";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCedula(cedula7Digitos);

            // Assert
            assertTrue(resultado, "Debería retornar true para cédula con 7 dígitos");
        }

        @Test
        @DisplayName("Debería validar cédula con 10 dígitos")
        void deberiaValidarCedulaCon10Digitos() {
            // Arrange
            String cedula10Digitos = "1234567890";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCedula(cedula10Digitos);

            // Assert
            assertTrue(resultado, "Debería retornar true para cédula con 10 dígitos");
        }

        @Test
        @DisplayName("Debería retornar false cuando cédula es null")
        void deberiaRetornarFalseCuandoCedulaEsNull() {
            // Arrange
            String cedulaNull = null;

            // Act
            boolean resultado = RRHHValidador.validarFormatoCedula(cedulaNull);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando la cédula es null");
        }

        @Test
        @DisplayName("Debería retornar false cuando cédula está vacía")
        void deberiaRetornarFalseCuandoCedulaEstaVacia() {
            // Arrange
            String cedulaVacia = "";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCedula(cedulaVacia);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando la cédula está vacía");
        }

        @Test
        @DisplayName("Debería retornar false cuando cédula es muy corta")
        void deberiaRetornarFalseCuandoCedulaEsMuyCorta() {
            // Arrange
            String cedulaCorta = "123456";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCedula(cedulaCorta);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando la cédula es muy corta");
        }

        @Test
        @DisplayName("Debería retornar false cuando cédula es muy larga")
        void deberiaRetornarFalseCuandoCedulaEsMuyLarga() {
            // Arrange
            String cedulaLarga = "12345678901";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCedula(cedulaLarga);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando la cédula es muy larga");
        }

        @Test
        @DisplayName("Debería retornar false cuando cédula tiene letras")
        void deberiaRetornarFalseCuandoCedulaTieneLetras() {
            // Arrange
            String cedulaConLetras = "12345ABC";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCedula(cedulaConLetras);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando la cédula tiene letras");
        }

        @Test
        @DisplayName("Debería retornar false cuando cédula tiene caracteres especiales")
        void deberiaRetornarFalseCuandoCedulaTieneCaracteresEspeciales() {
            // Arrange
            String cedulaConCaracteresEspeciales = "123-456-78";

            // Act
            boolean resultado = RRHHValidador.validarFormatoCedula(cedulaConCaracteresEspeciales);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando la cédula tiene caracteres especiales");
        }
    }

    @Nested
    @DisplayName("validarEmpleado Tests")
    class ValidarEmpleadoTests {

        @Test
        @DisplayName("Debería validar empleado completo y válido")
        void deberiaValidarEmpleadoCompletoYValido() {
            // Arrange
            RRHH empleadoValido = new RRHH(1, "Juan", "Pérez", "12345678", 
                                          "555-1234", "juan@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = RRHHValidador.validarEmpleado(empleadoValido);

            // Assert
            assertTrue(resultado, "Debería retornar true para empleado completo y válido");
        }

        @Test
        @DisplayName("Debería retornar false cuando empleado es null")
        void deberiaRetornarFalseCuandoEmpleadoEsNull() {
            // Arrange
            RRHH empleadoNull = null;

            // Act
            boolean resultado = RRHHValidador.validarEmpleado(empleadoNull);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el empleado es null");
        }

        @Test
        @DisplayName("Debería retornar false cuando campos obligatorios son inválidos")
        void deberiaRetornarFalseCuandoCamposObligatoriosSonInvalidos() {
            // Arrange
            RRHH empleadoInvalido = new RRHH(1, "", "", "", "", "", "", "");

            // Act
            boolean resultado = RRHHValidador.validarEmpleado(empleadoInvalido);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando los campos obligatorios son inválidos");
        }

        @Test
        @DisplayName("Debería retornar false cuando cédula tiene formato inválido")
        void deberiaRetornarFalseCuandoCedulaTieneFormatoInvalido() {
            // Arrange
            RRHH empleadoCedulaInvalida = new RRHH(1, "Juan", "Pérez", "123-456", 
                                                  "555-1234", "juan@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = RRHHValidador.validarEmpleado(empleadoCedulaInvalida);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando la cédula tiene formato inválido");
        }

        @Test
        @DisplayName("Debería retornar false cuando correo tiene formato inválido")
        void deberiaRetornarFalseCuandoCorreoTieneFormatoInvalido() {
            // Arrange
            RRHH empleadoCorreoInvalido = new RRHH(1, "Juan", "Pérez", "12345678", 
                                                  "555-1234", "juan.email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = RRHHValidador.validarEmpleado(empleadoCorreoInvalido);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el correo tiene formato inválido");
        }

        @Test
        @DisplayName("Debería retornar false cuando teléfono tiene formato inválido")
        void deberiaRetornarFalseCuandoTelefonoTieneFormatoInvalido() {
            // Arrange
            RRHH empleadoTelefonoInvalido = new RRHH(1, "Juan", "Pérez", "12345678", 
                                                    "ABC-1234", "juan@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = RRHHValidador.validarEmpleado(empleadoTelefonoInvalido);

            // Assert
            assertFalse(resultado, "Debería retornar false cuando el teléfono tiene formato inválido");
        }

        @Test
        @DisplayName("Debería validar empleado con correo null (opcional)")
        void deberiaValidarEmpleadoConCorreoNull() {
            // Arrange
            RRHH empleadoSinCorreo = new RRHH(1, "Juan", "Pérez", "12345678", 
                                             "555-1234", null, "Veterinario", "Cirugía");

            // Act
            boolean resultado = RRHHValidador.validarEmpleado(empleadoSinCorreo);

            // Assert
            assertTrue(resultado, "Debería retornar true para empleado sin correo (opcional)");
        }

        @Test
        @DisplayName("Debería validar empleado con teléfono null (opcional)")
        void deberiaValidarEmpleadoConTelefonoNull() {
            // Arrange
            RRHH empleadoSinTelefono = new RRHH(1, "Juan", "Pérez", "12345678", 
                                               null, "juan@email.com", "Veterinario", "Cirugía");

            // Act
            boolean resultado = RRHHValidador.validarEmpleado(empleadoSinTelefono);

            // Assert
            assertTrue(resultado, "Debería retornar true para empleado sin teléfono (opcional)");
        }
    }
} 