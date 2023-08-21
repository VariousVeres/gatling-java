//package simulations;
//
//import io.gatling.javaapi.core.ScenarioBuilder;
//import io.gatling.javaapi.http.*;
//import io.gatling.javaapi.core.Simulation;
//
//import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
//import static io.gatling.javaapi.core.CoreDsl.scenario;
//import static io.gatling.javaapi.http.HttpDsl.http;
//
//public class TestSimulation extends Simulation {
//    HttpProtocolBuilder httpProtocol = http.baseUrl("https://gatling.io");
//
//    ScenarioBuilder scn = scenario("Scenario")
//            // will make a request to "https://gatling.io/docs/"
//            .exec(
//                    http("Relative").get("/docs/")
//            )
//            // will make a request to "https://github.com/gatling/gatling"
//            .exec(
//                    http("Absolute").get("https://github.com/gatling/gatling")
//            );
//
//    {
//
//        setUp(scn.injectOpen(atOnceUsers(1)).protocols(httpProtocol));
//    }
//}
