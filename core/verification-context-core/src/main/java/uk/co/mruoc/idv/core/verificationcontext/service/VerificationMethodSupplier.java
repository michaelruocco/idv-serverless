package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import java.util.function.Supplier;

public class VerificationMethodSupplier implements Supplier<VerificationMethod> {

    private final VerificationMethodRequest request;
    private final EligibilityHandler handler;

    public VerificationMethodSupplier(final VerificationMethodRequest request, final EligibilityHandler handler) {
        this.request = request;
        this.handler = handler;
    }

    @Override
    public VerificationMethod get() {
        return handler.loadMethod(request);
    }

}
