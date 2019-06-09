package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.channel.model.DefaultChannel;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.DefaultActivity;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.dao.verificationcontext.FakeVerificationContextDao;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class GetVerificationContextHandlerIntegrationTest {

    private static final UUID CONTEXT_ID = UUID.randomUUID();

    private final VerificationContextDao contextDao = new FakeVerificationContextDao();
    private final GetVerificationContextService contextService = buildVerificationContextService(contextDao);

    private final GetVerificationContextHandler handler = new GetVerificationContextHandler(contextService);

    @Before
    public void setUp() {
        contextDao.save(buildContext(CONTEXT_ID));
    }

    @Test
    public void shouldLoadVerificationContext() {
        final Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("id", CONTEXT_ID.toString());
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(Collections.unmodifiableMap(pathParameters));

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        assertThat(response.getStatusCode()).isEqualTo(200);
        final String expectedBody = loadContentFromClasspath("/get-verification-context-response.json");
        assertThatJson(response.getBody()).isEqualTo(expectedBody);
    }

    private static VerificationContext buildContext(final UUID id) {
        final Instant now = Instant.now();
        final Alias providedAlias = new DefaultAlias(new DefaultAliasType("TYPE"), "FORMAT", "VALUE");
        return VerificationContext.builder()
                .id(id)
                .channel(new DefaultChannel("CHANNEL"))
                .providedAlias(providedAlias)
                .identity(Identity.withAliases(new IdvIdAlias(), providedAlias))
                .sequences(Collections.emptyList())
                .activity(new DefaultActivity("ACTIVITY", Instant.parse("2019-03-24T22:41:50.434Z")))
                .created(now)
                .expiry(now.plus(Duration.ofMinutes(5)))
                .build();
    }

    private static GetVerificationContextService buildVerificationContextService(final VerificationContextDao dao) {
        return GetVerificationContextService.builder()
                .dao(dao)
                .timeService(new DefaultTimeService())
                .build();
    }

}
