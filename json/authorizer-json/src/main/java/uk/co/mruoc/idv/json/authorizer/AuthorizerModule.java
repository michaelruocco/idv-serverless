package uk.co.mruoc.idv.json.authorizer;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;
import uk.co.mruoc.idv.core.authorizer.model.TokenResponse;

public class AuthorizerModule extends SimpleModule {

    public AuthorizerModule() {
        setMixInAnnotation(GenerateTokenRequest.class, GenerateTokenRequestMixin.class);

        addDeserializer(GenerateTokenRequest.class, new TokenRequestDeserializer());
        addDeserializer(TokenResponse.class, new TokenResponseDeserializer());
    }

}
