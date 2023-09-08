package simulations;

import io.gatling.app.Gatling;
import io.gatling.core.config.GatlingPropertiesBuilder;
import utils.ConfigLoader;
import utils.FileUploadHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class GatlingRunner {
    public static void main(String[] args) throws Exception {
        //We will have an ability to run some code after simulation (but it is omiting all maven stuff)

        String className = BasicManagerSimulation.class.getName();

        GatlingPropertiesBuilder props = new GatlingPropertiesBuilder();
        props.resultsDirectory("target/gatling");
        props.simulationClass(className);
        Gatling.fromMap(props.build());

        System.out.println("Additional Java code running...");

        File reportFile = null;
        try {
            reportFile = FileUploadHelper.getReportFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Report file is - " + reportFile);
//
//        Document document;
//        try {
//            document = Jsoup.parse(new File(reportFile.getAbsolutePath()), "UTF-8");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        Element tdElement1 = document.select("span.table-cell-tooltip").first();
//        Element tdElement2 = document.select("div .onglet").first();
//
////         Get the text value of the <td> element
//        String meanResponseTime1 = tdElement1.val();
//        String meanResponseTime2 = tdElement2.text();
////        int number = Integer.parseInt(meanResponseTime2);//
//        System.out.println("REPORT FILE NAME IS - " + reportFile.getAbsolutePath());//
//        System.out.println("Mean Response Time: " + meanResponseTime1);
//        System.out.println("Mean Response Time: " + meanResponseTime2);//
//        // Your code here will run after Gatling and additional Java code
        System.out.println("All Gatling simulations and additional code have completed.");

    }
}