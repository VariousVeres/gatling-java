//package simulations;
//
//import io.gatling.javaapi.core.ScenarioBuilder;
//import io.gatling.javaapi.http.*;
//
//
//import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
//import static io.gatling.javaapi.core.CoreDsl.scenario;
//import static io.gatling.javaapi.http.HttpDsl.*;
//
//import io.gatling.javaapi.core.Simulation;
//
//public class BasicBuyerSimulation extends Simulation {
//    HttpProtocolBuilder httpProtocol = http.baseUrl("https://staging1.doo.net/manager/event");
//
//    ScenarioBuilder scn = scenario("Scenario")
//            // will make a request to "https://gatling.io/docs/"
//            .exec(
//                    http("Relative").get("/event/264412/details/")
//            )
//            // will make a request to "https://github.com/gatling/gatling"
//            .exec(
//                    http("Absolute").get("https://staging1.doo.net/manager/event/264412/details")
//            );
//
//    {
//
//        setUp(scn.injectOpen(atOnceUsers(1)).protocols(httpProtocol));
//    }
//}
