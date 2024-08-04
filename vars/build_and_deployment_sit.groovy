pipeline {
    agent any
//added test
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
    }
}
