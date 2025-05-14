def test() {
    echo 'Running tests...'
    sh 'mvn test'
}

def bumpVersion() {
    echo 'Bumping project version...'
    sh 'mvn build-helper:parse-version versions:set \
          -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} versions:commit'
    def matcher = readFile('pom.xml') =~ /<version>(.*?)<\/version>/
    def version = matcher[0][1]
    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
    echo "Image name: ${env.IMAGE_NAME}"
}

def buildJar() {
    echo 'Building the application...'
    sh 'mvn package'
}

def buildImage() {
    echo "Building Docker image with version ${env.IMAGE_NAME}"
    
    // Enable Docker BuildKit for more efficient builds
    env.DOCKER_BUILDKIT = '1'
    
    withCredentials([usernamePassword(credentialsId: 'docker-hub-cred', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
        // Login once at the beginning
        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
        
        try {
            // Build with cache options and tag both with version and latest
            sh "docker build --pull --cache-from herdeybayor/java-maven-app:latest -t herdeybayor/java-maven-app:$IMAGE_NAME -t herdeybayor/java-maven-app:latest ."
            
            // Push both tags
            sh "docker push herdeybayor/java-maven-app:$IMAGE_NAME"
            sh "docker push herdeybayor/java-maven-app:latest"
            
            echo 'Docker image built and pushed successfully!'
        } finally {
            // Cleanup regardless of success or failure
            echo 'Cleaning up...'
            sh 'docker logout'
            sh "docker rmi herdeybayor/java-maven-app:$IMAGE_NAME || true"
            sh "docker rmi herdeybayor/java-maven-app:latest || true"
        }
    }
}

def deploy() {
    echo 'Deploying the application...'
}

def commitVersion() {
    withCredentials([sshUserPrivateKey(credentialsId: 'git-herdeybayor', keyFileVariable: 'SSH_KEY')]) {
        sh 'eval `ssh-agent -s`'
        sh 'ssh-add "$SSH_KEY"'
        sh 'git add pom.xml'
        sh "git commit -m 'Bump version to ${env.IMAGE_NAME}'"
        sh 'git push origin HEAD:main'
        sh 'ssh-agent -k'
    }
}
return this
