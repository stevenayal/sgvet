# Pipeline Simplificado - Configuración y Uso

## Problema Resuelto

El pipeline original era complejo y fallaba por problemas de autenticación con GitHub. Se ha simplificado para ser más robusto y fácil de mantener.

## Nuevo Pipeline Simplificado

### **Características Principales:**

1. **✅ Validación de Credenciales** - Verifica configuración antes de intentar checkout
2. **✅ Test de Conectividad** - Prueba conexión al repositorio antes del checkout
3. **✅ Manejo de Errores** - Mensajes claros y específicos para cada tipo de error
4. **✅ Estructura Simple** - Menos etapas paralelas, más fácil de debuggear
5. **✅ Falla Rápido** - No desperdicia recursos en etapas que van a fallar

## Configuración Requerida

### **1. Configurar Credenciales en Jenkins**

#### **Opción A: Username/Password**
1. Ve a `Jenkins` → `Manage Jenkins` → `Credentials`
2. Click en `System` → `Global credentials` → `Add Credentials`
3. Selecciona `Username with password`
4. Configura:
   - **Username:** Tu usuario de GitHub
   - **Password:** Tu token personal de GitHub (NO tu contraseña)
   - **ID:** `github-credentials` (o el ID que prefieras)
   - **Description:** `GitHub credentials for sgVet`

#### **Opción B: Secret Text (Token)**
1. Ve a `Jenkins` → `Manage Jenkins` → `Credentials`
2. Click en `System` → `Global credentials` → `Add Credentials`
3. Selecciona `Secret text`
4. Configura:
   - **Secret:** Tu token personal de GitHub
   - **ID:** `github-token` (o el ID que prefieras)
   - **Description:** `GitHub token for sgVet`

### **2. Crear Token Personal en GitHub**

1. Ve a `GitHub` → `Settings` → `Developer settings` → `Personal access tokens`
2. Click en `Generate new token` → `Classic`
3. Configura:
   - **Note:** `Jenkins sgVet Pipeline`
   - **Expiration:** Según tus necesidades
   - **Scopes:** Marca `repo` (acceso completo a repositorios privados)
4. Click en `Generate token`
5. **IMPORTANTE:** Copia el token inmediatamente (no lo verás de nuevo)

### **3. Actualizar Jenkinsfile**

Edita las variables de entorno en el Jenkinsfile:

```groovy
environment {
    SONARQUBE_ENV = 'sonarqube'
    MAVEN_OPTS = '-Xmx2048m -XX:MaxMetaspaceSize=512m'
    COVERAGE_THRESHOLD = '80'
    // ⚠️ ACTUALIZAR ESTOS VALORES:
    REPO_URL = 'https://github.com/TU-USUARIO-REAL/sgvet.git'
    REPO_BRANCH = 'main' // o 'master' según tu rama principal
    CREDENTIALS_ID = 'github-credentials' // ID de las credenciales configuradas
}
```

## Estructura del Nuevo Pipeline

### **Etapas del Pipeline:**

```
1. Verificación de Credenciales
   ├── Valida que CREDENTIALS_ID esté configurado
   ├── Valida que REPO_URL esté configurado
   └── Muestra configuración actual

2. Test de Conectividad
   ├── Prueba conexión con git ls-remote
   ├── Verifica que la rama existe
   └── Falla rápido si hay problemas de red/credenciales

3. Checkout del Repositorio
   ├── Limpia workspace
   ├── Hace checkout con credenciales
   ├── Verifica que el checkout fue exitoso
   └── Maneja errores específicos

4. Validación de Estructura
   ├── Verifica que es un repositorio Git
   ├── Valida que todos los módulos existen
   ├── Verifica que todos los pom.xml existen
   └── Falla si falta estructura

5. Compilación (Opcional)
   ├── Compila todos los módulos secuencialmente
   └── Solo si BUILD_TYPE != 'TEST_ONLY'

6. Pruebas Unitarias (Opcional)
   ├── Ejecuta pruebas de todos los módulos
   └── Solo si SKIP_TESTS = false

7. Análisis de Calidad (Opcional)
   ├── Ejecuta SonarQube en módulo RRHH
   └── Solo si SKIP_SONAR = false
```

## Mensajes de Error Mejorados

