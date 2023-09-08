pipeline {
    agent {
      label 'docker'
    }
    parameters {
        string(
            name: 'BRANCH',
            defaultValue: 'master',
            description: '',
            trim: true
        )
        choice(
            name: 'INFRASTRUCTURE',
            choices: ['staging1', 'staging2', 'staging3', 'staging4', 'staging5', 'staging6', 'staging7', 'staging8'],
            description: ''
        )
        choice(
            name: 'MULTIPLIER',
            choices: [1, 2, 3, 5],
            description: ''
        )
      }
    stages {
      stage('Build') {
        steps {
          docker.image('maven:3-eclipse-temurin-11').inside(
          """
          --entrypoint=''
          -v ${PWD}:/var/project
          """){
                  sh '''
mvn -f /var/project/pom.xml clean gatling:test -Dmp=master -Denvironment=staging1 -Dmultiplier=1
                  '''
              }
            }
        }
    }
}