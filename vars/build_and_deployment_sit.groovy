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
                    cd seh-students/
                    #git checkout feature/devops
                    """
                }
            }
        stage('build provisioning') {
            steps {
                dir("${WORKSPACE}/seh-students"){
                    sh """
                    mvn clean install
                    """
                }
            }
        }
        stage('deploy app') {
            dir("${WORKSPACE}/seh-students/target"){
                sh """
                    chmod +x seh-students.jar
                    java -jar seh-students.jar
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
