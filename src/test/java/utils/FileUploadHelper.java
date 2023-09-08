package utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;

public class FileUploadHelper {

    private static final File PAYLOADS_DIR = new File(ConfigLoader.loadProperty("payloads.folder"));
    private static final File REPORTS_FOLDER = new File(ConfigLoader.loadProperty("reports.folder"));
    
    public static File getPayload(String fileName) {
        //Not necessary because we are using gatling ElFileBody
        String path = PAYLOADS_DIR.getAbsolutePath() + File.separator + fileName;
        return new File(path);
    }

    public static File getReportFile() throws FileNotFoundException {
        File lastRunFolder = null;
        File[] files = REPORTS_FOLDER.listFiles();
        System.out.println(files.toString());
        if (files != null && files.length > 0) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
            lastRunFolder = files[0];
            System.out.println("Created on: " + lastRunFolder);
        } else {
            System.out.println("No files found in the directory.");
        }

        System.out.println("File with report name: " + lastRunFolder + File.separator + "index.html");
//        return new File(lastRunFolder.getAbsolutePath() + File.separator + "req_relative-2ca94.html");
        return new File(lastRunFolder.getAbsolutePath() + File.separator + "index.html");
    }


}
