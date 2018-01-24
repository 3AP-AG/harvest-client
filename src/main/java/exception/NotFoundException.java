package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotFoundException extends HarvestRuntimeException {

    private static final Logger log = LoggerFactory.getLogger(NotFoundException.class);
}
