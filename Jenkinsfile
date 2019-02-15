#!/usr/bin/env groovy
pipeline {
    agent any 
    stages {
        stage('clone github repo') {
            steps {
                git credentialsId: '1', url: 'https://github.com/IKAMTeam/test-uat-trackor.onevizion' 
            }
        }
        stage('clean') {
            steps {
                bat script: 'mvn clean'
            }
        }
        stage('install') {
            steps {
                bat script: 'mvn install'
            }
        }
    }
}
