#!/usr/bin/env groovy
def url_repo = 'https://github.com/IKAMTeam/test-uat-trackor.onevizion'
def server_url = 'https://uat-trackor.onevizion.com/Default.do'
def branch = '19.2'
def strings = url_repo.split('/')
def project_name = strings[strings.length-1]
pipeline {
    agent any
    stages {
        stage ('clone') {
            steps {
                git credentialsId: 'onevizion-github', url: url_repo, branch: branch
            }
        }
        stage('clean') {
            steps {
                sh script: 'mvn clean'
            }
        }
        stage('compile') {
            steps {
                sh script: 'mvn compile'
            }
        }
        stage('test') {
            steps {
                withCredentials([usernamePassword(credentialsId: "$project_name", passwordVariable: 'user_password', usernameVariable: 'user_name')]) {
                    sh "mvn exec:java -Dexec.mainClass=com.onevizion.guitest.TestNgRunAll -Dtest.selenium.user=$user_name -Dtest.selenium.password=$user_password -Dtest.selenium.serverUrl=$server_url"
                }
            }
        }
    }
}