package ch.aaap.harvestclient;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Junit5Extension implements BeforeAllCallback, BeforeEachCallback, TestExecutionExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(Junit5Extension.class);

    @Override
    public void beforeAll(ExtensionContext context) {

        // uncomment this line before copy pasting output for Harvest Bug Report
        // TestSetupUtil.prepareForHarvestBugReport();

        // create some spacing for logs
        log.debug("\n\n");
        log.debug("beforeAll for class [{}]", context.getDisplayName());
    }

    @Override
    public void beforeEach(ExtensionContext context) {

        String testClassName = context.getRequiredTestClass().getName();

        log.debug("\n");
        log.debug("beforeEach for [{}.{}]", testClassName, context.getDisplayName());
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        log.error("Exception thrown in Test " + context.getDisplayName(), throwable);
        throw throwable;
    }
}
