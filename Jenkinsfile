#!/usr/bin/env groovy

def gv

pipeline {
    agent any
    tools {
        maven 'maven-3.9'
    }
    stages {
        stage('init') {
            steps {
                script {
                    gv = load 'script.groovy'
                }
            }
        }
        stage('test') {
            steps {
                script {
                    gv.test()
                }
            }
        }
        stage('bump version') {
            steps {
                script {
                    gv.bumpVersion()
                }
            }
        }
        stage('build jar') {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }
        stage('build image') {
            when {
                expression {
                    return env.BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                    gv.buildImage()
                }
            }
        }
        stage('deploy') {
            steps {
                script {
                    gv.deploy()
                }
            }
        }
    }
}
