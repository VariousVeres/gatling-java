## Load tests empowered by Gatling

To run tests, call Maven: `mvn gatling:test`

Additional parameters available from command line:
* `-Dgatling.simulationClass=simulations.BasicManagerSimulation` - Set simulation to run
* `-Denvironment=staging1=<staging>` - override environment property from pom file

To see report, open `<project root>\target\site\serenity\index.html`