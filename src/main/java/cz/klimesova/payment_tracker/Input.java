package cz.klimesova.payment_tracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Zdenca on 8/21/2017.
 */
public class Input {
    private Scanner sc;
    private String currency;
    private Integer amount;
    private Map<String, Integer> paymentMap;
    private String paymentFilePath;

    public Input(InputStream inputStream, String paymentFilePath) {
        sc = new Scanner(inputStream);
        initPaymentFilePath(paymentFilePath);
        paymentMap = new Loader().loadPaymentsFromFile(paymentFilePath);
    }

    public void readInput() {
        System.out.println("Input currency and amount: ");
        try {
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
                splitAndPutToMap(line);
            }
        } finally {
            sc.close();
        }
    }

    private void initPaymentFilePath(String path) {
        if (path != null && !path.isEmpty()) {
            if (!new File(path).exists()) {
                try {
                    paymentFilePath = path;
                    new File(paymentFilePath).createNewFile();
                } catch (IOException e) {
                    System.out.println("Cannot initialize payment file path.");
                    e.printStackTrace();
                }
            } else {
                paymentFilePath = path;
            }
        }
    }

    private void splitAndPutToMap(String line) {
        String[] splitStrings = line.split("\\p{Space}");
        if (splitStrings[0].length() != 3) {
            System.out.println("The currency must have three letters");
        }
        if (!splitStrings[1].matches("[+-]?\\d+")) {
            throw new NumberFormatException("The amount must be a number");
        }
        putToMap(splitStrings);
        saveToFile();
    }

    private void putToMap(String[] splitStrings) {
        if (splitStrings.length == 2) {
            currency = splitStrings[0].toUpperCase();
            amount = Integer.valueOf(splitStrings[1]);
            if (currency != null && currency.length() == 3 && amount != 0) {
                if (paymentMap.containsKey(currency)) {
                    int updatedValue = paymentMap.get(currency) + amount;
                    if (updatedValue == 0) {
                        paymentMap.remove(currency);
                    } else {
                        paymentMap.put(currency, updatedValue);
                    }
                } else {
                    paymentMap.put(currency, amount);
                }
            }
        }
    }

    private void saveToFile() {
        if (LockUtils.lock("payment.lock")) {
            try (FileWriter fw = new FileWriter(new File(paymentFilePath), false)) {
                Iterator<Map.Entry<String, Integer>> it = paymentMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Integer> entries = it.next();
                    fw.write(entries.getKey() + " " + entries.getValue() + "\n");
                }
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                LockUtils.unlock("payment.lock");
            }
        }
    }

    public static void main(String[] args) {
        new Input(System.in, args[0]).readInput();
    }
}
