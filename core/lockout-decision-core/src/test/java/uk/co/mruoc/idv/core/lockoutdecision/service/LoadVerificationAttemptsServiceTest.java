package uk.co.mruoc.idv.core.lockoutdecision.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.CreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class LoadVerificationAttemptsServiceTest {

    private final IdentityService identityService = mock(IdentityService.class);
    private final VerificationAttemptsDao dao = mock(VerificationAttemptsDao.class);

    private final LoadVerificationAttemptsService service = LoadVerificationAttemptsService.builder()
            .identityService(identityService)
            .dao(dao)
            .build();

    @Test
    public void shouldReturnLoadAttemptsUsingAliasValueFromIdvIdAlias() {
        final IdvIdAlias alias = new IdvIdAlias();
        final VerificationAttempts expectedAttempts = mock(VerificationAttempts.class);
        given(dao.loadByIdvId(alias.getValueAsUuid())).willReturn(expectedAttempts);

        final VerificationAttempts attempts = service.load(alias);

        assertThat(attempts).isEqualTo(expectedAttempts);
    }

    @Test
    public void shouldLoadIdentityFromAliasAndLoadAttemptsUsingIdentityIdvAliasValue() {
        final Identity identity = mock(Identity.class);
        final UUID idvIdValue = UUID.randomUUID();
        given(identity.getIdvId()).willReturn(idvIdValue);

        final Alias alias = new CreditCardNumberAlias(Alias.Formats.CLEAR_TEXT, "1234567890123456");
        given(identityService.load(alias)).willReturn(identity);

        final VerificationAttempts expectedAttempts = mock(VerificationAttempts.class);
        given(dao.loadByIdvId(idvIdValue)).willReturn(expectedAttempts);

        final VerificationAttempts attempts = service.load(alias);

        assertThat(attempts).isEqualTo(expectedAttempts);
    }

}
