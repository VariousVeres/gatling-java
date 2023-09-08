pipeline {
    agent {
            docker {
                image 'maven:3.9.4-eclipse-temurin-11-alpine'
                args '-v /root/.m2:/root/.m2'
            }
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
                        sh 'mvn gatling:test -Dgatling.simulationClass=net.doo.loadtest.simulations.BankTransferBookingSimulation'
                    }
                }

    }
}