# Estructura del Repositorio y Configuración de Checkout

## Problema Identificado

El pipeline falla porque no encuentra los submódulos Maven (base, cliente, proveedor, mascota, rrhh) después del checkout del repositorio.

## Estructura Esperada del Repositorio

### **Estructura Correcta en GitHub:**

```
sgvet/
├── .git/
├── .gitignore
├── README.md
├── LICENSE
├── Jenkinsfile
├── base/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   │       └── java/
│   └── target/
├── cliente/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   │       └── java/
│   └── target/
├── proveedor/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   │       └── java/
│   └── target/
├── mascota/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   │       └── java/
│   └── target/
├── rrhh/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   │       └── java/
│   └── target/
└── baseUI/
    ├── pom.xml
    └── src/
```

### **Verificación de la Estructura:**

```bash
# Verificar que todos los módulos existen
ls -la base/ cliente/ proveedor/ mascota/ rrhh/

# Verificar que todos los pom.xml existen
ls -la base/pom.xml cliente/pom.xml proveedor/pom.xml mascota/pom.xml rrhh/pom.xml

# Verificar estructura de directorios
find . -name "pom.xml" -type f
find . -type d -name "src" | head -10
```

## Configuración del Checkout en Jenkins

### **1. Configuración del Job en Jenkins**

En la configuración del job de Jenkins:

1. **Source Code Management:**
   - Seleccionar **Git**
   - **Repository URL:** `https://github.com/tu-usuario/sgvet.git`
   - **Credentials:** Agregar credenciales si es necesario
   - **Branch Specifier:** `*/main` (o `*/master` según tu rama principal)

2. **Advanced Clone Behaviors:**
   - ✅ **Checkout to a sub-directory:** Dejar vacío (checkout en raíz)
   - ✅ **Shallow clone:** Desmarcar (necesitamos todo el historial)
   - ✅ **Fetch tags:** Marcar
   - ✅ **Recursively update submodules:** Marcar

3. **Additional Behaviors:**
   - ✅ **Clean before checkout**
   - ✅ **Clean after checkout**

### **2. Jenkinsfile con Checkout Explícito**

El Jenkinsfile ahora incluye un checkout explícito:

```groovy
stage('Checkout del Repositorio') {
    steps {
        script {
            echo "=== CHECKOUT DEL REPOSITORIO ==="
            
            // Checkout explícito del repositorio con todos los submódulos
            checkout([
                $class: 'GitSCM',
                branches: [[name: '*/main']], // o '*/master' según tu rama principal
                doGenerateSubmoduleConfigurations: false,
                extensions: [
                    [$class: 'SubmoduleOption', 
                     disableSubmodules: false, 
                     recursiveSubmodules: true, 
                     trackingSubmodules: false],
                    [$class: 'CleanBeforeCheckout'],
                    [$class: 'CleanCheckout'],
                    [$class: 'CloneOption', 
                     depth: 0, 
                     noTags: false, 
                     reference: '', 
                     shallow: false]
                ],
                submoduleCfg: [],
                userRemoteConfigs: [[
                    credentialsId: '', // Agregar credenciales si es necesario
                    url: 'https://github.com/tu-usuario/sgvet.git' // Reemplazar con tu URL real
                ]]
            ])
            
            // Verificar el checkout
            echo "=== VERIFICACIÓN DEL CHECKOUT ==="
            sh 'pwd'
            sh 'ls -la'
            sh 'git status'
            sh 'git log --oneline -5'
            
            // Verificar submódulos si existen
            sh 'git submodule status || echo "No hay submódulos configurados"'
        }
    }
}
```

## Pasos para Corregir el Problema

### **1. Verificar el Repositorio en GitHub**

Asegúrate de que todos los módulos estén en el repositorio:

```bash
# Clonar el repositorio localmente para verificar
git clone https://github.com/tu-usuario/sgvet.git
cd sgvet
ls -la
```

### **2. Verificar que los Módulos Estén Commiteados**

```bash
# Verificar que los módulos están en el repositorio
git ls-files | grep -E "(base|cliente|proveedor|mascota|rrhh)/pom.xml"

# Verificar el estado del repositorio
git status
git log --oneline -10
```

### **3. Si los Módulos No Están en el Repositorio**

Si los módulos no están en el repositorio, necesitas agregarlos:

```bash
# Agregar todos los módulos al repositorio
git add base/ cliente/ proveedor/ mascota/ rrhh/
git commit -m "Agregar módulos Maven al repositorio"
git push origin main
```

### **4. Configurar Jenkins Correctamente**

1. **Actualizar la URL del repositorio** en el Jenkinsfile
2. **Configurar credenciales** si es necesario
3. **Verificar la rama principal** (main vs master)

## Validación Mejorada en el Pipeline

