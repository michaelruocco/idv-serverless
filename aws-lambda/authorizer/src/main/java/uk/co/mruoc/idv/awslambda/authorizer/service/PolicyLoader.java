package uk.co.mruoc.idv.awslambda.authorizer.service;

import uk.co.mruoc.idv.awslambda.authorizer.model.PolicyRequest;

public interface PolicyLoader {

    String load(final PolicyRequest request);


}
