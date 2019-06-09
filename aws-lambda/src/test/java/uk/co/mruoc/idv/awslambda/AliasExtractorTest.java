package uk.co.mruoc.idv.awslambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasExtractorTest {

    private final AliasExtractor extractor = new AliasExtractor();

    @Test
    public void shouldUseIdPathParameterToExtractIdvIdAlias() {
        final String id = UUID.randomUUID().toString();
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(buildPathParameters(id));

        final Alias alias = extractor.extractAlias(request);

        assertThat(alias).isInstanceOf(IdvIdAlias.class);
        assertThat(alias.getValue()).isEqualTo(id);
    }

    @Test
    public void shouldQueryStringParametersToExtractAlias() {
        final String aliasType = "aliasType";
        final String aliasFormat = "aliasFormat";
        final String aliasValue = "aliasValue";
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withQueryStringParameters(buildQueryStringParameters(aliasType, aliasFormat, aliasValue));

        final Alias alias = extractor.extractAlias(request);

        assertThat(alias).isInstanceOf(DefaultAlias.class);
        assertThat(alias.getTypeName()).isEqualTo(aliasType);
        assertThat(alias.getFormat()).isEqualTo(aliasFormat);
        assertThat(alias.getValue()).isEqualTo(aliasValue);
    }

    @Test
    public void shouldUseClearTextAliasFormatIfNotProvided() {
        final String aliasType = "aliasType";
        final String aliasValue = "aliasValue";
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withQueryStringParameters(buildQueryStringParameters(aliasType, aliasValue));

        final Alias alias = extractor.extractAlias(request);

        assertThat(alias.getFormat()).isEqualTo(Alias.Formats.CLEAR_TEXT);
    }

    private static Map<String, String> buildPathParameters(final String id) {
        final Map<String, String> params = new HashMap<>();
        params.put("id", id);
        return Collections.unmodifiableMap(params);
    }

    private static Map<String, String> buildQueryStringParameters(final String aliasType, final String aliasFormat, final String aliasValue) {
        final Map<String, String> params = new HashMap<>(buildQueryStringParameters(aliasType, aliasValue));
        params.put("aliasFormat", aliasFormat);
        return Collections.unmodifiableMap(params);
    }

    private static Map<String, String> buildQueryStringParameters(final String aliasType, final String aliasValue) {
        final Map<String, String> params = new HashMap<>();
        params.put("aliasType", aliasType);
        params.put("aliasValue", aliasValue);
        return Collections.unmodifiableMap(params);
    }

}
