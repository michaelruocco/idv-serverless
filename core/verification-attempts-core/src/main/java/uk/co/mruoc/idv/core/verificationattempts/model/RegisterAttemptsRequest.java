package uk.co.mruoc.idv.core.verificationattempts.model;

import java.util.Collection;

public interface RegisterAttemptsRequest extends Iterable<RegisterAttemptRequest> {

    Collection<RegisterAttemptRequest> getAttempts();

}
