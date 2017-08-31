package cz.klimesova.payment_tracker;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by Zdenca on 8/21/2017.
 */
public class Output {
    private String paymentFilePath;

    public Output(String path) {
        paymentFilePath = path;
    }

    private String mapToString(Map<String, Integer> map) {
        return map.entrySet().stream().map(entry -> entry.getKey() + " "
                + entry.getValue()).collect(Collectors.joining("\n"));
    }

    private void displayOverview() {
        while (true) {
            while (true) {
                if (LockUtils.lock("payment.lock")) {
                    System.out.println(mapToString(new Loader().loadPaymentsFromFile(paymentFilePath)));
                    LockUtils.unlock("payment.lock");
                    break;
                } else {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Output(args[0]).displayOverview();
    }
}
