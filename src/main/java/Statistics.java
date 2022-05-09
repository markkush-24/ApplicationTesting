public class Statistics {

    private static Statistics instance;
    private static long logFIle = 0;
    private static long countReq = 0; // atomicLong, volatile

    public static synchronized Statistics getInstance() {
        if (instance == null) {
            instance = new Statistics();
        }
        return instance;
    }

    private Statistics() {
    }

    public synchronized void addInfo(long info) {
        logFIle += info;
        logRequestCount();
    }

    public long getLogFIle() {
        return logFIle;
    }

    public synchronized void logRequestCount() {
        countReq++;
    }

    public long getCountReq() {
        return countReq;
    }

    public void showLogFile(){
        System.out.println(logFIle);
    }
}
