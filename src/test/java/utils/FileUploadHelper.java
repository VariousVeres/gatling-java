package utils;


import java.io.File;

public class FileUploadHelper {

    private static final File PAYLOADS_DIR = new File(ConfigLoader.loadProperty("payloads.folder"));

    public static File getPayload(String fileName) {
        String path = PAYLOADS_DIR.getAbsolutePath() + File.separator + fileName;
        return new File(path);
    }


}
