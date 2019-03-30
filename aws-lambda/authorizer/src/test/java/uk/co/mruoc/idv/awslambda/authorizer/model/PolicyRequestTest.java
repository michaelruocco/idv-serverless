package uk.co.mruoc.idv.awslambda.authorizer.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PolicyRequestTest {

    @Test
    public void shouldSetPrincipalId() {
        final String principalId = "principalId";

        final PolicyRequest request = PolicyRequest.builder()
                .principalId(principalId)
                .build();

        assertThat(request.getPrincipalId()).isEqualTo(principalId);
    }

    @Test
    public void shouldSetMethodArn() {
        final ApiGatewayMethodArn methodArn = mock(ApiGatewayMethodArn.class);

        final PolicyRequest request = PolicyRequest.builder()
                .methodArn(methodArn)
                .build();

        assertThat(request.getMethodArn()).isEqualTo(methodArn);
    }

}
