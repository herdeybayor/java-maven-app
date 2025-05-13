def gv

pipeline {
    agent any
    parameters {
        string(name: 'APP_NAME', defaultValue: 'MyApp', description: 'Name of the application')
        booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Run tests after build')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Deployment environment')
    }
    stages {
        stage('init') {
            steps {
                script {
                    gv = load 'script.groovy'
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    gv.buildApp()
                }
            }
        }

        stage('Test') {
            when {
                expression { params.RUN_TESTS }
            }
            steps {
                script {
                    gv.testApp()
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}
