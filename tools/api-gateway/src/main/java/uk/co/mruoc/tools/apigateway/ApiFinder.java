package uk.co.mruoc.tools.apigateway;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.apigateway.AmazonApiGateway;
import com.amazonaws.services.apigateway.AmazonApiGatewayClientBuilder;
import com.amazonaws.services.apigateway.model.GetRestApisRequest;
import com.amazonaws.services.apigateway.model.GetRestApisResult;
import com.amazonaws.services.apigateway.model.RestApi;

import java.util.List;

public class ApiFinder {

    private final AmazonApiGateway gateway;

    public ApiFinder(final String region) {
        this(Regions.fromName(region));
    }

    public ApiFinder(final Regions region) {
        this(AmazonApiGatewayClientBuilder.standard()
                .withRegion(region)
                .build());
    }

    public ApiFinder(final AmazonApiGateway gateway) {
        this.gateway = gateway;
    }

    public String findApi(final FindApiRequest findApiRequest) {
        final GetRestApisRequest getApisRequest = new GetRestApisRequest();
        final GetRestApisResult result = gateway.getRestApis(getApisRequest);
        return extractIdForApiWithName(result, findApiRequest);
    }

    private static String extractIdForApiWithName(final GetRestApisResult result, final FindApiRequest request) {
        final String nameAndStage = request.getNameAndStage();
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
