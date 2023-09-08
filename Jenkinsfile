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
        withCredentials([string(credentialsId: 'da729162-2128-4bb1-8195-8e468a2d19b0', variable: 'MP')]) {
          script {
          withAWS(credentials: 'd2bc6db5-09c6-4790-8826-09fa28fc8ea0', region: settings.region) {
            tools.addInboundRuleForSecurityGroup("base-staging1-be-alb-s", 443, tools.getAgentPublicIp())
            try {
              docker.image('maven:3.8.6-openjdk-11').inside(
                            """
                            --entrypoint=''
                            """){
                              sh '''

                mvn clean gatling:test -Dgatling.simulationClass=simulations.BasicManagerSimulation;
                              '''
                          }
            } finally {
              tools.removeInboundRuleForSecurityGroup("base-staging1-be-alb-s", 443, tools.getAgentPublicIp())
            }
          }
        }}
       }
      }
    }
}