package cz.klimesova.payment_tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by Zdenca on 8/29/2017.
 */
public class LockUtilsTest {
    private final String LOCK_NAME = "test.lock";
    private final String lockPath = LockUtils.getLockPath(LOCK_NAME);

    @Before
    public void setUp() {
        new File(lockPath).delete();
    }

    @After
    public void tearDown() {
        new File(lockPath).delete();
    }

    @Test
    public void lock() throws Exception {
        boolean locked = LockUtils.lock(LOCK_NAME);
        assertTrue(locked);
        assertTrue(new File(lockPath).exists());
        locked = LockUtils.lock(LOCK_NAME);
        assertFalse(locked);
    }

    @Test
    public void unlock() throws Exception {
        File lock = new File(lockPath);
        lock.createNewFile();
        assertTrue(lock.exists());
        LockUtils.unlock(LOCK_NAME);
        assertFalse(lock.exists());
    }

}