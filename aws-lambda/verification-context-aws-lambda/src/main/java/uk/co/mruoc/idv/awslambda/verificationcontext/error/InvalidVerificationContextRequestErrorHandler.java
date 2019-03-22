package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.awslambda.verificationcontext.VerificationContextRequestExtractor.InvalidVerificationContextRequestException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collection;

import static java.util.Collections.singleton;

public class InvalidVerificationContextRequestErrorHandler implements JsonApiErrorHandler {

    @Override
    public JsonApiErrorDocument handle(final Exception e) {
        return new JsonApiErrorDocument(new InvalidVerificationContextRequestErrorItem(e.getMessage()));
    }

    @Override
    public Collection<Class<?>> getSupportedExceptions() {
        return singleton(InvalidVerificationContextRequestException.class);
    }

}
