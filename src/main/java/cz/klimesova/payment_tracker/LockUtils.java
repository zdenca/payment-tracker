package cz.klimesova.payment_tracker;

import java.io.File;
import java.io.IOException;

/**
 * Created by Zdenca on 8/21/2017.
 */
public class LockUtils {
    static String getLockPath(String lockName) {
        return System.getProperty("user.home") + System.getProperty("file.separator") + lockName;
    }

    public static boolean lock(String lockName) {
        String lockPath = getLockPath(lockName);
        File lock = new File(lockPath);
        if (lock.exists()) {
            return false;
        } else {
            try {
                lock.createNewFile();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

    public static void unlock(String lockName) {
        String lockPath = getLockPath(lockName);
        File lock = new File(lockPath);
        if (lock.exists()) {
            lock.delete();
        }
    }
}
