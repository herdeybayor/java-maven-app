def test() {
    echo 'Running tests...'
    sh 'mvn test'
}

def buildJar() {
    echo 'Bumping project version...'
    sh """
        mvn build-helper:parse-version versions:set \
          -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} \
          versions:commit
    """
    echo 'Building the application...'
    sh 'mvn package'
}

def buildImage() {
    def version = readMavenPom().getVersion()
    echo "Building Docker image with version ${version}"
    
    // Enable Docker BuildKit for more efficient builds
    env.DOCKER_BUILDKIT = '1'
    
    withCredentials([usernamePassword(credentialsId: 'docker-hub-cred', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
        // Login once at the beginning
        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
        
        try {
            // Build with cache options and tag both with version and latest
            sh "docker build --pull --cache-from herdeybayor/java-maven-app:latest -t herdeybayor/java-maven-app:jma-${version} -t herdeybayor/java-maven-app:latest ."
            
            // Push both tags
            sh "docker push herdeybayor/java-maven-app:jma-${version}"
            sh "docker push herdeybayor/java-maven-app:latest"
            
            echo 'Docker image built and pushed successfully!'
        } finally {
            // Cleanup regardless of success or failure
            echo 'Cleaning up...'
            sh 'docker logout'
            sh "docker rmi herdeybayor/java-maven-app:jma-${version} || true"
            sh "docker rmi herdeybayor/java-maven-app:latest || true"
        }
    }
}

def deploy() {
    echo 'Deploying the application...'
}
return this
