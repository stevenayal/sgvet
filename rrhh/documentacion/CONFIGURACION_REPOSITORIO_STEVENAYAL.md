# Configuración Específica para Repositorio stevenayal/sgvet

## Información del Repositorio

- **URL:** https://github.com/stevenayal/sgvet
- **Rama:** `rrhh_tl`
- **Tipo:** Fork público de UCOM-Seminario1/sgvet
- **Estructura:** Módulos Maven independientes

## Configuración Actualizada del Jenkinsfile

El Jenkinsfile ya está configurado con tu repositorio específico:

```groovy
environment {
    SONARQUBE_ENV = 'sonarqube'
    MAVEN_OPTS = '-Xmx2048m -XX:MaxMetaspaceSize=512m'
    COVERAGE_THRESHOLD = '80'
    // Configuración del repositorio - CONFIGURADO PARA TU REPOSITORIO
    REPO_URL = 'https://github.com/stevenayal/sgvet.git'
    REPO_BRANCH = 'rrhh_tl' // Rama específica de tu repositorio
    CREDENTIALS_ID = '' // ID de las credenciales configuradas en Jenkins
}
```

## Pasos para Configurar Jenkins

### **1. Configurar Credenciales (Opcional para Repositorio Público)**

Como tu repositorio es público, puedes usar el pipeline sin credenciales, pero es recomendable configurarlas para evitar límites de rate:

#### **Opción A: Sin Credenciales (Repositorio Público)**
```groovy
CREDENTIALS_ID = '' // Dejar vacío para repositorio público
```

#### **Opción B: Con Credenciales (Recomendado)**
1. Ve a `Jenkins` → `Manage Jenkins` → `Credentials`
2. Click en `System` → `Global credentials` → `Add Credentials`
3. Selecciona `Username with password`
4. Configura:
   - **Username:** `stevenayal`
   - **Password:** Tu token personal de GitHub
   - **ID:** `stevenayal-github-token`
   - **Description:** `GitHub token for stevenayal/sgvet`

### **2. Crear Token Personal en GitHub**

1. Ve a `GitHub` → `Settings` → `Developer settings` → `Personal access tokens`
2. Click en `Generate new token` → `Classic`
3. Configura:
   - **Note:** `Jenkins sgVet Pipeline - stevenayal`
   - **Expiration:** Según tus necesidades
   - **Scopes:** Marca `repo` (acceso completo a repositorios)
4. Click en `Generate token`
5. **IMPORTANTE:** Copia el token inmediatamente

### **3. Actualizar CREDENTIALS_ID en Jenkinsfile**

Si configuraste credenciales, actualiza el Jenkinsfile:

```groovy
CREDENTIALS_ID = 'stevenayal-github-token' // ID de tus credenciales
```

## Estructura del Repositorio

Según la información del repositorio, la estructura es:

```
sgvet/
├── base/
├── baseUI/
├── cliente/
├── doc/
├── mascota/
├── pipeline/
├── proveedor/
├── rrhh/
├── sgvetDB/
├── Jenkinsfile
├── README.md
└── LICENSE
```

## Verificación de la Configuración

### **Test de Conectividad Manual:**

```bash
# Probar sin credenciales (repositorio público)
git ls-remote https://github.com/stevenayal/sgvet.git rrhh_tl

# Probar con credenciales (si las configuraste)
git ls-remote https://TU-TOKEN@github.com/stevenayal/sgvet.git rrhh_tl
```

### **Clonar Repositorio Localmente:**

```bash
# Clonar la rama específica
git clone -b rrhh_tl https://github.com/stevenayal/sgvet.git
cd sgvet

# Verificar estructura
ls -la
find . -name "pom.xml" -type f
```

## Configuración del Job en Jenkins

### **1. Crear Nuevo Job**

1. Ve a Jenkins → `New Item`
2. Selecciona `Pipeline`
3. Nombre: `sgVet-stevenayal-pipeline`

### **2. Configuración del Pipeline**

En la configuración del job:

1. **Pipeline:**
   - **Definition:** `Pipeline script from SCM`
   - **SCM:** `Git`
   - **Repository URL:** `https://github.com/stevenayal/sgvet.git`
   - **Credentials:** Seleccionar las credenciales configuradas (si las hay)
   - **Branch Specifier:** `*/rrhh_tl`
   - **Script Path:** `Jenkinsfile`

### **3. Configuración Avanzada**

- **Lightweight checkout:** Desmarcar (necesitamos el Jenkinsfile completo)
- **Poll SCM:** Configurar según necesidades (ej: `H/5 * * * *` para cada 5 minutos)

## Parámetros del Pipeline

El pipeline incluye estos parámetros configurables:

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

## Troubleshooting Específico

### **Problema: "Branch rrhh_tl not found"**
**Solución:**
1. Verificar que la rama `rrhh_tl` existe en el repositorio
2. Verificar que el nombre de la rama esté correcto
3. Probar: `git ls-remote https://github.com/stevenayal/sgvet.git rrhh_tl`

### **Problema: "Repository not found"**
**Solución:**
1. Verificar que el repositorio sea público o tengas acceso
2. Verificar la URL: `https://github.com/stevenayal/sgvet.git`
3. Probar acceso manualmente

### **Problema: "Rate limit exceeded"**
**Solución:**
1. Configurar credenciales de GitHub
2. Usar token personal en lugar de acceso anónimo
3. Verificar que el token tenga permisos adecuados

## Comandos de Verificación

### **Verificar Estructura del Repositorio:**

```bash
# Clonar y verificar
git clone -b rrhh_tl https://github.com/stevenayal/sgvet.git
cd sgvet
./verificar-estructura.sh
```

### **Verificar Módulos Maven:**

```bash
# Verificar que todos los módulos tienen pom.xml
find . -name "pom.xml" -type f
ls -la base/ cliente/ proveedor/ mascota/ rrhh/
```

## Estado de la Configuración

- ✅ **URL del Repositorio:** Configurada correctamente
- ✅ **Rama:** Configurada como `rrhh_tl`
- ✅ **Jenkinsfile:** Actualizado con tu repositorio
- ⚠️ **Credenciales:** Pendiente de configuración (opcional)
- ✅ **Estructura:** Verificada en el repositorio

El pipeline está listo para usar con tu repositorio específico. Solo necesitas configurar las credenciales si quieres evitar límites de rate de GitHub. 