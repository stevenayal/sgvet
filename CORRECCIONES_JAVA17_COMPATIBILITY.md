# Correcciones de Compatibilidad con Java 17

## Problema Resuelto

El error original era:
```
Unrecognized VM option 'MaxPermSize=512m'
Error: Could not create the Java Virtual Machine.
```

Este error ocurría porque Maven se ejecutaba con Java 17 y la opción `-XX:MaxPermSize=512m` no es válida para esa versión de Java (ni en Java 8+).

## Cambios Realizados

### 1. **Jenkinsfile - Corrección de MAVEN_OPTS**

**Antes:**
```groovy
environment {
    MAVEN_OPTS = '-Xmx2048m -XX:MaxPermSize=512m'
}
```

**Después:**
```groovy
environment {
    MAVEN_OPTS = '-Xmx2048m -XX:MaxMetaspaceSize=512m'
}
```

### 2. **Documentación Actualizada**

El archivo `JENKINSFILE_MEJORADO.md` también fue actualizado para reflejar el cambio.

## Explicación Técnica

### **¿Por qué fallaba MaxPermSize?**

- **Java 7 y anteriores**: Usaban `PermGen` (Permanent Generation) para almacenar metadatos de clases
- **Java 8+**: Reemplazaron `PermGen` con `Metaspace`
- **MaxPermSize**: Era la opción para limitar el tamaño de `PermGen`
- **MaxMetaspaceSize**: Es la opción equivalente para `Metaspace` en Java 8+

### **Opciones de Memoria Compatibles con Java 17**

#### **Opciones Válidas:**
- `-Xmx2048m` - Memoria heap máxima
- `-Xms1024m` - Memoria heap inicial
- `-XX:MaxMetaspaceSize=512m` - Tamaño máximo de Metaspace
- `-XX:MetaspaceSize=256m` - Tamaño inicial de Metaspace

#### **Opciones Obsoletas (NO usar):**
- `-XX:MaxPermSize=512m` - Obsoleto desde Java 8
- `-XX:PermSize=256m` - Obsoleto desde Java 8

## Verificación de Configuración

### **En el Jenkinsfile:**
```groovy
stage('Preparación') {
    steps {
        script {
            // Verificar configuración de Java y Maven
            sh 'java -version'
            sh 'mvn -version'
            
            // Verificar variables de entorno
            echo "MAVEN_OPTS: ${env.MAVEN_OPTS}"
            echo "JAVA_HOME: ${env.JAVA_HOME}"
        }
    }
}
```

### **En pom.xml (RRHH):**
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <argLine>${jacocoArgLine} -Xmx1024m -XX:MaxMetaspaceSize=256m</argLine>
    </configuration>
</plugin>
```

## Archivos Modificados

1. **`Jenkinsfile`** - Línea 14: `MAVEN_OPTS` actualizada
2. **`JENKINSFILE_MEJORADO.md`** - Documentación actualizada

## Archivos Verificados (Sin Cambios Necesarios)

1. **`rrhh/pom.xml`** - Ya usa `MaxMetaspaceSize` correctamente

## Pasos Adicionales (Opcionales)

### **1. Configuración Global de Jenkins**

Si tienes variables de entorno globales en Jenkins que contengan `MaxPermSize`:

1. Ve a `Manage Jenkins` → `Configure System`
2. Busca variables de entorno globales
3. Reemplaza `MaxPermSize` por `MaxMetaspaceSize`

### **2. Variables de Entorno del Agente**

Si el agente de Jenkins tiene variables de entorno con `MaxPermSize`:

```bash
# En lugar de:
export MAVEN_OPTS="-Xmx2048m -XX:MaxPermSize=512m"

# Usar:
export MAVEN_OPTS="-Xmx2048m -XX:MaxMetaspaceSize=512m"
```

### **3. Scripts de Inicialización**

Revisar scripts `.bashrc`, `.profile`, o scripts de inicialización del sistema:

```bash
# Buscar y reemplazar:
grep -r "MaxPermSize" /etc/profile.d/ /home/jenkins/ /opt/jenkins/
```

## Recomendaciones para Java 17

### **Configuración Óptima de Memoria:**

```groovy
environment {
    MAVEN_OPTS = '-Xmx2048m -XX:MaxMetaspaceSize=512m -XX:+UseG1GC'
}
```

### **Para Pruebas Unitarias:**

```xml
<argLine>${jacocoArgLine} -Xmx1024m -XX:MaxMetaspaceSize=256m</argLine>
```

### **Para Compilación:**

```groovy
sh 'mvn clean compile -Xmx2048m -XX:MaxMetaspaceSize=512m'
```

## Verificación de Funcionamiento

### **Comandos de Prueba:**

```bash
# Verificar versión de Java
java -version

# Verificar que Maven funciona
mvn -version

# Verificar variables de entorno
echo $MAVEN_OPTS
echo $JAVA_OPTS

# Probar compilación
mvn clean compile
```

### **Resultados Esperados:**

- ✅ No errores de `MaxPermSize`
- ✅ Maven se ejecuta correctamente
- ✅ Compilación exitosa
- ✅ Pruebas unitarias funcionan

## Notas Importantes

1. **Compatibilidad**: Los cambios son compatibles con Java 8, 11, 17 y versiones posteriores
2. **Rendimiento**: `Metaspace` es más eficiente que `PermGen`
3. **Flexibilidad**: `Metaspace` se expande automáticamente según sea necesario
4. **Monitoreo**: Usar `jstat -gcmetacapacity` para monitorear Metaspace

## Estado de la Corrección

- ✅ **Jenkinsfile**: Corregido
- ✅ **Documentación**: Actualizada
- ✅ **pom.xml**: Ya compatible
- ✅ **Verificación**: Completada

El pipeline ahora es completamente compatible con Java 17 y no debería presentar errores de opciones de JVM incompatibles. 