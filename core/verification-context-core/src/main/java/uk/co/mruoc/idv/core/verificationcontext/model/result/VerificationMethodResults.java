package uk.co.mruoc.idv.core.verificationcontext.model.result;

import lombok.Builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@Builder
public class VerificationMethodResults implements Iterable<VerificationMethodResult> {

    private final UUID verificationContextId;

    @Builder.Default
    private final Collection<VerificationMethodResult> results = Collections.emptyList();

    @Override
    public Iterator<VerificationMethodResult> iterator() {
        return results.iterator();
    }

    public Optional<VerificationMethodResult> getResult(final String methodName) {
        return results.stream()
                .filter(result -> result.getMethodName().equals(methodName))
                .findFirst();
    }

    public UUID getVerificationContextId() {
        return verificationContextId;
    }

    public VerificationMethodResults add(final VerificationMethodResult result) {
        final Collection<VerificationMethodResult> updatedResults = new ArrayList<>(results);
        updatedResults.add(result);
        return VerificationMethodResults.builder()
                .verificationContextId(verificationContextId)
                .results(Collections.unmodifiableCollection(updatedResults))
                .build();
    }

}
