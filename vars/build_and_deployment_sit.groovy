pipeline {
    agent {
        label 'agent1'
    }
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
                cleanWs()
                sh """
                git clone https://github.com/sumitgit01/seh-students.git
                git checkout feature/devops
                """
            }
        }
    stage('build provisioning') {
            steps {
                sh """
                cd seh-students/
                mvn clean install
                """
            }
        }
    }
    post {
        failure {
            cleanWs()
        }
    }
}
