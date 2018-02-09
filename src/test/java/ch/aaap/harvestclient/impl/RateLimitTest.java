package ch.aaap.harvestclient.impl;

import java.time.Duration;
import java.util.concurrent.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import ch.aaap.harvestclient.HarvestTest;
import ch.aaap.harvestclient.core.Harvest;
import ch.aaap.harvestclient.domain.User;
import util.TestSetupUtil;

@HarvestTest
class RateLimitTest {

    private static class Task implements Runnable {

        private static final Logger log = LoggerFactory.getLogger(Task.class);
        private final int id;
        private final Harvest harvest;
        private final ConcurrentMap<Integer, Throwable> report;

        Task(int id, Harvest harvest, ConcurrentMap<Integer, Throwable> report) {
            this.id = id;
            this.harvest = harvest;
            this.report = report;
        }

        @Override
        public void run() {
            log.debug("Running task {}", id);
            try {
                User self = harvest.users().getSelf();
            } catch (Exception e) {
                report.put(id, e);
            }
        }
    }

    @Test
    void testGetLimit() {

        assertTimeoutPreemptively(Duration.ofMinutes(5), () -> {

            Harvest harvest = TestSetupUtil.getAdminAccess();

            ExecutorService executorService = Executors.newFixedThreadPool(10);
            ConcurrentMap<Integer, Throwable> report = new ConcurrentHashMap<>();

            // don't make this too high
            int iterationCount = 150;
            for (int i = 0; i < iterationCount; i++) {
                executorService.execute(new Task(i, harvest, report));
            }

            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.HOURS);

            assertThat(report.values()).isEmpty();

        });

    }
}
