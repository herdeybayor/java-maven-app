def test() {
    echo 'Running tests...'
    sh 'mvn test'
}

def buildJar() {
    echo 'Building the application...'
    sh 'mvn package'
}

def buildImage() {
    echo 'Building the docker image...'
    withCredentials([usernamePassword(credentialsId: 'docker-hub-cred', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
        sh 'docker build -t herdeybayor/java-maven-app:jma-2.0 .'
        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
        sh 'docker push herdeybayor/java-maven-app:jma-2.0'
    }
}
def deploy() {
    echo 'Deploying the application...'
}
return this
