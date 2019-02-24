package uk.co.mruoc.idv.core.identity.service;

public class AliasLoadFailedException extends RuntimeException {

    public AliasLoadFailedException() {
        super();
    }

    public AliasLoadFailedException(final String message) {
        super(message);
    }

    public AliasLoadFailedException(final Throwable cause) {
        super(cause);
    }

    public AliasLoadFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
