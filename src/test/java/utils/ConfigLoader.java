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


    public static String prepareConfigsToWriteInExcel(int... varargs) {
        if (varargs.length == 1) {
            return "At once users: " + varargs[0];
        }
        if (varargs.length == 2) {
            return "Ramp users: " + varargs[0] + ", Ramp duration " + varargs[1];
        }
        if (varargs.length == 3) {
            return "Ramp users: " + varargs[1] + ", Ramp duration: " + varargs[2] +", At once users: " + varargs[0];
        } else
            return "Something wrong with injection profile";
    }
}
