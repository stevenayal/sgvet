pipeline {
    agent any // Specifies that the pipeline can run on any available agent

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/UCOM-Seminario1/sgvet.git' 
            }
        }

        stage('Build & SonarQube Analysis') {
           steps {
                dir('base') { // Cambia 'base' por el nombre de tu carpeta
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