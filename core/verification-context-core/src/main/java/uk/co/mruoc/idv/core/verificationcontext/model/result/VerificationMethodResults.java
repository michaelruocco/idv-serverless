package uk.co.mruoc.idv.core.verificationcontext.model.result;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Builder
@Getter
@ToString
public class VerificationMethodResults implements Iterable<VerificationMethodResult> {

    private final UUID id;
    private final UUID contextId;

    @Builder.Default
    private final Collection<VerificationMethodResult> results = Collections.emptyList();

    @Override
    public Iterator<VerificationMethodResult> iterator() {
        return results.iterator();
    }

    public Stream<VerificationMethodResult> stream() {
        return results.stream();
    }

    public Optional<VerificationMethodResult> getResult(final String methodName) {
        return results.stream()
                .filter(result -> result.getMethodName().equals(methodName))
                .findFirst();
    }

    public VerificationMethodResults addAll(final VerificationMethodResults results) {
        return addAll(results.getResults());
    }

    public VerificationMethodResults add(final VerificationMethodResult result) {
        return addAll(Collections.singleton(result));
    }

    public int size() {
        return results.size();
    }

    public boolean isEmpty() {
        return results.isEmpty();
    }

    private VerificationMethodResults addAll(final Collection<VerificationMethodResult> resultsToAdd) {
        final Collection<VerificationMethodResult> updatedResults = new ArrayList<>(results);
        updatedResults.addAll(resultsToAdd);
        return VerificationMethodResults.builder()
                .id(id)
                .contextId(contextId)
                .results(Collections.unmodifiableCollection(updatedResults))
                .build();
    }

}
