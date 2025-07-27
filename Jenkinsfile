pipeline {
    agent any // Specifies that the pipeline can run on any available agent
    tools {
            maven 'mvn' // Replace with your configured Maven tool name
        }
    environment {
        SONARQUBE_ENV = 'sonarqube' // Este nombre debe coincidir con la configuración de SonarQube en Jenkins (Manage Jenkins > Configure System)
    }
    stages {
        
        stage('Build') {
           steps {

                dir('base') { // Cambia 'base' por el nombre de tu carpeta
                    sh 'mvn clean install'
                }

                dir('cliente') { // Cambia 'base' por el nombre de tu carpeta
                    sh 'mvn clean install'
                }
                dir('proveedor') { // Cambia 'base' por el nombre de tu carpeta
                    sh 'mvn clean install'
                }
                dir('mascota') { // Cambia 'base' por el nombre de tu carpeta
                    sh 'mvn clean install'
                }
                dir('rrhh') {
                    sh 'mvn clean install'
                }
            }
        }
         stage('SonarQube Analysis') {
            steps {
                dir('base') {
                    withSonarQubeEnv("${SONARQUBE_ENV}") {
                        sh 'mvn sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.projectKey=sgVet -Dsonar.projectName=SgVet-Base'
                    }
                }
                dir('rrhh') {
                    withSonarQubeEnv("${SONARQUBE_ENV}") {
                        sh 'mvn sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.projectKey=sgVet-rrhh -Dsonar.projectName=SgVet-RRHH'
                    }
                }
            }
        }

        // stage('Quality Gate Check') {
        //     steps {
        //         timeout(time: 1, unit: 'HOURS') { // Timeout for the quality gate check
        //             // 'waitForQualityGate' waits for the SonarQube analysis to complete and checks the quality gate status
        //             def qualityGate = waitForQualityGate()
        //             if (qualityGate.status != 'OK') {
        //                 error "Pipeline aborted due to quality gate failure: ${qualityGate.status}"
        //             } else {
        //                 echo "SonarQube Quality Gates Passed: ${qualityGate.status}"
        //             }
        //         }
        //     }
        // }
    }
}