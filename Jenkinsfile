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
          script {
            docker.image('maven:3.8.6-openjdk-11').inside(
              """
              --entrypoint=''
              """){
                sh '''

  mvn clean gatling:test -Dgatling.simulationClass=simulations.BasicManagerSimulation;
                '''
            }
          }

       }
      }
    }
}