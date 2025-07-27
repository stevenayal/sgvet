package com.sgvet.rrhh.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la entidad EvaluacionDesempeno
 * Cubre constructores, getters, setters y métodos
 */
@DisplayName("EvaluacionDesempeno Entity Tests")
class EvaluacionDesempenoTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Debería crear EvaluacionDesempeno con constructor vacío")
        void deberiaCrearEvaluacionDesempenoConConstructorVacio() {
            // Act
            EvaluacionDesempeno evaluacion = new EvaluacionDesempeno();

            // Assert
            assertNotNull(evaluacion, "El objeto EvaluacionDesempeno no debería ser null");
            assertEquals(0, evaluacion.getId(), "El ID debería ser 0 inicialmente");
            assertEquals(0, evaluacion.getIdEmpleado(), "El ID del empleado debería ser 0 inicialmente");
            assertNull(evaluacion.getFecha(), "La fecha debería ser null inicialmente");
            assertEquals(0, evaluacion.getPuntualidad(), "La puntualidad debería ser 0 inicialmente");
            assertEquals(0, evaluacion.getAtencionCliente(), "La atención al cliente debería ser 0 inicialmente");
            assertEquals(0, evaluacion.getTrabajoEquipo(), "El trabajo en equipo debería ser 0 inicialmente");
            assertEquals(0, evaluacion.getResponsabilidad(), "La responsabilidad debería ser 0 inicialmente");
            assertNull(evaluacion.getObservaciones(), "Las observaciones deberían ser null inicialmente");
        }

        @Test
        @DisplayName("Debería crear EvaluacionDesempeno con constructor completo")
        void deberiaCrearEvaluacionDesempenoConConstructorCompleto() {
            // Arrange
            int idEmpleado = 1;
            String fecha = "2024-01-15";
            int puntualidad = 8;
            int atencionCliente = 9;
            int trabajoEquipo = 7;
            int responsabilidad = 8;
            String observaciones = "Buen desempeño general";

            // Act
            EvaluacionDesempeno evaluacion = new EvaluacionDesempeno(idEmpleado, fecha, puntualidad, 
                                                                    atencionCliente, trabajoEquipo, 
                                                                    responsabilidad, observaciones);

            // Assert
            assertNotNull(evaluacion, "El objeto EvaluacionDesempeno no debería ser null");
            assertEquals(idEmpleado, evaluacion.getIdEmpleado(), "El ID del empleado debería coincidir");
            assertEquals(fecha, evaluacion.getFecha(), "La fecha debería coincidir");
            assertEquals(puntualidad, evaluacion.getPuntualidad(), "La puntualidad debería coincidir");
            assertEquals(atencionCliente, evaluacion.getAtencionCliente(), "La atención al cliente debería coincidir");
            assertEquals(trabajoEquipo, evaluacion.getTrabajoEquipo(), "El trabajo en equipo debería coincidir");
            assertEquals(responsabilidad, evaluacion.getResponsabilidad(), "La responsabilidad debería coincidir");
            assertEquals(observaciones, evaluacion.getObservaciones(), "Las observaciones deberían coincidir");
        }

        @Test
        @DisplayName("Debería crear EvaluacionDesempeno con valores mínimos")
        void deberiaCrearEvaluacionDesempenoConValoresMinimos() {
            // Arrange
            int idEmpleado = 1;
            String fecha = "2024-01-15";
            int puntualidad = 1;
            int atencionCliente = 1;
            int trabajoEquipo = 1;
            int responsabilidad = 1;
            String observaciones = "";

            // Act
            EvaluacionDesempeno evaluacion = new EvaluacionDesempeno(idEmpleado, fecha, puntualidad, 
                                                                    atencionCliente, trabajoEquipo, 
                                                                    responsabilidad, observaciones);

            // Assert
            assertNotNull(evaluacion, "El objeto EvaluacionDesempeno no debería ser null");
            assertEquals(idEmpleado, evaluacion.getIdEmpleado(), "El ID del empleado debería coincidir");
            assertEquals(fecha, evaluacion.getFecha(), "La fecha debería coincidir");
            assertEquals(puntualidad, evaluacion.getPuntualidad(), "La puntualidad debería coincidir");
            assertEquals(atencionCliente, evaluacion.getAtencionCliente(), "La atención al cliente debería coincidir");
            assertEquals(trabajoEquipo, evaluacion.getTrabajoEquipo(), "El trabajo en equipo debería coincidir");
            assertEquals(responsabilidad, evaluacion.getResponsabilidad(), "La responsabilidad debería coincidir");
            assertEquals(observaciones, evaluacion.getObservaciones(), "Las observaciones deberían coincidir");
        }

        @Test
        @DisplayName("Debería crear EvaluacionDesempeno con valores máximos")
        void deberiaCrearEvaluacionDesempenoConValoresMaximos() {
            // Arrange
            int idEmpleado = 999;
            String fecha = "2024-12-31";
            int puntualidad = 10;
            int atencionCliente = 10;
            int trabajoEquipo = 10;
            int responsabilidad = 10;
            String observaciones = "Excelente desempeño en todos los aspectos evaluados";

            // Act
            EvaluacionDesempeno evaluacion = new EvaluacionDesempeno(idEmpleado, fecha, puntualidad, 
                                                                    atencionCliente, trabajoEquipo, 
                                                                    responsabilidad, observaciones);

            // Assert
            assertNotNull(evaluacion, "El objeto EvaluacionDesempeno no debería ser null");
            assertEquals(idEmpleado, evaluacion.getIdEmpleado(), "El ID del empleado debería coincidir");
            assertEquals(fecha, evaluacion.getFecha(), "La fecha debería coincidir");
            assertEquals(puntualidad, evaluacion.getPuntualidad(), "La puntualidad debería coincidir");
            assertEquals(atencionCliente, evaluacion.getAtencionCliente(), "La atención al cliente debería coincidir");
            assertEquals(trabajoEquipo, evaluacion.getTrabajoEquipo(), "El trabajo en equipo debería coincidir");
            assertEquals(responsabilidad, evaluacion.getResponsabilidad(), "La responsabilidad debería coincidir");
            assertEquals(observaciones, evaluacion.getObservaciones(), "Las observaciones deberían coincidir");
        }
    }

    @Nested
    @DisplayName("Getters and Setters Tests")
    class GettersAndSettersTests {

        private EvaluacionDesempeno evaluacion;

        @BeforeEach
        void setUp() {
            evaluacion = new EvaluacionDesempeno();
        }

        @Test
        @DisplayName("Debería establecer y obtener ID correctamente")
        void deberiaEstablecerYObtenerIDCorrectamente() {
            // Arrange
            int id = 1;

            // Act
            evaluacion.setId(id);

            // Assert
            assertEquals(id, evaluacion.getId(), "El ID debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener ID del empleado correctamente")
        void deberiaEstablecerYObtenerIDDelEmpleadoCorrectamente() {
            // Arrange
            int idEmpleado = 123;

            // Act
            evaluacion.setIdEmpleado(idEmpleado);

            // Assert
            assertEquals(idEmpleado, evaluacion.getIdEmpleado(), "El ID del empleado debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener fecha correctamente")
        void deberiaEstablecerYObtenerFechaCorrectamente() {
            // Arrange
            String fecha = "2024-06-15";

            // Act
            evaluacion.setFecha(fecha);

            // Assert
            assertEquals(fecha, evaluacion.getFecha(), "La fecha debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener puntualidad correctamente")
        void deberiaEstablecerYObtenerPuntualidadCorrectamente() {
            // Arrange
            int puntualidad = 9;

            // Act
            evaluacion.setPuntualidad(puntualidad);

            // Assert
            assertEquals(puntualidad, evaluacion.getPuntualidad(), "La puntualidad debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener atención al cliente correctamente")
        void deberiaEstablecerYObtenerAtencionAlClienteCorrectamente() {
            // Arrange
            int atencionCliente = 8;

            // Act
            evaluacion.setAtencionCliente(atencionCliente);

            // Assert
            assertEquals(atencionCliente, evaluacion.getAtencionCliente(), "La atención al cliente debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener trabajo en equipo correctamente")
        void deberiaEstablecerYObtenerTrabajoEnEquipoCorrectamente() {
            // Arrange
            int trabajoEquipo = 7;

            // Act
            evaluacion.setTrabajoEquipo(trabajoEquipo);

            // Assert
            assertEquals(trabajoEquipo, evaluacion.getTrabajoEquipo(), "El trabajo en equipo debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener responsabilidad correctamente")
        void deberiaEstablecerYObtenerResponsabilidadCorrectamente() {
            // Arrange
            int responsabilidad = 10;

            // Act
            evaluacion.setResponsabilidad(responsabilidad);

            // Assert
            assertEquals(responsabilidad, evaluacion.getResponsabilidad(), "La responsabilidad debería coincidir");
        }

        @Test
        @DisplayName("Debería establecer y obtener observaciones correctamente")
        void deberiaEstablecerYObtenerObservacionesCorrectamente() {
            // Arrange
            String observaciones = "Muy buen desempeño en el equipo";

            // Act
            evaluacion.setObservaciones(observaciones);

            // Assert
            assertEquals(observaciones, evaluacion.getObservaciones(), "Las observaciones deberían coincidir");
        }

        @Test
        @DisplayName("Debería manejar valores negativos en calificaciones")
        void deberiaManejarValoresNegativosEnCalificaciones() {
            // Arrange
            int valorNegativo = -5;

            // Act
            evaluacion.setPuntualidad(valorNegativo);
            evaluacion.setAtencionCliente(valorNegativo);
            evaluacion.setTrabajoEquipo(valorNegativo);
            evaluacion.setResponsabilidad(valorNegativo);

            // Assert
            assertEquals(valorNegativo, evaluacion.getPuntualidad(), "La puntualidad debería aceptar valores negativos");
            assertEquals(valorNegativo, evaluacion.getAtencionCliente(), "La atención al cliente debería aceptar valores negativos");
            assertEquals(valorNegativo, evaluacion.getTrabajoEquipo(), "El trabajo en equipo debería aceptar valores negativos");
            assertEquals(valorNegativo, evaluacion.getResponsabilidad(), "La responsabilidad debería aceptar valores negativos");
        }

        @Test
        @DisplayName("Debería manejar valores mayores a 10 en calificaciones")
        void deberiaManejarValoresMayoresA10EnCalificaciones() {
            // Arrange
            int valorAlto = 15;

            // Act
            evaluacion.setPuntualidad(valorAlto);
            evaluacion.setAtencionCliente(valorAlto);
            evaluacion.setTrabajoEquipo(valorAlto);
            evaluacion.setResponsabilidad(valorAlto);

            // Assert
            assertEquals(valorAlto, evaluacion.getPuntualidad(), "La puntualidad debería aceptar valores mayores a 10");
            assertEquals(valorAlto, evaluacion.getAtencionCliente(), "La atención al cliente debería aceptar valores mayores a 10");
            assertEquals(valorAlto, evaluacion.getTrabajoEquipo(), "El trabajo en equipo debería aceptar valores mayores a 10");
            assertEquals(valorAlto, evaluacion.getResponsabilidad(), "La responsabilidad debería aceptar valores mayores a 10");
        }

        @Test
        @DisplayName("Debería manejar fecha null")
        void deberiaManejarFechaNull() {
            // Act
            evaluacion.setFecha(null);

            // Assert
            assertNull(evaluacion.getFecha(), "La fecha debería ser null");
        }

        @Test
        @DisplayName("Debería manejar observaciones null")
        void deberiaManejarObservacionesNull() {
            // Act
            evaluacion.setObservaciones(null);

            // Assert
            assertNull(evaluacion.getObservaciones(), "Las observaciones deberían ser null");
        }
    }

    @Nested
    @DisplayName("getCalificacionFinal Tests")
    class GetCalificacionFinalTests {

        @Test
        @DisplayName("Debería calcular calificación final correctamente")
        void deberiaCalcularCalificacionFinalCorrectamente() {
            // Arrange
            EvaluacionDesempeno evaluacion = new EvaluacionDesempeno(1, "2024-01-15", 8, 9, 7, 8, "Buen desempeño");

            // Act
            double resultado = evaluacion.getCalificacionFinal();

            // Assert
            assertEquals(8.0, resultado, 0.01, "La calificación final debería ser 8.0");
        }

        @Test
        @DisplayName("Debería calcular calificación final con valores iguales")
        void deberiaCalcularCalificacionFinalConValoresIguales() {
            // Arrange
            EvaluacionDesempeno evaluacion = new EvaluacionDesempeno(1, "2024-01-15", 10, 10, 10, 10, "Perfecto");

            // Act
            double resultado = evaluacion.getCalificacionFinal();

            // Assert
            assertEquals(10.0, resultado, 0.01, "La calificación final debería ser 10.0");
        }

        @Test
        @DisplayName("Debería calcular calificación final con valores mínimos")
        void deberiaCalcularCalificacionFinalConValoresMinimos() {
            // Arrange
            EvaluacionDesempeno evaluacion = new EvaluacionDesempeno(1, "2024-01-15", 1, 1, 1, 1, "Mínimo");

            // Act
            double resultado = evaluacion.getCalificacionFinal();

            // Assert
            assertEquals(1.0, resultado, 0.01, "La calificación final debería ser 1.0");
        }

        @Test
        @DisplayName("Debería calcular calificación final con valores diferentes")
        void deberiaCalcularCalificacionFinalConValoresDiferentes() {
            // Arrange
            EvaluacionDesempeno evaluacion = new EvaluacionDesempeno(1, "2024-01-15", 5, 8, 6, 9, "Variado");

            // Act
            double resultado = evaluacion.getCalificacionFinal();

            // Assert
            assertEquals(7.0, resultado, 0.01, "La calificación final debería ser 7.0");
        }

        @Test
        @DisplayName("Debería calcular calificación final con decimales")
        void deberiaCalcularCalificacionFinalConDecimales() {
            // Arrange
            EvaluacionDesempeno evaluacion = new EvaluacionDesempeno(1, "2024-01-15", 7, 8, 6, 9, "Con decimales");

            // Act
            double resultado = evaluacion.getCalificacionFinal();

            // Assert
            assertEquals(7.5, resultado, 0.01, "La calificación final debería ser 7.5");
        }

        @Test
        @DisplayName("Debería calcular calificación final con valores negativos")
        void deberiaCalcularCalificacionFinalConValoresNegativos() {
            // Arrange
            EvaluacionDesempeno evaluacion = new EvaluacionDesempeno(1, "2024-01-15", -2, -1, -3, -2, "Negativos");

            // Act
            double resultado = evaluacion.getCalificacionFinal();

            // Assert
            assertEquals(-2.0, resultado, 0.01, "La calificación final debería ser -2.0");
        }

        @Test
        @DisplayName("Debería calcular calificación final con valores altos")
        void deberiaCalcularCalificacionFinalConValoresAltos() {
            // Arrange
            EvaluacionDesempeno evaluacion = new EvaluacionDesempeno(1, "2024-01-15", 15, 12, 18, 14, "Valores altos");

            // Act
            double resultado = evaluacion.getCalificacionFinal();

            // Assert
            assertEquals(14.75, resultado, 0.01, "La calificación final debería ser 14.75");
        }

        @Test
        @DisplayName("Debería calcular calificación final con valores cero")
        void deberiaCalcularCalificacionFinalConValoresCero() {
            // Arrange
            EvaluacionDesempeno evaluacion = new EvaluacionDesempeno(1, "2024-01-15", 0, 0, 0, 0, "Ceros");

            // Act
            double resultado = evaluacion.getCalificacionFinal();

            // Assert
            assertEquals(0.0, resultado, 0.01, "La calificación final debería ser 0.0");
        }
    }
}
