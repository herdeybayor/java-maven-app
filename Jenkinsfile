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
        stage('build jar') {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                    gv.buildJar()
                }
            }
        }
        stage('build image') {
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
