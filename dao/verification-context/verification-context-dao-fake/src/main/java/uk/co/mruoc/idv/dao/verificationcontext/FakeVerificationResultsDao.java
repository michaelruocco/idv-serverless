package uk.co.mruoc.idv.dao.verificationcontext;

import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationResultsDao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FakeVerificationResultsDao implements VerificationResultsDao {

    private final Map<UUID, VerificationMethodResults> resultsMap = new HashMap<>();

    @Override
    public Optional<VerificationMethodResults> load(final UUID contextId) {
        return Optional.ofNullable(resultsMap.get(contextId));
    }

    @Override
    public void save(final VerificationMethodResults results) {
        add(results);
    }

    private void add(final VerificationMethodResults results) {
        resultsMap.put(results.getContextId(), results);
    }

}
