def gv

pipeline {
    agent any
    parameters {
        string(name: 'APP_NAME', defaultValue: 'MyApp', description: 'Name of the application')
        booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Run tests after build')
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
            input {
                message "Select the environment to deploy ${params.APP_NAME}"
                ok 'Deploy'
                parameters {
                    choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Deployment environment')
                }
            }
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}
