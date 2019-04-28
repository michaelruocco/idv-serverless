package uk.co.mruoc.idv.dao.verificationcontext;

import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FakeVerificationContextDao implements VerificationContextDao {

    private final Map<UUID, VerificationContext> contexts = new HashMap<>();

    @Override
    public void save(final VerificationContext context) {
        System.out.println("saving context with id " + context.getId());
        contexts.put(context.getId(), context);
        System.out.println("keyset " + contexts.keySet());
    }

    @Override
    public Optional<VerificationContext> load(final UUID id) {
        System.out.println("loading " + id + " from " + contexts.keySet());
        return Optional.ofNullable(contexts.get(id));
    }

}
