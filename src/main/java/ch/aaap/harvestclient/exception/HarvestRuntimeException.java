package ch.aaap.harvestclient.exception;

public class HarvestRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1859001685154776836L;

    public HarvestRuntimeException() {
    }

    public HarvestRuntimeException(String message) {
        super(message);
    }

    public HarvestRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public HarvestRuntimeException(Throwable cause) {
        super(cause);
    }

}
