package config;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.http;

public class APIConfig {
    private final static String environment = System.getProperty("environment");
    private final static String baseURL = "https://api." + environment + ".doo.net/v1";

    private static  HttpProtocolBuilder httpProtocol = http
            .baseUrl(baseURL)
            .acceptHeader("application/json")
            .header("Authorization", "Bearer ")
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("en-US,en;q=0.8,de-DE,de;q=0.5");


    public static HttpProtocolBuilder getHttpProtocol()  {
        return httpProtocol;
    }





}
