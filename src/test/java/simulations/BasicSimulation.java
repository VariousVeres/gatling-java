package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.*;


import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.Simulation;

public class BasicSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = http
//            .baseUrl("") // 5
            .acceptHeader("application/json")
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("en-US,en;q=0.8,de-DE,de;q=0.5")
            .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0");

    ScenarioBuilder scn1 = scenario("BasicSimulation1") // 7
            .exec(http("request_1") // 8
                    .get("https://staging1.doo.net/veranstaltung/261655/buchung")) // 9
            .pause(5); // 10

    ScenarioBuilder scn2 = scenario("BasicSimulation2")
            .exec(http("request_2") // 8
                    .get("https://www.google.com/"))
            .pause(5); // 10

    {
        setUp( // 11
                scn2.injectOpen(atOnceUsers(1)) // 12
        ).protocols(httpProtocol); // 13
    }

}
