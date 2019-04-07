package uk.co.mruoc.idv.awslambda.authorizer.service;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(final Throwable cause) {
        super(cause);
    }

}
