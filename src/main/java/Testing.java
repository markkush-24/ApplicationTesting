
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class Testing {

    private static final Logger log = LoggerFactory.getLogger(Testing.class);

    public static void main(String[] args) throws InterruptedException {
        int countOfThreads = Integer.valueOf(args[0]);
        int totalTime = Integer.valueOf(args[1]);
        log.info("Testing start");
        long start = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < countOfThreads; i++) {
            threads.add(new Client());
        }
        for (Thread th : threads) {
            th.start();
        }
        for (Thread th : threads) {
            th.join();
        }

        log.info("Testing ends");
        log.info("Total time = " + (System.currentTimeMillis() - start));
        log.info("count req = " + Statistics.getInstance().getCountReq());
        log.info("middle time = " + Statistics.getInstance().getLogFIle() / Statistics.getInstance().getCountReq());
        //log.info("averageTimeBetweenRequest " + countRequest / averageTimeBetweenRequest);


    }

    public static class Client extends Thread {

        private final RestTemplate restTemplate;

        public Client() {
            this.restTemplate = new RestTemplate();
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            String fooResourceUrl = "http://localhost:8118/foo";
            ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                long time = System.currentTimeMillis() - start;
                Statistics.getInstance().addInfo(time);
            }
        }
    }
}
