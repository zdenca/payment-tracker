package cz.klimesova.payment_tracker;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Zdenca on 8/29/2017.
 */
public class LoaderTest {

    public static final String PAYMENT_FILE_PATH = "test.txt";

    @Before
    public void setUp() {
        new File(PAYMENT_FILE_PATH).delete();
    }

    @Test
    public void loadPaymentsFromFileEmptyFile() throws Exception {
        Map<String, Integer> paymentsFromFile = new Loader().loadPaymentsFromFile(PAYMENT_FILE_PATH);
        assertTrue(paymentsFromFile.isEmpty());
    }

    @Test
    public void loadPaymentsFromFile() throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(PAYMENT_FILE_PATH)));
        writer.write("USD 100");
        writer.newLine();
        writer.write("CZK 200");
        writer.flush();
        writer.close();

        Map<String, Integer> paymentsFromFile = new Loader().loadPaymentsFromFile(PAYMENT_FILE_PATH);
        assertEquals(new LinkedHashMap<String, Integer>(){{
            put("USD", 100);
            put("CZK", 200);
        }}, paymentsFromFile);
    }

    @Test()
    public void loadPaymentsFromFileBrokenFileSkipLine() throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(PAYMENT_FILE_PATH)));
        writer.write("USD100");
        writer.flush();
        writer.close();
        Map<String, Integer> paymentsFromFile = new Loader().loadPaymentsFromFile(PAYMENT_FILE_PATH);
        assertTrue(paymentsFromFile.isEmpty());
    }

    @Test()
    public void loadPaymentsFromFileBrokenFileFailed() throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(PAYMENT_FILE_PATH)));
        writer.write("100 USD");
        writer.flush();
        writer.close();
        Map<String, Integer> paymentsFromFile = new Loader().loadPaymentsFromFile(PAYMENT_FILE_PATH);
        assertTrue(paymentsFromFile.isEmpty());
    }



}