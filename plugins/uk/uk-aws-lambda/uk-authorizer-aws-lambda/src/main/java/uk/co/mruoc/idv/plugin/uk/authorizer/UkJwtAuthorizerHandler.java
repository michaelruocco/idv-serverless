package uk.co.mruoc.idv.plugin.uk.authorizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.awslambda.authorizer.handler.JwtAuthorizerHandler;
import uk.co.mruoc.idv.awslambda.authorizer.service.ApiGatewayMethodArnParser;
import uk.co.mruoc.idv.awslambda.authorizer.service.AuthPolicyConverter;
import uk.co.mruoc.idv.json.JacksonJsonConverter;

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