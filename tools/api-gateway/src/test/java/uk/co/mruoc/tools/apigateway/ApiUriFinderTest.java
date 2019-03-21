package uk.co.mruoc.tools.apigateway;

import com.amazonaws.services.apigateway.AmazonApiGateway;
import com.amazonaws.services.apigateway.model.GetRestApisRequest;
import com.amazonaws.services.apigateway.model.GetRestApisResult;
import com.amazonaws.services.apigateway.model.RestApi;
import org.junit.Test;
import uk.co.mruoc.tools.apigateway.ApiUriFinder.ApiNotFoundException;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ApiUriFinderTest {

    private final AmazonApiGateway client = mock(AmazonApiGateway.class);

    private final ApiUriFinder finder = new ApiUriFinder(client);

    @Test
    public void shouldThrowApiNotFoundExceptionIfApiNotFound() {
        final GetRestApisResult result = mock(GetRestApisResult.class);
        given(result.getItems()).willReturn(Collections.emptyList());
        given(client.getRestApis(any(GetRestApisRequest.class))).willReturn(result);
        final FindApiRequest findRequest = FindApiRequest.builder()
                .name("name")
                .stage("stage")
                .build();

        final Throwable cause = catchThrowable(() -> finder.findApiUri(findRequest));

        assertThat(cause).isInstanceOf(ApiNotFoundException.class)
                .hasMessage("stage-name");
    }

    @Test
    public void shouldReturnApiIdIfFound() {
        final GetRestApisResult result = mock(GetRestApisResult.class);
        final RestApi restApi1 = mock(RestApi.class);
        given(restApi1.getName()).willReturn("any-name");
        final RestApi restApi2 = mock(RestApi.class);
        given(restApi2.getId()).willReturn("id");
        given(restApi2.getName()).willReturn("stage-name");
        given(result.getItems()).willReturn(Arrays.asList(restApi1, restApi2));
        given(client.getRestApis(any(GetRestApisRequest.class))).willReturn(result);
        final FindApiRequest findRequest = FindApiRequest.builder()
                .name("name")
                .stage("stage")
                .region("region")
                .build();

        final String uri = finder.findApiUri(findRequest);

        final String expectedUri = "https://id.execute-api.region.amazonaws.com/stage";
        assertThat(uri).isEqualTo(expectedUri);
    }

}
