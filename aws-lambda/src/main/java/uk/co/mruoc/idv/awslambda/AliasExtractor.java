package uk.co.mruoc.idv.awslambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;

import java.util.Map;
import java.util.Optional;

public class AliasExtractor {

    private final IdPathParameterExtractor idExtractor;

    public AliasExtractor() {
        this(new IdPathParameterExtractor());
    }

    public AliasExtractor(final IdPathParameterExtractor idExtractor) {
        this.idExtractor = idExtractor;
    }

    public Alias extractAlias(final APIGatewayProxyRequestEvent input) {
        final Optional<String> id = idExtractor.extractId(input.getPathParameters());
        return id.map(AliasExtractor::toIdvId)
                .orElseGet(() -> toAlias(input.getQueryStringParameters()));
    }

    private static Alias toIdvId(final String id) {
        return new IdvIdAlias(id);
    }

    private static Alias toAlias(final Map<String, String> queryStringParameters) {
        final String type = queryStringParameters.get("aliasType");
        final String format = queryStringParameters.getOrDefault("aliasFormat", Alias.Formats.CLEAR_TEXT);
        final String value = queryStringParameters.get("aliasValue");
        return new DefaultAlias(new DefaultAliasType(type), format, value);
    }

}
