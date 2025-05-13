pipeline {
    agent any
    parameters {
        string(name: 'APP_NAME', defaultValue: 'MyApp', description: 'Name of the application')
        booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Run tests after build')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Deployment environment')
    }
    stages {
        stage('Build') {
            steps {
                echo 'Bulding the application'
            }
        }

        stage('Test') {
            when {
                expression { params.RUN_TESTS }
            }
            steps {
                echo 'Testing the application'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying the application'
                echo "Deploying ${params.APP_NAME} to ${params.ENVIRONMENT} environment"
            }
        }
    }
}
