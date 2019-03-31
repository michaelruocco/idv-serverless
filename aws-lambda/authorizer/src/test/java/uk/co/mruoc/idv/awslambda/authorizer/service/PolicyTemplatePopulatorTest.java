package uk.co.mruoc.idv.awslambda.authorizer.service;

import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.authorizer.model.ApiGatewayMethodArn;
import uk.co.mruoc.idv.awslambda.authorizer.model.PolicyRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PolicyTemplatePopulatorTest {

    private final PolicyRequest request = mock(PolicyRequest.class);
    private final ApiGatewayMethodArn arn = mock(ApiGatewayMethodArn.class);

    private final PolicyTemplatePopulator populator = new PolicyTemplatePopulator();

    @Before
    public void setUp() {
        given(request.getMethodArn()).willReturn(arn);
    }

    @Test
    public void shouldReplaceRegion() {
        final String region = "region";
        given(arn.getRegion()).willReturn(region);
        final String template = ":%REGION%:";

        final String result = populator.populate(template, request);

        assertThat(result).isEqualTo(":region:");
    }

    @Test
    public void shouldReplaceAccountId() {
        final String accountId = "accountId";
        given(arn.getAccountId()).willReturn(accountId);
        final String template = ":%ACCOUNT_ID%:";

        final String result = populator.populate(template, request);

        assertThat(result).isEqualTo(":accountId:");
    }

    @Test
    public void shouldReplaceApiId() {
        final String apiId = "apiId";
        given(arn.getApiId()).willReturn(apiId);
        final String template = ":%API_ID%:";

        final String result = populator.populate(template, request);

        assertThat(result).isEqualTo(":apiId:");
    }

    @Test
    public void shouldReplaceStage() {
        final String stage = "stage";
        given(arn.getStage()).willReturn(stage);
        final String template = ":%STAGE%:";

        final String result = populator.populate(template, request);

        assertThat(result).isEqualTo(":stage:");
    }

    @Test
    public void shouldReplacePrincipalId() {
        final String principalId = "principalId";
        given(request.getPrincipalId()).willReturn(principalId);
        final String template = ":%PRINCIPAL_ID%:";

        final String result = populator.populate(template, request);

        assertThat(result).isEqualTo(":principalId:");
    }

}
