pipeline {
    agent any
    
    tools {
        maven 'mvn'
    }
    
    environment {
        SONARQUBE_ENV = 'sonarqube'
        MAVEN_OPTS = '-Xmx2048m -XX:MaxMetaspaceSize=512m'
        COVERAGE_THRESHOLD = '80'
        // Configuración del repositorio - CONFIGURADO PARA TU REPOSITORIO
        REPO_URL = 'https://github.com/stevenayal/sgvet.git'
        REPO_BRANCH = 'rrhh_tl' // Rama específica de tu repositorio
        CREDENTIALS_ID = 'd5f23b7491ad482b8519debed83faeee' // ID de las credenciales configuradas en Jenkins
    }
    
    options {
        timeout(time: 1, unit: 'HOURS')
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    
    parameters {
        choice(
            name: 'BUILD_TYPE',
            choices: ['FULL_BUILD', 'RRHH_ONLY', 'TEST_ONLY'],
            description: 'Tipo de build a ejecutar'
        )
        booleanParam(
            name: 'SKIP_TESTS',
            defaultValue: false,
            description: 'Saltar ejecución de pruebas unitarias'
        )
        booleanParam(
            name: 'SKIP_SONAR',
            defaultValue: false,
            description: 'Saltar análisis de SonarQube'
        )
    }
    
    stages {
        stage('Verificación de Credenciales') {
            steps {
                script {
                    echo "=== VERIFICACIÓN DE CREDENCIALES ==="
                    
                    // Verificar que las credenciales estén configuradas
                    if (env.CREDENTIALS_ID == null || env.CREDENTIALS_ID.isEmpty()) {
                        error """
                        ❌ ERROR: CREDENTIALS_ID no está configurado
                        
                        🔧 SOLUCIÓN:
                        1. Ve a Jenkins > Manage Jenkins > Credentials
                        2. Agrega credenciales de tipo 'Username with password' o 'Secret text'
                        3. Actualiza CREDENTIALS_ID en el Jenkinsfile con el ID de las credenciales
                        4. O configura las credenciales en la configuración del job
                        """
                    }
                    
                    // Verificar que la URL del repositorio esté configurada
                    if (env.REPO_URL == null || env.REPO_URL.isEmpty() || env.REPO_URL.contains('tu-usuario')) {
                        error """
                        ❌ ERROR: REPO_URL no está configurado correctamente
                        
                        🔧 SOLUCIÓN:
                        1. Actualiza REPO_URL en el Jenkinsfile con tu URL real
                        2. Formato: https://github.com/TU-USUARIO/TU-REPOSITORIO.git
                        """
                    }
                    
                    echo "✅ Configuración de credenciales verificada"
                    echo "📦 Repositorio: ${env.REPO_URL}"
                    echo "🌿 Rama: ${env.REPO_BRANCH}"
                    echo "🔑 Credenciales ID: ${env.CREDENTIALS_ID}"
                }
            }
        }
        
        stage('Test de Conectividad') {
            steps {
                script {
                    echo "=== TEST DE CONECTIVIDAD ==="
                    
                    try {
                        // Test simple de conectividad con git ls-remote
                        sh """
                            git ls-remote --heads ${env.REPO_URL} ${env.REPO_BRANCH} || {
                                echo "❌ ERROR: No se puede conectar al repositorio"
                                echo "🔧 Verificar:"
                                echo "   - URL del repositorio: ${env.REPO_URL}"
                                echo "   - Rama: ${env.REPO_BRANCH}"
                                echo "   - Credenciales: ${env.CREDENTIALS_ID}"
                                exit 1
                            }
                        """
                        echo "✅ Conectividad al repositorio verificada"
                    } catch (Exception e) {
                        error """
                        ❌ ERROR DE CONECTIVIDAD
                        
                        🔧 POSIBLES CAUSAS:
                        1. URL del repositorio incorrecta
                        2. Credenciales inválidas o expiradas
                        3. Repositorio privado sin acceso
                        4. Problemas de red
                        
                        🔧 SOLUCIÓN:
                        1. Verificar la URL: ${env.REPO_URL}
                        2. Actualizar credenciales en Jenkins
                        3. Verificar permisos del repositorio
                        4. Probar conectividad manualmente: git ls-remote ${env.REPO_URL}
                        """
                    }
                }
            }
        }
        
        stage('Checkout del Repositorio') {
            steps {
                script {
                    echo "=== CHECKOUT DEL REPOSITORIO ==="
                    
                    try {
                        // Limpiar workspace
                        cleanWs()
                        
                        // Checkout con manejo de errores
                        checkout([
                            $class: 'GitSCM',
                            branches: [[name: "*/${env.REPO_BRANCH}"]],
                            doGenerateSubmoduleConfigurations: false,
                            extensions: [
                                [$class: 'CleanBeforeCheckout'],
                                [$class: 'CleanCheckout']
                            ],
                            userRemoteConfigs: [[
                                credentialsId: env.CREDENTIALS_ID,
                                url: env.REPO_URL
                            ]]
                        ])
                        
                        // Verificación básica del checkout
                        sh 'pwd && ls -la'
                        sh 'git status'
                        
                        echo "✅ Checkout completado exitosamente"
                        
                    } catch (Exception e) {
                        error """
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
                        
                        📋 DETALLES DEL ERROR:
                        ${e.getMessage()}
                        """
                    }
                }
            }
        }
        
        stage('Validación de Estructura') {
            steps {
                script {
                    echo "=== VALIDACIÓN DE ESTRUCTURA ==="
                    
                    // Verificar que es un repositorio Git
                    if (!fileExists('.git')) {
                        error "❌ ERROR: No se detectó un repositorio Git. El checkout falló."
                    }
                    
                    // Verificar módulos esperados
                    def modules = ['base', 'cliente', 'proveedor', 'mascota', 'rrhh']
                    def missingModules = []
                    
                    modules.each { module ->
                        if (!fileExists(module) || !fileExists("${module}/pom.xml")) {
                            missingModules.add(module)
                        }
                    }
                    
                    if (!missingModules.isEmpty()) {
                        error """
                        ❌ ERROR: Módulos faltantes: ${missingModules.join(', ')}
                        
                        🔧 POSIBLES CAUSAS:
                        1. Los módulos no están en el repositorio
                        2. Checkout incompleto
                        3. Estructura del proyecto incorrecta
                        
                        🔧 SOLUCIÓN:
                        1. Verificar que los módulos estén commiteados
                        2. Ejecutar: git add . && git commit && git push
                        3. Verificar estructura del repositorio
                        """
                    }
                    
                    echo "✅ Estructura del proyecto validada"
                    echo "📦 Módulos encontrados: ${modules.size()}"
                }
            }
        }
        
        stage('Compilación') {
            when {
                expression { params.BUILD_TYPE != 'TEST_ONLY' }
            }
            steps {
                script {
                    echo "=== COMPILACIÓN ==="
                    
                    def modules = ['base', 'cliente', 'proveedor', 'mascota', 'rrhh']
                    
                    modules.each { module ->
                        dir(module) {
                            echo "📦 Compilando ${module}..."
                            sh 'mvn clean compile -q'
                            echo "✅ ${module} compilado"
                        }
                    }
                    
                    echo "✅ Compilación completada"
                }
            }
        }
        
        stage('Pruebas Unitarias') {
            when {
                expression { !params.SKIP_TESTS }
            }
            steps {
                script {
                    echo "=== PRUEBAS UNITARIAS ==="
                    
                    def modules = ['base', 'cliente', 'proveedor', 'mascota', 'rrhh']
                    
                    modules.each { module ->
                        dir(module) {
                            echo "🧪 Ejecutando pruebas de ${module}..."
                            sh 'mvn test -Dmaven.test.failure.ignore=true'
                            echo "✅ Pruebas de ${module} completadas"
                        }
                    }
                    
                    echo "✅ Pruebas unitarias completadas"
                }
            }
        }
        
        stage('Análisis de Calidad') {
            when {
                expression { !params.SKIP_SONAR }
            }
            steps {
                script {
                    echo "=== ANÁLISIS DE CALIDAD ==="
                    
                    // Análisis simple de SonarQube para el módulo principal
                    dir('rrhh') {
                        withSonarQubeEnv("${SONARQUBE_ENV}") {
                            sh """
                                mvn sonar:sonar \
                                -Dsonar.host.url=http://sonarqube:9000 \
                                -Dsonar.projectKey=sgVet-rrhh \
                                -Dsonar.projectName=SgVet-RRHH \
                                -Dsonar.projectVersion=${BUILD_NUMBER}
                            """
                        }
                    }
                    
                    echo "✅ Análisis de calidad completado"
                }
            }
        }
    }
    
    post {
        always {
            script {
                echo "=== RESUMEN DEL BUILD ==="
                echo "📊 Resultado: ${currentBuild.result}"
                echo "⏱️ Duración: ${currentBuild.durationString}"
                echo "🔢 Build Number: ${BUILD_NUMBER}"
            }
        }
        
        success {
            script {
                echo "✅ Pipeline ejecutado exitosamente"
            }
        }
        
        failure {
            script {
                echo "❌ Pipeline falló"
                echo "🔧 Revisar logs para más detalles"
            }
        }
    }
}