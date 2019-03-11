#!/usr/bin/env groovy
def url_repo = 'https://github.com/TJuliaV/test-uat-trackor.onevizion'
def server_url = 'https://uat-trackor.onevizion.com/Default.do'
def strings = url_repo.split('/')
def project_name = strings[strings.length-1]
pipeline {
    agent any
    stages {
        stage ('Clone') {
            steps {
                git credentialsId: 'onevizion-github', url: "$url_repo"
            }
        }
        stage('clean') {
            steps {
                bat script: 'mvn clean'
            }
        }
        stage('compile') {
            steps {
                bat script: 'mvn compile'
            }
        }
        stage('test') {
            steps {
                withCredentials([usernamePassword(credentialsId: "$project_name", passwordVariable: 'user_password', usernameVariable: 'user_name')]) {
                    bat "mvn exec:java -Dexec.mainClass=com.onevizion.guitest.TestNgRunAll -Dtest.selenium.user=$user_name -Dtest.selenium.password=$user_password -Dtest.selenium.serverUrl=$server_url"
                }
            }
        }
    }
}