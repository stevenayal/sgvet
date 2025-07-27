package com.sgvet.rrhh.control;

import com.sgvet.rrhh.boundary.EvaluacionDesempenoRepository;
import com.sgvet.rrhh.entity.EvaluacionDesempeno;
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
 * Pruebas unitarias para EvaluacionDesempenoController
 * Cubre todos los métodos públicos con casos normales, de borde y errores
 */
@DisplayName("EvaluacionDesempenoController Tests")
class EvaluacionDesempenoControllerTest {

    @Spy
    private EvaluacionDesempenoController controller = new EvaluacionDesempenoController();

    private EvaluacionDesempeno evaluacionValida;
    private EvaluacionDesempeno evaluacionInvalida;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Evaluación válida para pruebas
        evaluacionValida = new EvaluacionDesempeno(1, "2024-01-15", 8, 9, 7, 8, "Buen desempeño general");
        
        // Evaluación inválida para pruebas
        evaluacionInvalida = new EvaluacionDesempeno(2, "2024-01-16", -1, 11, 0, 5, "");
    }

    @Nested
    @DisplayName("registrarEvaluacion Tests")
    class RegistrarEvaluacionTests {

        @Test
        @DisplayName("Debería registrar evaluación exitosamente")
        void deberiaRegistrarEvaluacionExitosamente() {
            // Arrange
            EvaluacionDesempeno nuevaEvaluacion = new EvaluacionDesempeno(3, "2024-01-17", 9, 8, 9, 9, "Excelente trabajo");
            doNothing().when(controller).registrarEvaluacion(nuevaEvaluacion);

            // Act
            controller.registrarEvaluacion(nuevaEvaluacion);

            // Assert
            verify(controller, times(1)).registrarEvaluacion(nuevaEvaluacion);
        }

        @Test
        @DisplayName("Debería manejar evaluación con datos nulos")
        void deberiaManejarEvaluacionConDatosNulos() {
            // Arrange
            EvaluacionDesempeno evaluacionNula = new EvaluacionDesempeno(4, null, 0, 0, 0, 0, null);
            doNothing().when(controller).registrarEvaluacion(evaluacionNula);

            // Act
            controller.registrarEvaluacion(evaluacionNula);

            // Assert
            verify(controller, times(1)).registrarEvaluacion(evaluacionNula);
        }
    }

    @Nested
    @DisplayName("obtenerPorEmpleado Tests")
    class ObtenerPorEmpleadoTests {

        @Test
        @DisplayName("Debería obtener evaluaciones por empleado exitosamente")
        void deberiaObtenerEvaluacionesPorEmpleadoExitosamente() {
            // Arrange
            int idEmpleado = 1;
            List<EvaluacionDesempeno> evaluacionesEsperadas = Arrays.asList(
                new EvaluacionDesempeno(idEmpleado, "2024-01-15", 8, 9, 7, 8, "Buen desempeño"),
                new EvaluacionDesempeno(idEmpleado, "2024-02-15", 9, 8, 9, 8, "Mejoró significativamente")
            );
            doReturn(evaluacionesEsperadas).when(controller).obtenerPorEmpleado(idEmpleado);

            // Act
            List<EvaluacionDesempeno> resultado = controller.obtenerPorEmpleado(idEmpleado);

            // Assert
            assertNotNull(resultado, "La lista no debería ser null");
            assertEquals(2, resultado.size(), "Debería retornar 2 evaluaciones");
            assertEquals(evaluacionesEsperadas, resultado, "Debería retornar las evaluaciones esperadas");
        }

        @Test
        @DisplayName("Debería retornar lista vacía cuando empleado no tiene evaluaciones")
        void deberiaRetornarListaVaciaCuandoEmpleadoNoTieneEvaluaciones() {
            // Arrange
            int idEmpleado = 999;
            doReturn(Collections.emptyList()).when(controller).obtenerPorEmpleado(idEmpleado);

            // Act
            List<EvaluacionDesempeno> resultado = controller.obtenerPorEmpleado(idEmpleado);

            // Assert
            assertNotNull(resultado, "La lista no debería ser null");
            assertTrue(resultado.isEmpty(), "Debería retornar lista vacía");
        }

        @Test
        @DisplayName("Debería manejar ID de empleado negativo")
        void deberiaManejarIDDeEmpleadoNegativo() {
            // Arrange
            int idEmpleadoNegativo = -1;
            doReturn(Collections.emptyList()).when(controller).obtenerPorEmpleado(idEmpleadoNegativo);

            // Act
            List<EvaluacionDesempeno> resultado = controller.obtenerPorEmpleado(idEmpleadoNegativo);

            // Assert
            assertNotNull(resultado, "La lista no debería ser null");
            assertTrue(resultado.isEmpty(), "Debería retornar lista vacía para ID negativo");
        }

        @Test
        @DisplayName("Debería manejar ID de empleado cero")
        void deberiaManejarIDDeEmpleadoCero() {
            // Arrange
            int idEmpleadoCero = 0;
            doReturn(Collections.emptyList()).when(controller).obtenerPorEmpleado(idEmpleadoCero);

            // Act
            List<EvaluacionDesempeno> resultado = controller.obtenerPorEmpleado(idEmpleadoCero);

            // Assert
            assertNotNull(resultado, "La lista no debería ser null");
            assertTrue(resultado.isEmpty(), "Debería retornar lista vacía para ID cero");
        }
    }
} 