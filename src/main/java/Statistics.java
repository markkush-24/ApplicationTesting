public class Statistics {

    private static Statistics instance;
    private static long logFIle = 0;

    public static synchronized Statistics getInstance() {
        if (instance == null) {
            instance = new Statistics();
        }
        return instance;
    }

    private Statistics() {
    }

    public void addInfo(long info) {
        logFIle += info;
    }

    public void showLogFile(){
        System.out.println(logFIle);
    }
}
