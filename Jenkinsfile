#!/usr/bin/env groovy
pipeline {
    agent any 
    stages {
        stage('clone github repo') {
            steps {
                git credentialsId: '2', url: 'https://github.com/TJuliaV/test-uat-trackor.onevizion' 
            }
        }
        stage('clean') {
            steps {
                bat label: 'installation', script: 'mvn clean'
            }
        }
        stage('install') {
            steps {
                bat label: 'installation', script: 'mvn install'
            }
        }
    }
}
