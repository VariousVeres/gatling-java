package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    public static String loadProperty(String property) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        try (InputStream resourceStream = loader.getResourceAsStream("test.properties")) {
            props.load(resourceStream);
            System.out.println("Properties file" + property + " loaded");
        } catch (IOException e) {
            System.out.println("Error properties file loaded");
        }
        return props.getProperty(property);
    }
}
