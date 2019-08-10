package uk.co.mruoc.idv.core.lockoutstate;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutPolicy;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutstate.service.LoadLockoutStateService;
import uk.co.mruoc.idv.core.lockoutstate.service.LockoutPoliciesService;
import uk.co.mruoc.idv.core.lockoutstate.service.LockoutStateCalculationService;
import uk.co.mruoc.idv.core.lockoutstate.service.VerificationAttemptConverter;
import uk.co.mruoc.idv.core.verificationattempts.model.RegisterAttemptRequest;
import uk.co.mruoc.idv.core.verificationattempts.model.RegisterAttemptsRequest;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;
import uk.co.mruoc.idv.core.verificationattempts.service.VerificationAttemptsService;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService;


@Slf4j
@Builder
public class UpdateLockoutStateService {

    private final VerificationAttemptsService attemptsService;
    private final LoadLockoutStateService loadLockoutStateService;
    private final LockoutStateCalculationService calculationService;
    private final VerificationAttemptConverter attemptConverter;
    private final LockoutPoliciesService policiesService;
    private final GetVerificationContextService getContextService;

    public LockoutState register(final RegisterAttemptsRequest request) {
        LockoutState state = null;
        for (final RegisterAttemptRequest attemptRequest : request.getAttempts()) {
            state = register(attemptRequest);
        }
        return state;
    }

    public LockoutState register(final RegisterAttemptRequest request) {
        log.info("registering attempt request {}", request);
        final VerificationAttempt attempt = toAttempt(request);
        log.info("attempt request converted to attempt {}", attempt);
        final LockoutStateRequest stateRequest = attemptConverter.toLockoutStateRequest(attempt);
        final LockoutPolicy policy = policiesService.getPolicy(stateRequest);
        final Alias alias = attempt.getAlias();
        final VerificationAttempts attempts = attemptsService.load(alias);

        final LockoutState state = calculationService.calculateLockoutState(alias, policy, attempts);
        if (state.isLocked()) {
            log.info("not registering attempt as lockout state is already locked");
            return state;
        }

        if (attempt.isSuccessful()) {
            return reset(alias, policy, attempts);
        }

        return update(policy, attempts, attempt);
    }

    private VerificationAttempt toAttempt(final RegisterAttemptRequest request) {
        final VerificationContext context = getContextService.load(request.getContextId());
        //TODO add logic here to determine whether attempt should be registered or not
        return VerificationAttempt.builder()
                .channelId(context.getChannelId())
                .activityType(context.getActivityType())
                .alias(context.getProvidedAlias())
                .methodName(request.getMethodName())
                .timestamp(request.getTimestamp())
                .verificationId(request.getVerificationId())
                .successful(request.isSuccessful())
                .build();
    }

    public LockoutState reset(final LockoutStateRequest request) {
        log.info("resetting lockout state for request {}", request);
        final LockoutPolicy policy = policiesService.getPolicy(request);
        final VerificationAttempts attempts = attemptsService.load(request.getAlias());
        return reset(request.getAlias(), policy, attempts);
    }

    private LockoutState reset(final Alias alias, final LockoutPolicy policy, final VerificationAttempts attempts) {
        log.info("resetting verification attempts {} with alias {}", attempts, alias);
        final VerificationAttempts resetAttempts = policy.reset(alias, attempts);
        return update(alias, policy, resetAttempts);
    }

    private LockoutState update(final LockoutPolicy policy, final VerificationAttempts attempts, final VerificationAttempt attempt) {
        log.info("updating verification attempts {} with attempt {}", attempts, attempt);
        final VerificationAttempts updatedAttempts = attempts.add(attempt);
        return update(attempt.getAlias(), policy, updatedAttempts);
    }

    private LockoutState update(final Alias alias, final LockoutPolicy policy, final VerificationAttempts attempts) {
        attemptsService.update(attempts);
        final LockoutState state = calculationService.calculateLockoutState(alias, policy, attempts);
        log.info("calculated lockout state {}", state);
        return state;
    }

}
