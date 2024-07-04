pipeline {
    agent {
        label 'agent1'
    }
 tools{
     jdk 'jdk17'
     maven 'maven3'
 }
def bit_bucket_repo_url="https://github.com/sumitgit01/seh-students.git"   
def bitBucketCredentialsid="OWNSEH" 

    stages {
        stage('Which Java?') {
            steps {
                sh 'java --version'
            }
        }
        stage('checkout SCM') {
                steps {
                    cleanWs()
                    /* sh """
                    git clone https://github.com/sumitgit01/seh-students.git
                    cd seh-students/
                    #git checkout feature/devops
                    """ */
                    git branch: 'main', url: "${bit_bucket_repo_url}", credentialsId: "${bitBucketCredentialsid}"


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
            steps {
                dir("${WORKSPACE}/seh-students/target"){
                    sh """
                        chmod +x seh-students.jar
                        java -jar seh-students.jar
                    """
                }
            }
        }
    }
    post {
        failure {
            cleanWs()
        }
    }
}
