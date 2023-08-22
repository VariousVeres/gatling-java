package simulations;

import utils.APIConfig;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.*;


import static io.gatling.javaapi.core.CoreDsl.*;
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
                    .post("/orders").body(ElFileBody("payloads/test_payload.json")).asJson().check(status().is(201)));

    {
        setUp(scn1.injectOpen(atOnceUsers(3))).protocols(httpProtocol);
    }


}
