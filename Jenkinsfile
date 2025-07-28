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
    
    options {
        timeout(time: 2, unit: 'HOURS')
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
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
        string(
            name: 'CUSTOM_COVERAGE_THRESHOLD',
            defaultValue: '80',
            description: 'Umbral mínimo de cobertura de código (%)'
        )
    }
    
    stages {
        stage('Preparación') {
            steps {
                script {
                    echo "=== INICIO DE PIPELINE ==="
                    echo "Build Type: ${params.BUILD_TYPE}"
                    echo "Skip Tests: ${params.SKIP_TESTS}"
                    echo "Skip Sonar: ${params.SKIP_SONAR}"
                    echo "Coverage Threshold: ${params.CUSTOM_COVERAGE_THRESHOLD}%"
                    
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
                    echo "Maven Version: ${tool('mvn')}"
                    
                    // Establecer umbral de cobertura dinámico
                    env.COVERAGE_THRESHOLD = params.CUSTOM_COVERAGE_THRESHOLD
                }
            }
        }
        
        stage('Checkout y Limpieza') {
            steps {
                script {
                    echo "=== LIMPIEZA DE WORKSPACE ==="
                    cleanWs()
                    
                    // Verificar que estamos en el directorio correcto
                    sh 'pwd && ls -la'
                }
            }
        }
        
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
        
        stage('Análisis de Dependencias') {
            when {
                expression { params.BUILD_TYPE != 'TEST_ONLY' }
            }
            steps {
                script {
                    echo "=== ANÁLISIS DE DEPENDENCIAS ==="
                    
                    def modules = ['base', 'cliente', 'proveedor', 'mascota', 'rrhh']
                    
                    modules.each { module ->
                        if (fileExists(module)) {
                            if (fileExists("${module}/pom.xml")) {
                                dir(module) {
                                    echo "Analizando dependencias de ${module}..."
                                    sh 'mvn dependency:tree -DoutputFile=dependency-tree.txt'
                                    archiveArtifacts artifacts: "${module}/dependency-tree.txt", fingerprint: true
                                }
                            } else {
                                error "❌ ERROR: No se encontró pom.xml en el módulo ${module}"
                            }
                        } else {
                            echo "⚠️ ADVERTENCIA: El módulo ${module} no existe en el workspace"
                        }
                    }
                }
            }
        }
        
        stage('Compilación') {
            when {
                expression { params.BUILD_TYPE != 'TEST_ONLY' }
            }
            parallel {
                stage('Compilar Base') {
                    steps {
                        script {
                            if (fileExists('base') && fileExists('base/pom.xml')) {
                                dir('base') {
                                    echo "=== COMPILANDO MÓDULO BASE ==="
                                    sh 'mvn clean compile -q'
                                    echo "✅ Compilación de Base completada"
                                }
                            } else {
                                error "❌ ERROR: No se encontró el módulo base o su pom.xml"
                            }
                        }
                    }
                }
                
                stage('Compilar Cliente') {
                    steps {
                        script {
                            if (fileExists('cliente') && fileExists('cliente/pom.xml')) {
                                dir('cliente') {
                                    echo "=== COMPILANDO MÓDULO CLIENTE ==="
                                    sh 'mvn clean compile -q'
                                    echo "✅ Compilación de Cliente completada"
                                }
                            } else {
                                error "❌ ERROR: No se encontró el módulo cliente o su pom.xml"
                            }
                        }
                    }
                }
                
                stage('Compilar Proveedor') {
                    steps {
                        script {
                            if (fileExists('proveedor') && fileExists('proveedor/pom.xml')) {
                                dir('proveedor') {
                                    echo "=== COMPILANDO MÓDULO PROVEEDOR ==="
                                    sh 'mvn clean compile -q'
                                    echo "✅ Compilación de Proveedor completada"
                                }
                            } else {
                                error "❌ ERROR: No se encontró el módulo proveedor o su pom.xml"
                            }
                        }
                    }
                }
                
                stage('Compilar Mascota') {
                    steps {
                        script {
                            if (fileExists('mascota') && fileExists('mascota/pom.xml')) {
                                dir('mascota') {
                                    echo "=== COMPILANDO MÓDULO MASCOTA ==="
                                    sh 'mvn clean compile -q'
                                    echo "✅ Compilación de Mascota completada"
                                }
                            } else {
                                error "❌ ERROR: No se encontró el módulo mascota o su pom.xml"
                            }
                        }
                    }
                }
                
                stage('Compilar RRHH') {
                    steps {
                        script {
                            if (fileExists('rrhh') && fileExists('rrhh/pom.xml')) {
                                dir('rrhh') {
                                    echo "=== COMPILANDO MÓDULO RRHH ==="
                                    sh 'mvn clean compile -q'
                                    echo "✅ Compilación de RRHH completada"
                                }
                            } else {
                                error "❌ ERROR: No se encontró el módulo rrhh o su pom.xml"
                            }
                        }
                    }
                }
            }
        }
        
        stage('Pruebas Unitarias') {
            when {
                expression { !params.SKIP_TESTS }
            }
            parallel {
                stage('Pruebas Base') {
                    steps {
                        script {
                            if (fileExists('base') && fileExists('base/pom.xml')) {
                                dir('base') {
                                    echo "=== EJECUTANDO PRUEBAS UNITARIAS - BASE ==="
                                    sh 'mvn test -Dmaven.test.failure.ignore=true'
                                    
                                    // Publicar resultados de pruebas
                                    publishTestResults testResultsPattern: '**/surefire-reports/*.xml'
                                    
                                    // Generar reporte de cobertura si está disponible
                                    if (fileExists('target/site/jacoco')) {
                                        publishCoverage adapters: [jacocoAdapter('target/site/jacoco/jacoco.xml')], 
                                                       sourceFileResolver: sourceFiles('STORE_LAST_BUILD')
                                    }
                                }
                            } else {
                                error "❌ ERROR: No se encontró el módulo base o su pom.xml"
                            }
                        }
                    }
                }
                
                stage('Pruebas Cliente') {
                    steps {
                        script {
                            if (fileExists('cliente') && fileExists('cliente/pom.xml')) {
                                dir('cliente') {
                                    echo "=== EJECUTANDO PRUEBAS UNITARIAS - CLIENTE ==="
                                    sh 'mvn test -Dmaven.test.failure.ignore=true'
                                    publishTestResults testResultsPattern: '**/surefire-reports/*.xml'
                                }
                            } else {
                                error "❌ ERROR: No se encontró el módulo cliente o su pom.xml"
                            }
                        }
                    }
                }
                
                stage('Pruebas Proveedor') {
                    steps {
                        script {
                            if (fileExists('proveedor') && fileExists('proveedor/pom.xml')) {
                                dir('proveedor') {
                                    echo "=== EJECUTANDO PRUEBAS UNITARIAS - PROVEEDOR ==="
                                    sh 'mvn test -Dmaven.test.failure.ignore=true'
                                    publishTestResults testResultsPattern: '**/surefire-reports/*.xml'
                                }
                            } else {
                                error "❌ ERROR: No se encontró el módulo proveedor o su pom.xml"
                            }
                        }
                    }
                }
                
                stage('Pruebas Mascota') {
                    steps {
                        script {
                            if (fileExists('mascota') && fileExists('mascota/pom.xml')) {
                                dir('mascota') {
                                    echo "=== EJECUTANDO PRUEBAS UNITARIAS - MASCOTA ==="
                                    sh 'mvn test -Dmaven.test.failure.ignore=true'
                                    publishTestResults testResultsPattern: '**/surefire-reports/*.xml'
                                }
                            } else {
                                error "❌ ERROR: No se encontró el módulo mascota o su pom.xml"
                            }
                        }
                    }
                }
                
                stage('Pruebas RRHH') {
                    steps {
                        script {
                            if (fileExists('rrhh') && fileExists('rrhh/pom.xml')) {
                                dir('rrhh') {
                                    echo "=== EJECUTANDO PRUEBAS UNITARIAS - RRHH ==="
                                    
                                    // Ejecutar pruebas con timeout y configuración específica
                                    timeout(time: 10, unit: 'MINUTES') {
                                        sh '''
                                            mvn test \
                                            -Dmaven.test.failure.ignore=true \
                                            -Dtest=RRHHControllerTest,RRHHRepositoryTest,RRHHValidadorTest,EvaluacionDesempenoControllerTest,EvaluacionDesempenoTest \
                                            -Dsurefire.useFile=false \
                                            -Dmaven.test.redirectTestOutputToFile=false
                                        '''
                                    }
                                    
                                    // Publicar resultados de pruebas
                                    publishTestResults testResultsPattern: '**/surefire-reports/*.xml'
                                    
                                    // Generar reporte de cobertura
                                    sh 'mvn jacoco:report'
                                    
                                    // Publicar cobertura de código
                                    publishCoverage adapters: [jacocoAdapter('target/site/jacoco/jacoco.xml')], 
                                                   sourceFileResolver: sourceFiles('STORE_LAST_BUILD')
                                    
                                    // Verificar umbral de cobertura
                                    script {
                                        def coverageFile = readFile('target/site/jacoco/jacoco.xml')
                                        def coverage = new XmlSlurper().parseText(coverageFile)
                                        def lineCoverage = coverage.counter.find { it.@type == 'LINE' }.@covered.toInteger()
                                        def totalLines = coverage.counter.find { it.@type == 'LINE' }.@covered.toInteger() + 
                                                       coverage.counter.find { it.@type == 'LINE' }.@missed.toInteger()
                                        def coveragePercentage = (lineCoverage / totalLines * 100).round(2)
                                        
                                        echo "📊 Cobertura de código RRHH: ${coveragePercentage}%"
                                        
                                        if (coveragePercentage < env.COVERAGE_THRESHOLD.toInteger()) {
                                            error "❌ Cobertura de código (${coveragePercentage}%) está por debajo del umbral mínimo (${env.COVERAGE_THRESHOLD}%)"
                                        } else {
                                            echo "✅ Cobertura de código cumple con el umbral mínimo"
                                        }
                                    }
                                }
                            } else {
                                error "❌ ERROR: No se encontró el módulo rrhh o su pom.xml"
                            }
                        }
                    }
                }
            }
            post {
                always {
                    script {
                        echo "=== RESUMEN DE PRUEBAS UNITARIAS ==="
                        // Generar reporte consolidado de pruebas
                        sh '''
                            echo "=== REPORTE DE PRUEBAS UNITARIAS ===" > test-summary.txt
                            echo "Fecha: $(date)" >> test-summary.txt
                            echo "Build: ${BUILD_NUMBER}" >> test-summary.txt
                            echo "" >> test-summary.txt
                            
                            for module in base cliente proveedor mascota rrhh; do
                                if [ -d "$module/target/surefire-reports" ]; then
                                    echo "=== MÓDULO $module ===" >> test-summary.txt
                                    find "$module/target/surefire-reports" -name "*.txt" -exec cat {} \\; >> test-summary.txt
                                    echo "" >> test-summary.txt
                                fi
                            done
                        '''
                        archiveArtifacts artifacts: 'test-summary.txt', fingerprint: true
                    }
                }
            }
        }
        
        stage('Análisis de Calidad de Código') {
            when {
                expression { !params.SKIP_SONAR }
            }
            parallel {
                stage('SonarQube - Base') {
                    steps {
                        dir('base') {
                            script {
                                echo "=== ANÁLISIS SONARQUBE - BASE ==="
                                withSonarQubeEnv("${SONARQUBE_ENV}") {
                                    sh '''
                                        mvn sonar:sonar \
                                        -Dsonar.host.url=http://sonarqube:9000 \
                                        -Dsonar.projectKey=sgVet-base \
                                        -Dsonar.projectName=SgVet-Base \
                                        -Dsonar.projectVersion=${BUILD_NUMBER} \
                                        -Dsonar.sources=src/main/java \
                                        -Dsonar.tests=src/test/java \
                                        -Dsonar.java.binaries=target/classes \
                                        -Dsonar.java.test.binaries=target/test-classes \
                                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                                    '''
                                }
                            }
                        }
                    }
                }
                
                stage('SonarQube - RRHH') {
                    steps {
                        dir('rrhh') {
                            script {
                                echo "=== ANÁLISIS SONARQUBE - RRHH ==="
                                withSonarQubeEnv("${SONARQUBE_ENV}") {
                                    sh '''
                                        mvn sonar:sonar \
                                        -Dsonar.host.url=http://sonarqube:9000 \
                                        -Dsonar.projectKey=sgVet-rrhh \
                                        -Dsonar.projectName=SgVet-RRHH \
                                        -Dsonar.projectVersion=${BUILD_NUMBER} \
                                        -Dsonar.sources=src/main/java \
                                        -Dsonar.tests=src/test/java \
                                        -Dsonar.java.binaries=target/classes \
                                        -Dsonar.java.test.binaries=target/test-classes \
                                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                                        -Dsonar.qualitygate.wait=true \
                                        -Dsonar.qualitygate.timeout=300
                                    '''
                                }
                            }
                        }
                    }
                }
                
                stage('SonarQube - Otros Módulos') {
                    steps {
                        script {
                            def modules = ['cliente', 'proveedor', 'mascota']
                            modules.each { module ->
                                dir(module) {
                                    script {
                                        echo "=== ANÁLISIS SONARQUBE - ${module.toUpperCase()} ==="
                                        withSonarQubeEnv("${SONARQUBE_ENV}") {
                                            sh """
                                                mvn sonar:sonar \
                                                -Dsonar.host.url=http://sonarqube:9000 \
                                                -Dsonar.projectKey=sgVet-${module} \
                                                -Dsonar.projectName=SgVet-${module.capitalize()} \
                                                -Dsonar.projectVersion=${BUILD_NUMBER} \
                                                -Dsonar.sources=src/main/java \
                                                -Dsonar.tests=src/test/java \
                                                -Dsonar.java.binaries=target/classes \
                                                -Dsonar.java.test.binaries=target/test-classes
                                            """
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        stage('Quality Gate Check') {
            when {
                expression { !params.SKIP_SONAR }
            }
            steps {
                script {
                    echo "=== VERIFICACIÓN DE QUALITY GATE ==="
                    
                    timeout(time: 30, unit: 'MINUTES') {
                        def qualityGate = waitForQualityGate()
                        
                        if (qualityGate.status != 'OK') {
                            error "❌ Pipeline abortado debido a fallo en Quality Gate: ${qualityGate.status}"
                        } else {
                            echo "✅ SonarQube Quality Gates Pasó: ${qualityGate.status}"
                        }
                    }
                }
            }
        }
        
        stage('Empaquetado') {
            when {
                expression { params.BUILD_TYPE != 'TEST_ONLY' }
            }
            steps {
                script {
                    echo "=== EMPAQUETADO DE ARTEFACTOS ==="
                    
                    def modules = ['base', 'cliente', 'proveedor', 'mascota', 'rrhh']
                    
                    modules.each { module ->
                        if (fileExists(module)) {
                            dir(module) {
                                sh 'mvn package -DskipTests'
                                
                                // Archivar artefactos
                                archiveArtifacts artifacts: "target/*.jar", fingerprint: true
                                archiveArtifacts artifacts: "target/site/**/*", fingerprint: true
                            }
                        }
                    }
                }
            }
        }
        
        stage('Reportes y Documentación') {
            steps {
                script {
                    echo "=== GENERACIÓN DE REPORTES ==="
                    
                    // Generar reporte consolidado
                    sh '''
                        echo "=== REPORTE DE BUILD COMPLETADO ===" > build-report.txt
                        echo "Build Number: ${BUILD_NUMBER}" >> build-report.txt
                        echo "Build URL: ${BUILD_URL}" >> build-report.txt
                        echo "Fecha: $(date)" >> build-report.txt
                        echo "Duración: ${currentBuild.durationString}" >> build-report.txt
                        echo "" >> build-report.txt
                        echo "=== MÓDULOS PROCESADOS ===" >> build-report.txt
                        echo "- Base: ✅" >> build-report.txt
                        echo "- Cliente: ✅" >> build-report.txt
                        echo "- Proveedor: ✅" >> build-report.txt
                        echo "- Mascota: ✅" >> build-report.txt
                        echo "- RRHH: ✅" >> build-report.txt
                        echo "" >> build-report.txt
                        echo "=== PRUEBAS UNITARIAS ===" >> build-report.txt
                        echo "Ejecutadas: ${!params.SKIP_TESTS}" >> build-report.txt
                        echo "Cobertura mínima requerida: ${env.COVERAGE_THRESHOLD}%" >> build-report.txt
                        echo "" >> build-report.txt
                        echo "=== ANÁLISIS DE CALIDAD ===" >> build-report.txt
                        echo "SonarQube: ${!params.SKIP_SONAR}" >> build-report.txt
                        echo "Quality Gate: ${currentBuild.result == 'SUCCESS' ? 'PASÓ' : 'FALLÓ'}" >> build-report.txt
                    '''
                    
                    archiveArtifacts artifacts: 'build-report.txt', fingerprint: true
                }
            }
        }
    }
    
    post {
        always {
            script {
                echo "=== LIMPIEZA POST-BUILD ==="
                
                // Limpiar archivos temporales
                sh 'find . -name "*.tmp" -delete'
                sh 'find . -name "*.log" -size +10M -delete'
                
                // Generar estadísticas del build
                def duration = currentBuild.durationString
                def result = currentBuild.result
                
                echo """
                ========================================
                🏁 BUILD COMPLETADO
                ========================================
                📊 Resultado: ${result}
                ⏱️  Duración: ${duration}
                🔢 Build Number: ${BUILD_NUMBER}
                🌐 Build URL: ${BUILD_URL}
                ========================================
                """
            }
        }
        
        success {
            script {
                echo "✅ Pipeline ejecutado exitosamente"
                
                // Notificaciones de éxito (opcional)
                // emailext (
                //     subject: "✅ Build Exitoso - sgVet #${BUILD_NUMBER}",
                //     body: "El build ${BUILD_NUMBER} se completó exitosamente.\n\nVer detalles: ${BUILD_URL}",
                //     to: "team@sgvet.com"
                // )
            }
        }
        
        failure {
            script {
                echo "❌ Pipeline falló"
                
                // Notificaciones de fallo (opcional)
                // emailext (
                //     subject: "❌ Build Falló - sgVet #${BUILD_NUMBER}",
                //     body: "El build ${BUILD_NUMBER} falló.\n\nVer detalles: ${BUILD_URL}",
                //     to: "team@sgvet.com"
                // )
            }
        }
        
        unstable {
            script {
                echo "⚠️ Pipeline inestable (algunas pruebas fallaron)"
            }
        }
    }
}