package uk.co.mruoc.tools.apigateway;

import org.apache.commons.cli.ParseException;

public class Application {

    public static void main(String[] args) throws ParseException {
        final ArgumentConverter argumentConverter = new ArgumentConverter();
        final FindApiRequest request = argumentConverter.toFindApiRequest(args);
        final ApiFinder finder = new ApiFinder(request.getRegion());

        final String apiId = finder.findApi(request);
        System.out.println(apiId);
    }

}
