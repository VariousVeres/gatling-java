package simulations;

import io.gatling.javaapi.core.Session;
import utils.APIConfig;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.*;


import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.Simulation;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BasicManagerSimulation extends Simulation {

    @Override
    public void before() {
        System.out.println("Simulation is about to start");
    }

    private HttpProtocolBuilder httpProtocol = APIConfig.getHttpProtocol();

    ScenarioBuilder scn1 = scenario("Order") // 7
            .exec(http("Relative")
                    .post("/orders").body(ElFileBody("payloads/real_payload.json")).asJson()
                    .check(status().is(201))
                    .check(responseTimeInMillis().saveAs("responseTime")))
            .exec(session -> {
                String responseTime = session.getString("responseTime");
                System.out.println("Response Time: " + responseTime + "ms");
                Session newSession = session.set(responseTime, "sharedValue");
                SharedSession.set(responseTime, "sharedValue");
                return newSession;
            });

    {
        setUp(scn1.injectOpen(atOnceUsers(5))
                .protocols(httpProtocol));
    }

    @Override
    public void after() {
        System.out.println("Response Time from Order: " + SharedSession.get() + " ms");
        String endpointUrl = "https://hook.doo.integromat.celonis.com/v47mb2n6jo0zbu8077a7upkab1iioc5k";
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);

        String payload = "{\"environement\":\"" + APIConfig.getEnv() + "\",\"response_time\":\"" + SharedSession.get() + "\"," +
                "\"time_stamp\":\"" + formattedDateTime + "\",\"configuration\":\"" + "HERE" + "\"}";


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
        System.out.println("Make webhook with :   " + SharedSession.get() + " ms sent");
    }


}

class SharedSession {
    private static String sharedValue;

    public static synchronized String get() {
        return sharedValue;
    }

    public static synchronized void set(String value, String s) {
        sharedValue = value;
    }
}
