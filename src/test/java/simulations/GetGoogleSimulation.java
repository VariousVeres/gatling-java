package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import utils.APIConfig;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class GetGoogleSimulation extends Simulation {
    ScenarioBuilder scenario = scenario("Get on google.com")
            .exec(http("Making GAT on Google gom")
                    .get("https://google.com")
                    .check(status().is(201))
                    .check(responseTimeInMillis().saveAs("response_time")));

    {
        setUp(scenario.injectOpen(atOnceUsers(1))).protocols(APIConfig.getHttpProtocol());
    }
}
