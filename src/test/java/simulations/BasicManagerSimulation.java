package simulations;

import config.APIConfig;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.*;


import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.Simulation;

public class BasicManagerSimulation extends Simulation {

    @Override
    public void before() {
        System.out.println("Simulation is about to start");
    }

    private HttpProtocolBuilder httpProtocol = APIConfig.getHttpProtocol();


    ScenarioBuilder scn1 = scenario("Test") // 7
            .exec(http("Relative")
                    .get("/organization/189932/statistics/events"));


    {
        setUp(scn1.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }


}
