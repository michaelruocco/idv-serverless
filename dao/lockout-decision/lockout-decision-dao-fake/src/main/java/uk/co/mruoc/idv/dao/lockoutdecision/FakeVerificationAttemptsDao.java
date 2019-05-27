package uk.co.mruoc.idv.dao.lockoutdecision;

import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FakeVerificationAttemptsDao implements VerificationAttemptsDao {

    private final Map<UUID, VerificationAttempts> attemptsMap = new HashMap<>();

    @Override
    public Optional<VerificationAttempts> loadByIdvId(final UUID idvId) {
        return Optional.ofNullable(attemptsMap.get(idvId));
    }

    @Override
    public void save(final VerificationAttempts attempts) {
        attemptsMap.put(attempts.getIdvId(), attempts);
    }

}
