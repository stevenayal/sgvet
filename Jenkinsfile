pipeline {
    agent any // Specifies that the pipeline can run on any available agent
    tools {
            maven 'mvn' // Replace with your configured Maven tool name
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