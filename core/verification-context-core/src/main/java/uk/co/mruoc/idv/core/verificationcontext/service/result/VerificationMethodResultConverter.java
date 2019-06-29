package uk.co.mruoc.idv.core.verificationcontext.service.result;

import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;

import java.util.Collection;
import java.util.stream.Collectors;

public class VerificationMethodResultConverter {

    public Collection<VerificationAttempt> toAttempts(final VerificationContext context, final VerificationMethodResults results) {
        return results.stream()
                .map(result -> toAttempt(context, result))
                .collect(Collectors.toList());
    }

    public VerificationAttempt toAttempt(final VerificationContext context, final VerificationMethodResult result) {
        return VerificationAttempt.builder()
                .activityType(context.getActivityType())
                .alias(context.getProvidedAlias())
                .channelId(context.getChannelId())
                .methodName(result.getMethodName())
                .timestamp(result.getTimestamp())
                .successful(result.isSuccessful())
                .build();
    }

}
