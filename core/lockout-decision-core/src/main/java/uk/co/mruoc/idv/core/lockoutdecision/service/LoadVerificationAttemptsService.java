package uk.co.mruoc.idv.core.lockoutdecision.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.core.service.UuidGenerator;

import java.util.Collections;
import java.util.UUID;

@Builder
@Slf4j
public class LoadVerificationAttemptsService {

    private final IdentityService identityService;
    private final VerificationAttemptsDao dao;
    private final UuidGenerator uuidGenerator;

    public VerificationAttempts load(final Alias alias) {
        log.info("loading attempts for alias {}", alias);
        final IdvIdAlias idvIdAlias = loadIdvIdAlias(alias);
        final UUID idvId = idvIdAlias.getValueAsUuid();
        log.info("loading attempts for idv id {}", idvId);
        return dao.loadByIdvId(idvId).orElseGet(() -> createNewAttempts(idvIdAlias));
    }

    private IdvIdAlias loadIdvIdAlias(final Alias alias) {
        if (AliasType.isIdvId(alias.getTypeName())) {
            log.info("returning uuid value from idv id alias");
            return ((IdvIdAlias) alias);
        }
        final Identity identity = identityService.load(alias);
        return identity.getIdvIdAlias();
    }

    private VerificationAttempts createNewAttempts(final IdvIdAlias idvIdAlias) {
        return VerificationAttempts.builder()
                .lockoutStateId(uuidGenerator.randomUuid())
                .attempts(Collections.emptyList())
                .idvIdAlias(idvIdAlias)
                .build();
    }

}
