package uk.co.mruoc.idv.core.authorizer.service;

import uk.co.mruoc.idv.core.authorizer.model.PolicyRequest;

public interface PolicyLoader {

    String load(final PolicyRequest request);

}
