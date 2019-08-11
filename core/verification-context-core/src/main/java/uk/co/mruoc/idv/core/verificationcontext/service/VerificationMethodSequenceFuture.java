package uk.co.mruoc.idv.core.verificationcontext.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Getter
@Slf4j
public class VerificationMethodSequenceFuture {

    private final String name;
    private final Collection<CompletionStage<VerificationMethod>> methodStages;

    public Collection<VerificationMethod> getMethods() {
        final Collection<VerificationMethod> methods = new ArrayList<>();
        for (final CompletionStage<VerificationMethod> stage : methodStages) {
            try {
                final CompletableFuture<VerificationMethod> future = stage.toCompletableFuture();
                methods.add(future.get());
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("interrupted exception", e);
                return Collections.emptyList();
            } catch (final ExecutionException e) {
                log.error("execution exception", e);
                return Collections.emptyList();
            }
        }
        return methods;
    }

}
