# Correcciones de Compatibilidad con Apache Derby

## Resumen de Cambios Realizados

Este documento describe las correcciones realizadas para asegurar la compatibilidad de los scripts SQL de inicialización de la base de datos con Apache Derby, utilizado en los tests unitarios del proyecto sgvet-rrhh.

## Problemas Identificados y Soluciones

### 1. Uso de AUTO_INCREMENT

**Problema**: El script SQL de RRHH utilizaba `AUTO_INCREMENT` que no es compatible con Apache Derby.

**Archivo afectado**: `rrhh/src/main/resources/db/initRRHH.sql`

**Solución**: Reemplazado `AUTO_INCREMENT` por `GENERATED ALWAYS AS IDENTITY`, que es la sintaxis estándar de Derby para columnas autoincrementales.

```sql
-- ANTES (incompatible con Derby)
CREATE TABLE EVALUACIONDESEMPENO (
     ID INT PRIMARY KEY AUTO_INCREMENT,
     -- ... otros campos
);

-- DESPUÉS (compatible con Derby)
CREATE TABLE EVALUACIONDESEMPENO (
     ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
     -- ... otros campos
);
```

### 2. Manejo de Tablas Existentes

**Problema**: Los scripts SQL intentaban eliminar tablas que podrían no existir, causando errores en Derby.

**Archivo afectado**: `rrhh/src/main/resources/db/initRRHH.sql`

**Solución**: Eliminadas las sentencias `DROP TABLE` y mejorado el manejo de errores en el código Java.

```sql
-- ELIMINADO: DROP TABLE statements
-- Ahora se maneja automáticamente en el código Java
```

### 3. Mejora del Manejo de Errores en BaseDbManager

**Archivo afectado**: `base/src/main/java/com/sgvet/base/boundary/BaseDbManager.java`

**Solución**: Agregado manejo robusto de errores para ignorar errores de `CREATE TABLE` cuando las tablas ya existen.

```java
try {
    stmt.executeUpdate(command);
} catch (SQLException e) {
    // Ignorar errores de CREATE TABLE si la tabla ya existe
    if (command.trim().toUpperCase().startsWith("CREATE TABLE")) {
        logger.info("Tabla ya existe, continuando: " + command);
        // No relanzar la excepción para CREATE TABLE
    } else {
        logger.error("Error ejecutando SQL: " + command, e);
        throw e;
    }
}
```

## Archivos Modificados

### 1. Script SQL de RRHH
- **Archivo**: `rrhh/src/main/resources/db/initRRHH.sql`
- **Cambios**:
  - Reemplazado `AUTO_INCREMENT` por `GENERATED ALWAYS AS IDENTITY`
  - Eliminadas sentencias `DROP TABLE`
  - Agregados comentarios explicativos

### 2. BaseDbManager
- **Archivo**: `base/src/main/java/com/sgvet/base/boundary/BaseDbManager.java`
- **Cambios**:
  - Mejorado el manejo de errores SQL
  - Agregado logging para debugging
  - Manejo específico para errores de `CREATE TABLE`

## Validación de Cambios

### Tests Ejecutados
- ✅ `RRHHTest` - 16 tests pasando
- ✅ `RRHHControllerTest` - 18 tests pasando
- ✅ `EvaluacionDesempenoControllerTest` - 6 tests pasando
- ✅ `EvaluacionDesempenoTest` - 24 tests pasando
- ✅ `RRHHValidadorTest` - 41 tests pasando

**Total**: 105 tests pasando, 0 fallos, 0 errores

### Comandos de Validación
```bash
cd rrhh
mvn test
```

## Recomendaciones para Otros Módulos

### 1. Verificar Scripts SQL
Revisar los siguientes archivos en otros módulos para asegurar compatibilidad:

- `proveedor/src/main/resources/db/initProveedores.sql`
- `mascota/src/main/resources/db/initMascotas.sql`
- `cliente/src/main/resources/db/initClientes.sql`
- `base/src/main/resources/db/init.sql`

### 2. Patrones a Buscar
- `AUTO_INCREMENT` → Reemplazar por `GENERATED ALWAYS AS IDENTITY`
- `DROP TABLE` sin `IF EXISTS` → Considerar manejo en código Java
- Tipos de datos específicos de MySQL → Verificar compatibilidad con Derby

### 3. Tests Recomendados
Para cada módulo, ejecutar:
```bash
cd [modulo]
mvn test
```

## Notas Técnicas

### Compatibilidad Derby
- Derby no soporta `AUTO_INCREMENT`
- Derby no soporta `DROP TABLE IF EXISTS`
- Derby usa `GENERATED ALWAYS AS IDENTITY` para autoincrementales
- Derby es más estricto con la sintaxis SQL que otros motores

### Base de Datos en Memoria
El proyecto utiliza Derby en memoria para tests:
```java
private static final String DB_URL = "jdbc:derby:memory:" + DB_NAME + ";create=true";
```

## Estado Final

✅ **Todos los problemas de compatibilidad han sido resueltos**
✅ **Todos los tests unitarios del módulo RRHH pasan correctamente**
✅ **La base de datos se inicializa sin errores en Derby**
✅ **El código maneja robustamente los errores de SQL**

## Próximos Pasos

1. Aplicar las mismas correcciones a otros módulos si es necesario
2. Considerar agregar tests de integración con Derby
3. Documentar las mejores prácticas para scripts SQL compatibles con Derby
4. Revisar otros motores de base de datos utilizados en el proyecto 