pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Checkout your source code from your repository
                script {
                    git branch: 'main', url: 'https://github.com/RodrigoAmora/eventosti_backend.git'
                }
            }
        }
        
        stage('Build') {
            steps {
                // Use Maven to build your project
                dir('eventosti_backend') {
                    sh 'mvn -f pom.xml clean package'
                }
            }
        }
        
        stage('Upload to S3') {
            steps {
                script {
                    // Copy the JAR file to the workspace
                    sh 'cp target/*.jar $WORKSPACE'
                    
                    // Upload the JAR file to S3 bucket
                    sh 'aws s3 cp $WORKSPACE/*.jar s3://eventosti/'
                }
            }
        }
    }