package ch.aaap.harvestclient;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Junit5Extension implements BeforeAllCallback, BeforeEachCallback {

    private static final Logger log = LoggerFactory.getLogger(Junit5Extension.class);

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        log.debug("beforeAll for class {}", context.getDisplayName());
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        String testClassName = context.getRequiredTestClass().getName();

        log.debug("beforeEach for {}.{}", testClassName, context.getDisplayName());
    }
}
