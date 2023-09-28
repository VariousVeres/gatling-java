package simulations;

import io.gatling.javaapi.core.*;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
import utils.APIConfig;
import io.gatling.javaapi.http.*;


import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BasicManagerSimulation extends Simulation {
    //1

    private int atOnceUsersCount1 = 1;

    private Duration duration = Duration.ofMinutes(60);
    private Duration pause = Duration.ofMinutes(5);

    //2
    private int atOnceUsersCount2 = 3;
    private int rampUsersCount2 = 3;
    private int rampUsersDuration2 = 5;

    @Override
    public void before() {
        System.out.println("Basic manager simulation is about to start");
    }

    private long buildDurationMinutes = Long.parseLong(System.getProperty("buildMinutes"));
    private long pauseBetweenRequestsDurationSeconds = Long.parseLong(System.getProperty("pauseSeconds"));

    private HttpProtocolBuilder httpProtocol = APIConfig.getHttpProtocol();

    private Assertion myAssertion = global().successfulRequests().percent().is(100d);

    String payload = "payloads/" + APIConfig.getEnv() + "_real_payload.json";

    ScenarioBuilder scenario = scenario("Order")

            .exitBlockOnFail(exec(session -> session.set("endpoint_name", "/orders"))
//                    .during(Duration.ofMinutes(buildDurationMinutes)).on(
                    .exec(http("Order performing")
                            .post(session -> session.getString("endpoint_name"))
                            .body(ElFileBody(payload)).asJson()
                                    .check(status().is(201))
                                    .check(responseTimeInMillis().saveAs("response_time")))
                            .exec(session -> {
                                LocalDateTime dateTime = LocalDateTime.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                String formattedDateTime = dateTime.format(formatter);
                                String config = "Build duration: " + buildDurationMinutes + " min, Pause between requests: " + pauseBetweenRequestsDurationSeconds + " sec";
                                return session.set("date_time", formattedDateTime).set("config", config);
                            })
                            .exec(session -> {
                                        System.out.println("Response time - " + session.getString("response_time"));
                                        System.out.println("Date time - " + session.getString("date_time"));
                                        return session;
                                    }
                            )
                            .exec(http("Webhook sending")
                                    .post("https://hook.doo.integromat.celonis.com/v47mb2n6jo0zbu8077a7upkab1iioc5k")
                                    .header("Content-type", "application/json")
                                    .body(StringBody(session -> "{\"environement\":\"" + APIConfig.getEnv() + "\",\"response_time\":\"" + session.getString("response_time") + "\"," +
                                            "\"time_stamp\":\"" + session.getString("date_time") + "\",\"configuration\":\"" + session.getString("config") + "\"}"))
                            ));
//                            .pause(pauseBetweenRequestsDurationSeconds));


    //1
    PopulationBuilder user1 = scenario.injectOpen(atOnceUsers(atOnceUsersCount1));

    //2
    PopulationBuilder user2 = scenario
            .injectOpen(
                    //HEating if needed
//                    atOnceUsers(1),
//                    nothingFor(3),
//                    atOnceUsers(2),
//                    nothingFor(3),
//                    atOnceUsers(2),
                    nothingFor(10),
                    rampUsers(rampUsersCount2).during(rampUsersDuration2),
                    atOnceUsers(atOnceUsersCount2));


    {
        setUp(scenario.injectOpen(atOnceUsers(atOnceUsersCount1))).protocols(httpProtocol).assertions(myAssertion);
    }

    @Override
    public void after() {
        System.out.println("After method has been started");
        String endpointUrl = "https://hook.doo.integromat.celonis.com/v47mb2n6jo0zbu8077a7upkab1iioc5k";
//        LocalDateTime dateTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedDateTime = dateTime.format(formatter);

//        //1
//        String config1 = ConfigLoader.prepareConfigsToWriteInExcel(atOnceUsersCount1);
//
//        //2
//        String config2 = ConfigLoader.prepareConfigsToWriteInExcel(atOnceUsersCount2, rampUsersCount2, rampUsersDuration2);
//
//        String payload = "{\"environement\":\"" + APIConfig.getEnv() + "\",\"response_time\":\"" + SharedSession.getSharedValue() + "\"," +
//                "\"time_stamp\":\"" + formattedDateTime + "\",\"configuration\":\"" + config1 + "\"}";
//
        try {
            URL url = new URL(endpointUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream();
                 OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
                osw.write("{\"environement\":0}");
                osw.flush();
            }
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Make webhooks with responses results were sent");
    }

}


