package uk.co.mruoc.tools.apigateway;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.apigateway.AmazonApiGateway;
import com.amazonaws.services.apigateway.AmazonApiGatewayClientBuilder;
import com.amazonaws.services.apigateway.model.GetRestApisRequest;
import com.amazonaws.services.apigateway.model.GetRestApisResult;
import com.amazonaws.services.apigateway.model.RestApi;

import java.util.List;

public class ApiUriFinder {

    private static final String URI_FORMAT = "https://%s.execute-api.%s.amazonaws.com/%s";

    private final AmazonApiGateway gateway;

    public ApiUriFinder(final Regions region) {
        this(AmazonApiGatewayClientBuilder.standard()
                .withRegion(region)
                .build());
    }

    public ApiUriFinder(final AmazonApiGateway gateway) {
        this.gateway = gateway;
    }

    public String findApiUri(final FindApiRequest request) {
        final String apiId = findApiId(request);
        final String region = request.getRegion();
        final String stage = request.getStage();
        return String.format(URI_FORMAT, apiId, region, stage);
    }

    private String findApiId(final FindApiRequest findApiRequest) {
        final GetRestApisRequest getApisRequest = new GetRestApisRequest();
        final GetRestApisResult result = gateway.getRestApis(getApisRequest);
        return extractIdForApiWithName(result, findApiRequest);
    }

    private static String extractIdForApiWithName(final GetRestApisResult result, final FindApiRequest request) {
        final String nameAndStage = request.getStageAndName();
        final List<RestApi> apis = result.getItems();
        for (RestApi api : apis) {
            if (api.getName().equals(nameAndStage)) {
                return api.getId();
            }
        }
        throw new ApiNotFoundException(nameAndStage);
    }

    public static class ApiNotFoundException extends RuntimeException {

        public ApiNotFoundException(final String name) {
            super(name);
        }

    }

}
