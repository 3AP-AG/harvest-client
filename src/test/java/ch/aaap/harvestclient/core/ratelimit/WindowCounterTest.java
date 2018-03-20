package ch.aaap.harvestclient.core.ratelimit;

import java.time.Duration;
import java.util.concurrent.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import ch.aaap.harvestclient.HarvestTest;

@HarvestTest
class WindowCounterTest {

    private static class Task implements Runnable {

        private static final Logger log = LoggerFactory.getLogger(Task.class);
        private final int id;
        private WindowCounter counter;
        private final ConcurrentMap<Integer, Throwable> report;

        Task(int id, WindowCounter counter, ConcurrentMap<Integer, Throwable> report) {
            this.id = id;
            this.counter = counter;
            this.report = report;
        }

        @Override
        public void run() {
            log.debug("Running task {}", id);
            try {
                counter.mark();
                counter.waitUntilBelow(100);
                counter.mark();
            } catch (Exception e) {
                report.put(id, e);
            }
        }
    }

    @Test
    void testThreadSafety() {

        assertTimeoutPreemptively(Duration.ofMinutes(1), () -> {

            WindowCounter counter = new WindowCounter(1);

            ExecutorService executorService = Executors.newFixedThreadPool(10);
            ConcurrentMap<Integer, Throwable> report = new ConcurrentHashMap<>();

            // don't make this too high
            int iterationCount = 150;
            for (int i = 0; i < iterationCount; i++) {
                executorService.execute(new Task(i, counter, report));
            }

            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.HOURS);

            assertThat(report.values()).isEmpty();

        });

    }
}
