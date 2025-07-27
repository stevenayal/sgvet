# Pruebas Unitarias - Módulo RRHH

Este documento describe las pruebas unitarias implementadas para el módulo RRHH del proyecto SGVet.

## Estructura de Pruebas

Las pruebas unitarias están organizadas en la siguiente estructura:

```
src/test/java/com/sgvet/rrhh/
├── control/
│   ├── RRHHControllerTest.java
│   └── EvaluacionDesempenoControllerTest.java
├── entity/
│   ├── RRHHTest.java
│   └── EvaluacionDesempenoTest.java
└── util/
    └── RRHHValidadorTest.java
```

## Clases Cubiertas

### Control Layer
- **RRHHControllerTest**: Pruebas para la lógica de negocio de RRHH
  - Crear RRHH
  - Listar RRHHs
  - Eliminar RRHH
  - Buscar RRHH
  - Solicitar vacaciones
  - Solicitar permisos
  - Actualizar empleado

- **EvaluacionDesempenoControllerTest**: Pruebas para la gestión de evaluaciones
  - Registrar evaluación
  - Obtener evaluaciones por empleado

### Entity Layer
- **RRHHTest**: Pruebas para la entidad RRHH
  - Constructores
  - Getters y setters
  - Método toString

- **EvaluacionDesempenoTest**: Pruebas para la entidad EvaluacionDesempeno
  - Constructores
  - Getters y setters
  - Cálculo de calificación final

### Util Layer
- **RRHHValidadorTest**: Pruebas para las validaciones
  - Validación de campos obligatorios
  - Validación de formato de correo
  - Validación de formato de teléfono
  - Validación de formato de cédula
  - Validación completa de empleado

## Tecnologías Utilizadas

- **JUnit 5**: Framework de pruebas unitarias
- **Mockito**: Framework para mocking de dependencias
- **Maven**: Gestión de dependencias y ejecución de pruebas

## Dependencias

Las dependencias están configuradas en `pom.xml`:

```xml
<!-- JUnit 5 -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>

<!-- Mockito -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.7.0</version>
    <scope>test</scope>
</dependency>

<!-- Mockito JUnit Jupiter Integration -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>5.7.0</version>
    <scope>test</scope>
</dependency>
```

## Ejecución de Pruebas

### Ejecutar todas las pruebas
```bash
mvn test
```

### Ejecutar pruebas de una clase específica
```bash
mvn test -Dtest=RRHHControllerTest
```

### Ejecutar un método de prueba específico
```bash
mvn test -Dtest=RRHHControllerTest#deberiaCrearRRHHExitosamente
```

### Ejecutar pruebas con cobertura (requiere plugin JaCoCo)
```bash
mvn clean test jacoco:report
```

## Cobertura de Pruebas

Las pruebas cubren:

### Casos Normales
- Funcionalidad básica de cada método
- Flujos exitosos
- Validaciones correctas

### Casos de Borde
- Valores límite (0, 1, valores máximos)
- Cadenas vacías
- Valores null
- IDs negativos o cero

### Casos de Error
- Excepciones de base de datos
- Datos inválidos
- Empleados inexistentes
- Formatos incorrectos

## Convenciones de Nomenclatura

### Archivos de Prueba
- Nombres terminados en `Test` o `Tests`
- Ubicados en el mismo paquete que la clase bajo prueba
- Estructura: `src/test/java/[paquete]/[ClaseTest].java`

### Métodos de Prueba
- Nombres descriptivos en español
- Formato: `deberia[Accion][Condicion]`
- Ejemplos:
  - `deberiaCrearRRHHExitosamente()`
  - `deberiaRetornarFalseCuandoEmpleadoEsNull()`
  - `deberiaManejarExcepcionAlActualizarEmpleado()`

### Anotaciones JUnit 5
- `@Test`: Métodos de prueba
- `@DisplayName`: Nombres legibles para reportes
- `@Nested`: Agrupación de pruebas relacionadas
- `@BeforeEach`: Configuración antes de cada prueba

## Mocking

Se utiliza Mockito para:

### Mocking de Dependencias
- `RRHHRepository`: Acceso a base de datos
- `EvaluacionDesempenoRepository`: Operaciones de evaluación
- `RRHHValidador`: Validaciones

### Verificaciones
- `verify()`: Verificar que los métodos mock fueron llamados
- `when()`: Configurar comportamiento de mocks
- `doThrow()`: Simular excepciones

## Ejemplos de Uso

### Prueba con Mocking
```java
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
```

### Prueba de Validación
```java
@Test
@DisplayName("Debería validar empleado con todos los campos obligatorios")
void deberiaValidarEmpleadoConTodosLosCamposObligatorios() {
    // Arrange
    RRHH empleadoValido = new RRHH(1, "Juan", "Pérez", "12345678", 
                                  "555-1234", "juan@email.com", "Veterinario", "Cirugía");

    // Act
    boolean resultado = RRHHValidador.validarEmpleado(empleadoValido);

    // Assert
    assertTrue(resultado, "Debería retornar true para empleado con todos los campos obligatorios");
}
```

## Integración con CI/CD

Las pruebas están configuradas para ejecutarse automáticamente en:

### Jenkins Pipeline
- Ejecución en cada build
- Reportes de cobertura
- Quality Gates de SonarQube

### SonarQube
- Análisis de cobertura de código
- Detección de code smells
- Métricas de calidad

## Mantenimiento

### Agregar Nuevas Pruebas
1. Crear archivo de prueba en el paquete correspondiente
2. Seguir convenciones de nomenclatura
3. Incluir casos normales, de borde y errores
4. Documentar casos especiales

### Actualizar Pruebas Existentes
1. Mantener compatibilidad con cambios en el código
2. Actualizar mocks si cambian las dependencias
3. Verificar que las aserciones siguen siendo válidas

## Troubleshooting

### Errores Comunes

#### Dependencias no encontradas
```bash
mvn clean install
```

#### Pruebas que fallan
- Verificar que los mocks están configurados correctamente
- Revisar que las aserciones coinciden con el comportamiento esperado
- Comprobar que no hay efectos secundarios entre pruebas

#### Problemas de compilación
- Verificar que Java 11 está configurado
- Limpiar y recompilar: `mvn clean compile`

## Contacto

Para preguntas sobre las pruebas unitarias, contactar al equipo de desarrollo. 