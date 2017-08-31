package cz.klimesova.payment_tracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Zdenca on 8/29/2017.
 */
public class Loader {

    public Map<String, Integer> loadPaymentsFromFile(String paymentFilePath) {
        Map<String, Integer> paymentMap = new LinkedHashMap<>();
        File paymentFile = new File(paymentFilePath);
        try (FileReader fr = new FileReader(paymentFile);
             BufferedReader bfr = new BufferedReader(fr)) {
            String line = "";
            while ((line = bfr.readLine()) != null) {
                String[] splits = line.split("\\p{Space}");
                if (line == null) {
                    return null;
                }
                if (splits.length == 2) {
                    String currency = splits[0].toUpperCase();
                    Integer amount = Integer.valueOf(splits[1]);
                    paymentMap.put(currency, amount);
                }
            }
        } catch (IOException e) {
            System.out.println("File " + paymentFile.getAbsolutePath() + " does not exist.");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return paymentMap;
    }
}
