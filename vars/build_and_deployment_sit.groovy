pipeline {
    agent agent1
 tools{
     jdk 'jdk17'
     maven 'maven3'
 }
    stages {
        stage('Which Java?') {
            steps {
                sh 'java --version'
            }
        }
    stage('checkout SCM') {
            steps {
                sh 'java --version'
            }
        }
    stage('build provisioning') {
            steps {
                sh """
                mvn clean install
                ls -alrt
                """
            }
        }
    }
}
