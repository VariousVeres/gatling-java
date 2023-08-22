package utils;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.http;

public class APIConfig {
    private final static String ENV = System.getProperty("environment");
    private final static String BASE_URL = "https://api." + ENV + ".doo.net/v1";

    private static HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
            .acceptHeader("application/json")
//            .header("Authorization", "Bearer ")
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("en-US,en;q=0.8,de-DE,de;q=0.5");


    public static HttpProtocolBuilder getHttpProtocol() {
        return httpProtocol;
    }

    public static String gatBaseURL() {
        return BASE_URL;
    }


}
