package uk.co.mruoc.idv.core.authorizer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import uk.co.mruoc.idv.core.authorizer.model.ApiGatewayMethodArn;
import uk.co.mruoc.idv.core.authorizer.model.PolicyRequest;

@Slf4j
public class PolicyTemplatePopulator {

    private static final String[] PLACEHOLDERS = new String[]{
            "%REGION%",
            "%ACCOUNT_ID%",
            "%API_ID%",
            "%STAGE%",
            "%PRINCIPAL_ID%"};

    public String populate(final String template, final PolicyRequest request) {
        final ApiGatewayMethodArn arn = request.getMethodArn();
        final String[] values = new String[]{
                arn.getRegion(),
                arn.getAccountId(),
                arn.getApiId(),
                arn.getStage(),
                request.getPrincipalId()
        };
        log.debug("populating template {} replacing placholders {} with values {}", template, PLACEHOLDERS, values);
        return StringUtils.replaceEach(template, PLACEHOLDERS, values);
    }

}
