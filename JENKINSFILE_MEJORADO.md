# Jenkinsfile Mejorado - Configuración de JDK

## Problema Resuelto

El error original era:
```
Tool type "jdk" does not have an install of "jdk-11" configured - did you mean "null"?
```

## Soluciones Implementadas

### 1. **Configuración Automática (Recomendada)**
El Jenkinsfile ahora detecta automáticamente el JDK del sistema sin requerir configuración específica en Jenkins.

**Cambios realizados:**
- Comentada la línea `jdk 'jdk-11'` en la sección `tools`
- Comentada la línea `JAVA_HOME = tool('jdk-11')` en `environment`
- Agregada lógica de detección automática de Java en el stage de Preparación

### 2. **Opciones de Configuración en Jenkins**

#### Opción A: Usar JDK del Sistema (Actual)
- No requiere configuración adicional en Jenkins
- Usa el JDK instalado en el sistema operativo
- Funciona automáticamente en la mayoría de entornos

#### Opción B: Configurar JDK Específico en Jenkins
Si necesitas usar un JDK específico, sigue estos pasos:

1. **En Jenkins, ve a:**
   - `Manage Jenkins` → `Tools` → `JDK installations`

2. **Agrega una nueva instalación:**
   - **Name:** `jdk-11` (o el nombre que prefieras)
   - **JAVA_HOME:** `/path/to/your/jdk-11`
   - **Install automatically:** Marca si quieres que Jenkins lo descargue automáticamente

3. **Descomenta en el Jenkinsfile:**
   ```groovy
   tools {
       maven 'mvn'
       jdk 'jdk-11'  // Descomenta esta línea
   }
   
   environment {
       JAVA_HOME = tool('jdk-11')  // Descomenta esta línea
   }
   ```

#### Opción C: Configurar JAVA_HOME Globalmente
En el agente de Jenkins, configura la variable de entorno `JAVA_HOME`:
```bash
export JAVA_HOME=/path/to/your/jdk-11
export PATH=$JAVA_HOME/bin:$PATH
```

## Verificación de Configuración

El pipeline ahora incluye verificaciones automáticas:
- Ejecuta `java -version` para mostrar la versión de Java
- Ejecuta `mvn -version` para mostrar la versión de Maven
- Detecta y configura `JAVA_HOME` automáticamente si es necesario

## Fragmento Corregido

```groovy
pipeline {
    agent any
    
    // Configuración de herramientas - Comentada la configuración específica de JDK
    // para usar el JDK del sistema o configurar manualmente en Jenkins
    tools {
        maven 'mvn'
        // jdk 'jdk-11'  // Comentado: requiere configuración específica en Jenkins
    }
    
    environment {
        SONARQUBE_ENV = 'sonarqube'
        // JAVA_HOME se establecerá automáticamente o se puede configurar manualmente
        // JAVA_HOME = tool('jdk-11')  // Comentado: requiere JDK configurado por nombre
        MAVEN_OPTS = '-Xmx2048m -XX:MaxMetaspaceSize=512m'
        COVERAGE_THRESHOLD = '80'
        TEST_TIMEOUT = '30'
    }
    
    stages {
        stage('Preparación') {
            steps {
                script {
                    // Verificar configuración de Java y Maven
                    sh 'java -version'
                    sh 'mvn -version'
                    
                    // Establecer JAVA_HOME si no está definido
                    if (env.JAVA_HOME == null || env.JAVA_HOME.isEmpty()) {
                        def javaHome = sh(script: 'echo $JAVA_HOME', returnStdout: true).trim()
                        if (javaHome.isEmpty()) {
                            // Intentar encontrar Java en ubicaciones comunes
                            def javaPath = sh(script: 'which java', returnStdout: true).trim()
                            if (!javaPath.isEmpty()) {
                                def javaDir = sh(script: 'dirname $(dirname $(readlink -f $(which java)))', returnStdout: true).trim()
                                env.JAVA_HOME = javaDir
                                echo "JAVA_HOME configurado automáticamente: ${env.JAVA_HOME}"
                            } else {
                                echo "⚠️ ADVERTENCIA: JAVA_HOME no está configurado. El build puede fallar."
                            }
                        } else {
                            env.JAVA_HOME = javaHome
                        }
                    }
                    
                    echo "JAVA_HOME: ${env.JAVA_HOME}"
                }
            }
        }
        // ... resto del pipeline
    }
}
```

## Recomendaciones

1. **Para desarrollo local:** Usa la configuración automática actual
2. **Para entornos de producción:** Considera configurar un JDK específico en Jenkins para mayor control
3. **Para CI/CD robusto:** Usa la Opción B con un JDK específico configurado en Jenkins

## Notas Importantes

- El pipeline ahora es más robusto y no depende de configuraciones específicas de Jenkins
- Incluye verificaciones automáticas de la configuración de Java
- Proporciona mensajes informativos sobre la configuración detectada
- Mantiene compatibilidad con diferentes entornos de Jenkins 