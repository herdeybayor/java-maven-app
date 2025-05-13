def buildApp() {
    echo 'building the application...'
}

def testApp() {
    echo 'testing the application...'
}

def deployApp() {
    echo 'Deploying the application'
    if (ENVIRONMENT == 'prod') {
        echo 'This is a production deployment!'
    } else {
        echo "Deploying to ${ENVIRONMENT} environment"
    }
}
return this
