package uk.co.mruoc.idv.dao.verificationattempts;

import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FakeVerificationAttemptsDao implements VerificationAttemptsDao {

    private final Map<UUID, VerificationAttempts> idvIdvAttempts = new HashMap<>();
    private final Map<UUID, VerificationAttempts> contextIdAttempts = new HashMap<>();

    @Override
    public Optional<VerificationAttempts> loadByIdvId(final UUID idvId) {
        return Optional.ofNullable(idvIdvAttempts.get(idvId));
    }

    @Override
    public Optional<VerificationAttempts> loadByContextId(UUID contextId) {
        return Optional.ofNullable(contextIdAttempts.get(contextId));
    }

    @Override
    public void save(final VerificationAttempts attempts) {
        idvIdvAttempts.put(attempts.getIdvId(), attempts);
        saveByContextId(attempts);
    }

    private void saveByContextId(final VerificationAttempts attempts) {
        final Collection<UUID> contextIds = attempts.getContextIds();
        for (final UUID contextId : contextIds) {
            final VerificationAttempts idAttempts = contextIdAttempts.getOrDefault(contextId, new VerificationAttempts());
            contextIdAttempts.put(contextId, idAttempts.addAll(attempts.getByContextId(contextId)));
        }
    }

}
