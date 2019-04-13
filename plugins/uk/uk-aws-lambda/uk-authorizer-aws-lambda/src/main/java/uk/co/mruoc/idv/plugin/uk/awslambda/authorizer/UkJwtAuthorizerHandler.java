package uk.co.mruoc.idv.plugin.uk.awslambda.authorizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.awslambda.authorizer.handler.JwtAuthorizerHandler;
import uk.co.mruoc.idv.core.authorizer.service.ApiGatewayMethodArnParser;
import uk.co.mruoc.idv.json.JacksonJsonConverter;
import uk.co.mruoc.idv.json.authorizer.AuthPolicyConverter;

public class UkJwtAuthorizerHandler extends JwtAuthorizerHandler {

    private static final String SECRET_KEY = System.getenv("AUTHORIZER_SECRET_KEY");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public UkJwtAuthorizerHandler() {
        super(new UkJwtTokenService(SECRET_KEY),
                new UkPolicyLoader(),
                new ApiGatewayMethodArnParser(),
                new AuthPolicyConverter(new JacksonJsonConverter(MAPPER)));
    }

}
