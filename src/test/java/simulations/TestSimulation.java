package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import utils.APIConfig;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class TestSimulation extends Simulation {
    ScenarioBuilder scenario = scenario("Order")
            .exec(session -> session.set("endpoint_name", "/orders"))
//            during(Duration.ofMinutes(3)).on(
            .exec(http("Relative")
                    .post(session -> session.getString("endpoint_name"))
                    .body(ElFileBody("payloads/real_payload.json")).asJson()
                    .check(status().is(201))
                    .check(responseTimeInMillis().saveAs("response_time")));

    {
        setUp(scenario.injectOpen(atOnceUsers(1))).protocols(APIConfig.getHttpProtocol());
    }
}
