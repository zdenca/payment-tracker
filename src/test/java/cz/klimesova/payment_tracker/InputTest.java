package cz.klimesova.payment_tracker;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by Zdenca on 8/29/2017.
 */
public class InputTest {
    @Test
    public void readInput() throws Exception {
        byte[] line = "usd 200\nczk 100".getBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(line);

        String path = "test.txt";
        Input input = new Input(bis, path);
        input.readInput();

        File testPaymentFile = new File(path);
        try (FileReader fr = new FileReader(testPaymentFile); BufferedReader bfr = new BufferedReader(fr)) {
            List<String> testPaymentFileLines = bfr.lines().collect(Collectors.toList());
            assertEquals(Arrays.asList("usd 200".toUpperCase(), "czk 100".toUpperCase()), testPaymentFileLines);
        } finally {
            testPaymentFile.delete();
        }
    }
}