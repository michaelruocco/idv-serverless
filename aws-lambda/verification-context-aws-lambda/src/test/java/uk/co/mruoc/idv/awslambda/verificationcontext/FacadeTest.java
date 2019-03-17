package uk.co.mruoc.idv.awslambda.verificationcontext;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextServiceRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextService;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextRequest;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FacadeTest {

    private final IdentityService identityService = mock(IdentityService.class);
    private final VerificationContextService verificationContextService = mock(VerificationContextService.class);
    private final VerificationContextRequestConverter requestConverter = mock(VerificationContextRequestConverter.class);

    private final Facade facade = Facade.builder()
            .identityService(identityService)
            .verificationContextService(verificationContextService)
            .requestConverter(requestConverter)
            .build();

    @Test
    public void shouldCreateContext() {
        final VerificationContextRequest request = buildRequest();
        final VerificationContextServiceRequest serviceRequest = toServiceRequest(request);
        final VerificationContext context = VerificationContext.builder().build();
        given(identityService.load(request.getProvidedAlias())).willReturn(serviceRequest.getIdentity());
        given(requestConverter.toServiceRequest(request, serviceRequest.getIdentity())).willReturn(serviceRequest);
        given(verificationContextService.create(serviceRequest)).willReturn(context);

        final VerificationContext createdContext = facade.create(request);

        assertThat(createdContext).isEqualTo(context);
    }

    private static VerificationContextRequest buildRequest() {
        final Alias providedAlias = new TokenizedCreditCardNumberAlias("3489347343788005");
        return VerificationContextRequest.builder()
                .channel(new DefaultChannel("DEFAULT"))
                .activity(new LoginActivity(Instant.parse("2019-03-10T12:53:57.547Z")))
                .providedAlias(providedAlias)
                .build();
    }

    private static VerificationContextServiceRequest toServiceRequest(final VerificationContextRequest clientRequest) {
        final Identity identity = Identity.withAliases(new IdvIdAlias(), clientRequest.getProvidedAlias());
        return VerificationContextServiceRequest.builder()
                .channel(clientRequest.getChannel())
                .activity(clientRequest.getActivity())
                .providedAlias(clientRequest.getProvidedAlias())
                .identity(identity)
                .build();
    }

}
