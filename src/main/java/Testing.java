
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
        log.info("averageTimeBetweenRequest " + countRequest / averageTimeBetweenRequest);


    }

    static class InterruptedThread extends Thread {
        public void run() {
            synchronized (Testing.class) {
                LocalDateTime dt = LocalDateTime.now();
                LocalDateTime dt1 = LocalDateTime.now();
                long output = (dt1.getNano() - dt.getNano()) / 1_000_000;
                log.info(currentThread().getName() + output);
                averageTimeBetweenRequest += output;
                log.info(String.valueOf(averageTimeBetweenRequest));
                countRequest++;
                countResponse++;
            }
        }
    }

    public class Client implements Runnable {

        private final RestTemplate restTemplate;

        public Client(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            String fooResourceUrl = "http://localhost:8118/foo";
            ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
//                log.info(String.valueOf(System.currentTimeMillis() - start));
                Statistics.getInstance().addInfo(System.currentTimeMillis() - start);
            }
        }
    }
}
