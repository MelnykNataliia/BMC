package utils;

public class GlobalHelpers {
        public static void sleepWait(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
