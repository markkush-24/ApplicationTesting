import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Testing {

    private static final Logger log = LoggerFactory.getLogger(Testing.class);
    private static long averageTimeBetweenRequest = 0;
    private static long countRequest = 0;
    private static long countResponse = 0;


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        log.info("Testing start");
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new InterruptedThread());
        }
        Thread.sleep(10000);
        executorService.shutdown();
//        executorService.awaitTermination(10, TimeUnit.SECONDS);
        log.info("Testing ends");
        log.info("countRequest " + countRequest);
        log.info("countResponse " + countResponse);
        log.info("averageTimeBetweenRequest " + averageTimeBetweenRequest);
        log.info("averageTimeBetweenRequest " + countRequest/averageTimeBetweenRequest);


    }

    static class InterruptedThread extends Thread {
        public void run() {
            synchronized (Testing.class){
                LocalDateTime dt = LocalDateTime.now();
                LocalDateTime dt1 = LocalDateTime.now();
                long output = (dt1.getNano() - dt.getNano()) / 1_000_000;
                log.info(currentThread().getName()+output);
                averageTimeBetweenRequest+=output;
                log.info(String.valueOf(averageTimeBetweenRequest));
                countRequest++;
                countResponse++;
            }




//            Instant start = Instant.now();
//            Instant end = Instant.now();
//            Duration timeElapsed = Duration.between(start, end);
//            log.info("Time taken: "+ timeElapsed.toNanos() +" nanoseconds");


//            long lStartTime = System.nanoTime();
//            long lEndTime = System.nanoTime();
//            long output = lEndTime - lStartTime;
//            log.info("Elapsed time in milliseconds: " + output);
//            log.info(String.valueOf(lStartTime));
        }
    }
}
