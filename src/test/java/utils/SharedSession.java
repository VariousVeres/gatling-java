package utils;

public class SharedSession {
    //Class to store values globally
    private static String sharedValue;
    private static String unrealValue;

    public static synchronized String getSharedValue() {
        return sharedValue;
    }

    public static synchronized String getUnrealValue() {
        return unrealValue;
    }

    public static synchronized void setSharedValue(String value) {
        sharedValue = value;
    }

    public static synchronized void setUnrealValue(String value) {
        unrealValue = value;
    }
}
