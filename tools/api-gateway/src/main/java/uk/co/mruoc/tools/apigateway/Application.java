package uk.co.mruoc.tools.apigateway;

import com.amazonaws.regions.Regions;

public class Application {

    public static void main(String[] args) {
        final ArgumentConverter argumentConverter = new ArgumentConverter();
        final FindApiRequest request = argumentConverter.toFindApiRequest(args);
        final ApiUriFinder finder = new ApiUriFinder(Regions.fromName(request.getRegion()));

        final String uri = finder.findApiUri(request);
        System.out.println(uri);
    }

}
