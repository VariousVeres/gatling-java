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
          sh "ls -la"
          docker.image('maven:3.8.6-openjdk-11').inside(
            """
            --entrypoint=''
            """){
                    sh '''
  mvn -f /opt/jenkins/workspace/AcceptanceTests/Load-testing/pom.xml clean gatling:test -Dgatling.simulationClass=simulations.BasicManagerSimulation;
                    '''
                }
            }
          }

        }
    }
}