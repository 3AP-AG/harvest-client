package ch.aaap.harvestclient.core.ratelimit;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Keep track of event timestamps, can be use for rate limiting
 */
public class WindowCounter {

    private static final Logger log = LoggerFactory.getLogger(WindowCounter.class);

    /**
     * How many seconds before we delete entries
     */
    private final Duration window;

    /**
     * The first element is the oldest
     */
    private LinkedBlockingDeque<Instant> events;

    public WindowCounter(int window) {
        this.window = Duration.ofSeconds(window);
        events = new LinkedBlockingDeque<>();
    }

    public void waitUntilBelow(int totalCount) throws InterruptedException {

        if (events.size() < totalCount) {
            return;
        }
        update();
        Duration toWait = Duration.between(Instant.now().minus(window), events.getFirst());
        if (!toWait.isNegative()) {
            log.debug("Waiting {}s to issue next request, hit request limit", toWait.getSeconds());
            Thread.sleep(toWait.toMillis());
        }
    }

    /**
     * remove timestamps from the list that are older that window
     */
    private void update() {
        Instant now = Instant.now();
        Iterator<Instant> it = events.iterator();
        while (it.hasNext()) {
            Instant event = it.next();
            if (Duration.between(event, now).compareTo(window) > 0) {
                it.remove();
            } else {
                // early exit, entries are sorted
                break;
            }

        }
    }

    public void mark() {
        events.add(Instant.now());
    }

    public int getCount() {
        update();
        return events.size();
    }

}
