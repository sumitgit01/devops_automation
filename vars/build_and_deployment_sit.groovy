def bit_bucket_repo_url="https://github.com/sumitgit01/seh-students.git"   
def bitBucketCredentialsid="OWNSEH"

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
                    /* sh """
                    git clone https://github.com/sumitgit01/seh-students.git
                    cd seh-students/
                    #git checkout feature/devops
                    """ */
                    //git branch: 'main', url: "${bit_bucket_repo_url}", credentialsId: "${bitBucketCredentialsid}"
                    checkout([$class: 'GitSCM',
                    branches: [[name: 'main']],
                    userRemoteConfigs: [[url: "${bit_bucket_repo_url}", credentialsId: "${bitBucketCredentialsid}"]],
                    extensions: [[$class: 'CloneOption', depth: 1, noTags: false, reference: '', shallow: true, timeout: 120]]])
                    /* withCredentials([usernameColonPassword(credentialsId: "${bitBucketCredentialsid}", passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]){
                        sh """
                        export GIT_COMMITTER_NAME='CICD_SEH'
                        export GIT_COMMITTER_EMAIL='sumitjoshi1988@gmail.com'
                        git config http.sslverify false 
                        """
                    } */

                }
            }
        stage('build provisioning') {
            steps {
                //dir("${WORKSPACE}/seh-students"){
                dir("${WORKSPACE}"){
                    sh """
                        mvn clean install
                    """
                }
            }
        }
        stage('deploy app') {
            steps {
                //dir("${WORKSPACE}/seh-students/target")
                dir("${WORKSPACE}/target"){
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
            //cleanWs()
            print("job is failing")
        }
    }
}