### **Error de Credenciales:**
```
❌ ERROR: CREDENTIALS_ID no está configurado

🔧 SOLUCIÓN:
1. Ve a Jenkins > Manage Jenkins > Credentials
2. Agrega credenciales de tipo 'Username with password' o 'Secret text'
3. Actualiza CREDENTIALS_ID en el Jenkinsfile con el ID de las credenciales
4. O configura las credenciales en la configuración del job
```

### **Error de Conectividad:**
```
❌ ERROR DE CONECTIVIDAD

🔧 POSIBLES CAUSAS:
1. URL del repositorio incorrecta
2. Credenciales inválidas o expiradas
3. Repositorio privado sin acceso
4. Problemas de red

🔧 SOLUCIÓN:
1. Verificar la URL: https://github.com/TU-USUARIO/sgvet.git
2. Actualizar credenciales en Jenkins
3. Verificar permisos del repositorio
4. Probar conectividad manualmente: git ls-remote https://github.com/TU-USUARIO/sgvet.git
```

### **Error de Checkout:**
```
❌ ERROR EN CHECKOUT

🔧 POSIBLES CAUSAS:
1. Credenciales incorrectas o expiradas
2. URL del repositorio mal formada
3. Rama no existe
4. Problemas de permisos

🔧 SOLUCIÓN:
1. Verificar credenciales en Jenkins
2. Actualizar URL del repositorio
3. Verificar que la rama existe
4. Comprobar permisos del repositorio
```

## Parámetros del Pipeline

### **BUILD_TYPE:**
- `FULL_BUILD` - Compila, testea y analiza calidad
- `RRHH_ONLY` - Solo módulo RRHH
- `TEST_ONLY` - Solo pruebas, sin compilación

### **SKIP_TESTS:**
- `false` - Ejecuta pruebas unitarias
- `true` - Salta pruebas unitarias

### **SKIP_SONAR:**
- `false` - Ejecuta análisis de SonarQube
- `true` - Salta análisis de SonarQube

## Comandos de Diagnóstico

### **Probar Conectividad Manualmente:**
```bash
# Probar sin credenciales (repositorio público)
git ls-remote https://github.com/TU-USUARIO/sgvet.git main

# Probar con credenciales (repositorio privado)
git ls-remote https://TU-TOKEN@github.com/TU-USUARIO/sgvet.git main
```

### **Verificar Credenciales en Jenkins:**
```bash
# En el workspace de Jenkins
echo $CREDENTIALS_ID
echo $REPO_URL
echo $REPO_BRANCH
```

## Ventajas del Pipeline Simplificado

### **1. Robustez:**
- ✅ Valida configuración antes de ejecutar
- ✅ Prueba conectividad antes del checkout
- ✅ Maneja errores específicos
- ✅ Falla rápido en caso de problemas

### **2. Simplicidad:**
- ✅ Menos etapas paralelas
- ✅ Flujo secuencial claro
- ✅ Fácil de debuggear
- ✅ Mensajes de error claros

### **3. Mantenibilidad:**
- ✅ Configuración centralizada
- ✅ Variables de entorno claras
- ✅ Documentación integrada
- ✅ Fácil de modificar

## Troubleshooting

### **Problema: "Invalid username or token"**
**Solución:**
1. Verificar que el token no haya expirado
2. Crear nuevo token en GitHub
3. Actualizar credenciales en Jenkins
4. Verificar que el token tenga permisos `repo`

### **Problema: "Repository not found"**
**Solución:**
1. Verificar URL del repositorio
2. Verificar que el repositorio existe
3. Verificar permisos del usuario/token
4. Probar acceso manualmente

### **Problema: "Branch not found"**
**Solución:**
1. Verificar nombre de la rama (main vs master)
2. Verificar que la rama existe en el repositorio
3. Actualizar REPO_BRANCH en el Jenkinsfile

## Estado de la Implementación

- ✅ **Pipeline Simplificado**: Implementado
- ✅ **Validación de Credenciales**: Agregada
- ✅ **Test de Conectividad**: Implementado
- ✅ **Manejo de Errores**: Mejorado
- ✅ **Documentación**: Completa

El pipeline ahora es robusto, simple y fácil de mantener, con validaciones claras y mensajes de error específicos para facilitar el debugging. 