package simulations;

import io.gatling.javaapi.core.*;
import utils.APIConfig;
import io.gatling.javaapi.http.*;
import utils.ConfigLoader;
import utils.SharedSession;


import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BasicManagerSimulation extends Simulation {
    //1

    private int atOnceUsersCount1 = 1;

    //2
    private int atOnceUsersCount2 = 3;
    private int rampUsersCount2 = 3;
    private int rampUsersDuration2 = 5;

    @Override
    public void before() {
        System.out.println("Simulation is about to start");
    }

    private HttpProtocolBuilder httpProtocol = APIConfig.getHttpProtocol();

    private Assertion myAssertion = global().successfulRequests().percent().is(100d);

    ScenarioBuilder scenario = scenario("Order")
            .exec(session -> session.set("endpoint_name", "/orders"))
            .exec(http("Relative")
                    .post(session -> session.getString("endpoint_name"))
//                    .post("/orders")
                    .body(ElFileBody("payloads/real_payload.json")).asJson()
                    .check(status().is(201))
                    .check(responseTimeInMillis().saveAs("response_time")))
            .exec(session -> {
                String responseTime = session.getString("response_time");
                System.out.println("Response Time is: " + responseTime + "ms");
                SharedSession.setSharedValue(responseTime);
                SharedSession.setUnrealValue("Test unreal value_$$$$");
                return session;
            });


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
        setUp(user1).protocols(httpProtocol).assertions(myAssertion);
    }

    @Override
    public void after() {
        System.out.println("Response Time from order that will be populated in Excel table: " + SharedSession.getUnrealValue() + " ms");
        String endpointUrl = "https://hook.doo.integromat.celonis.com/v47mb2n6jo0zbu8077a7upkab1iioc5k";
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);

        //1
        String config1 = ConfigLoader.prepareConfigsToWriteInExcel(atOnceUsersCount1);

        //2
        String config2 = ConfigLoader.prepareConfigsToWriteInExcel(atOnceUsersCount2, rampUsersCount2, rampUsersDuration2);

        String payload = "{\"environement\":\"" + APIConfig.getEnv() + "\",\"response_time\":\"" + SharedSession.getSharedValue() + "\"," +
                "\"time_stamp\":\"" + formattedDateTime + "\",\"configuration\":\"" + config1 + "\"}";

        try {
            URL url = new URL(endpointUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream();
                 OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
                osw.write(payload);
                osw.flush();
            }
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Make webhook with :   " + SharedSession.getSharedValue() + " ms reault sent");
    }

}


