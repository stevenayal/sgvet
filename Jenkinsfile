pipeline {
    agent any
    
    tools {
        maven 'mvn'
        jdk 'jdk-11'
    }
    
    environment {
        SONARQUBE_ENV = 'sonarqube'
        JAVA_HOME = tool('jdk-11')
        MAVEN_OPTS = '-Xmx2048m -XX:MaxPermSize=512m'
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
                    echo "Java Version: ${JAVA_HOME}"
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
                            dir(module) {
                                echo "Analizando dependencias de ${module}..."
                                sh 'mvn dependency:tree -DoutputFile=dependency-tree.txt'
                                archiveArtifacts artifacts: "${module}/dependency-tree.txt", fingerprint: true
                            }
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
                        dir('base') {
                            script {
                                echo "=== COMPILANDO MÓDULO BASE ==="
                                sh 'mvn clean compile -q'
                                echo "✅ Compilación de Base completada"
                            }
                        }
                    }
                }
                
                stage('Compilar Cliente') {
                    steps {
                        dir('cliente') {
                            script {
                                echo "=== COMPILANDO MÓDULO CLIENTE ==="
                                sh 'mvn clean compile -q'
                                echo "✅ Compilación de Cliente completada"
                            }
                        }
                    }
                }
                
                stage('Compilar Proveedor') {
                    steps {
                        dir('proveedor') {
                            script {
                                echo "=== COMPILANDO MÓDULO PROVEEDOR ==="
                                sh 'mvn clean compile -q'
                                echo "✅ Compilación de Proveedor completada"
                            }
                        }
                    }
                }
                
                stage('Compilar Mascota') {
                    steps {
                        dir('mascota') {
                            script {
                                echo "=== COMPILANDO MÓDULO MASCOTA ==="
                                sh 'mvn clean compile -q'
                                echo "✅ Compilación de Mascota completada"
                            }
                        }
                    }
                }
                
                stage('Compilar RRHH') {
                    steps {
                        dir('rrhh') {
                            script {
                                echo "=== COMPILANDO MÓDULO RRHH ==="
                                sh 'mvn clean compile -q'
                                echo "✅ Compilación de RRHH completada"
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
                        dir('base') {
                            script {
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
                        }
                    }
                }
                
                stage('Pruebas Cliente') {
                    steps {
                        dir('cliente') {
                            script {
                                echo "=== EJECUTANDO PRUEBAS UNITARIAS - CLIENTE ==="
                                sh 'mvn test -Dmaven.test.failure.ignore=true'
                                publishTestResults testResultsPattern: '**/surefire-reports/*.xml'
                            }
                        }
                    }
                }
                
                stage('Pruebas Proveedor') {
                    steps {
                        dir('proveedor') {
                            script {
                                echo "=== EJECUTANDO PRUEBAS UNITARIAS - PROVEEDOR ==="
                                sh 'mvn test -Dmaven.test.failure.ignore=true'
                                publishTestResults testResultsPattern: '**/surefire-reports/*.xml'
                            }
                        }
                    }
                }
                
                stage('Pruebas Mascota') {
                    steps {
                        dir('mascota') {
                            script {
                                echo "=== EJECUTANDO PRUEBAS UNITARIAS - MASCOTA ==="
                                sh 'mvn test -Dmaven.test.failure.ignore=true'
                                publishTestResults testResultsPattern: '**/surefire-reports/*.xml'
                            }
                        }
                    }
                }
                
                stage('Pruebas RRHH') {
                    steps {
                        dir('rrhh') {
                            script {
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