### **Nueva Etapa de Validación:**

```groovy
stage('Validación de Estructura del Proyecto') {
    steps {
        script {
            echo "=== VALIDACIÓN DE ESTRUCTURA DEL PROYECTO ==="
            
            // Mostrar información del workspace
            echo "=== INFORMACIÓN DEL WORKSPACE ==="
            sh 'pwd'
            sh 'ls -la'
            
            // Verificar si estamos en el directorio correcto
            if (!fileExists('.git')) {
                error "❌ ERROR: No se detectó un repositorio Git. Verificar el checkout."
            }
            
            def modules = ['base', 'cliente', 'proveedor', 'mascota', 'rrhh']
            def missingModules = []
            def missingPoms = []
            def foundModules = []
            
            echo "=== VERIFICACIÓN DE MÓDULOS ==="
            modules.each { module ->
                if (!fileExists(module)) {
                    missingModules.add(module)
                    echo "❌ Módulo ${module} no encontrado"
                } else {
                    foundModules.add(module)
                    if (!fileExists("${module}/pom.xml")) {
                        missingPoms.add(module)
                        echo "❌ pom.xml no encontrado en módulo ${module}"
                    } else {
                        echo "✅ Módulo ${module} y su pom.xml encontrados"
                        
                        // Mostrar información del pom.xml
                        def pomContent = readFile("${module}/pom.xml")
                        def artifactId = pomContent =~ /<artifactId>([^<]+)<\/artifactId>/
                        if (artifactId) {
                            echo "   📦 ArtifactId: ${artifactId[0][1]}"
                        }
                    }
                }
            }
            
            // Mostrar estructura completa del proyecto
            echo "=== ESTRUCTURA COMPLETA DEL PROYECTO ==="
            sh 'find . -type d -name "src" | head -10'
            sh 'find . -name "pom.xml" -type f'
            
            // Mostrar contenido de directorios encontrados
            if (!foundModules.isEmpty()) {
                echo "=== CONTENIDO DE MÓDULOS ENCONTRADOS ==="
                foundModules.each { module ->
                    echo "📁 Módulo ${module}:"
                    sh "ls -la ${module}/"
                }
            }
            
            // Verificar si hay un pom.xml padre en la raíz
            if (fileExists('pom.xml')) {
                echo "✅ pom.xml padre encontrado en la raíz"
                def rootPom = readFile('pom.xml')
                if (rootPom.contains('<modules>')) {
                    echo "📦 Proyecto multi-módulo detectado"
                } else {
                    echo "📦 Proyecto simple detectado"
                }
            } else {
                echo "ℹ️ No hay pom.xml en la raíz (proyecto modular independiente)"
            }
            
            // Mostrar resumen y errores
            echo "=== RESUMEN DE VALIDACIÓN ==="
            echo "Módulos encontrados: ${foundModules.size()}/${modules.size()}"
            echo "Módulos faltantes: ${missingModules.size()}"
            echo "pom.xml faltantes: ${missingPoms.size()}"
            
            if (!missingModules.isEmpty()) {
                error "❌ ERROR: Módulos faltantes: ${missingModules.join(', ')}"
            }
            
            if (!missingPoms.isEmpty()) {
                error "❌ ERROR: pom.xml faltante en módulos: ${missingPoms.join(', ')}"
            }
            
            echo "✅ Todos los módulos y pom.xml encontrados correctamente"
        }
    }
}
```

## Comandos de Diagnóstico

### **Para Verificar el Repositorio:**

```bash
# Verificar estructura local
find . -name "pom.xml" -type f
ls -la base/ cliente/ proveedor/ mascota/ rrhh/

# Verificar estado de Git
git status
git log --oneline -5
git remote -v

# Verificar submódulos
git submodule status
```

### **Para Verificar Jenkins:**

```bash
# En el workspace de Jenkins
pwd
ls -la
find . -name "pom.xml" -type f
git status
```

## Configuración Recomendada

### **1. Repositorio:**
- ✅ Todos los módulos commiteados
- ✅ Estructura de directorios correcta
- ✅ pom.xml en cada módulo

### **2. Jenkins:**
- ✅ Checkout explícito configurado
- ✅ Submódulos habilitados
- ✅ Clean checkout habilitado

### **3. Pipeline:**
- ✅ Validación de estructura
- ✅ Mensajes de error claros
- ✅ Diagnóstico detallado

## Estado de la Corrección

- ✅ **Checkout Explícito**: Implementado
- ✅ **Validación Mejorada**: Agregada
- ✅ **Diagnóstico Detallado**: Implementado
- ✅ **Mensajes de Error**: Mejorados

El pipeline ahora debería encontrar correctamente todos los módulos y proceder con la compilación, pruebas y análisis de calidad. 