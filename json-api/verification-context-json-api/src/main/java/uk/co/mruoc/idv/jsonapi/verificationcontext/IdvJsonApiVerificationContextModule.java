package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.jsonapi.verificationcontext.result.JsonApiVerificationMethodResultMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.result.JsonApiVerificationMethodResultsMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.result.VerificationResultResponseDocument;
import uk.co.mruoc.idv.jsonapi.verificationcontext.result.VerificationResultResponseDocumentDeserializer;

public class IdvJsonApiVerificationContextModule extends SimpleModule {

    public IdvJsonApiVerificationContextModule() {
        addDeserializer(VerificationResultResponseDocument.class, new VerificationResultResponseDocumentDeserializer());

        setMixInAnnotation(VerificationContext.class, JsonApiVerificationContextMixin.class);
        setMixInAnnotation(VerificationMethodResult.class, JsonApiVerificationMethodResultMixin.class);
        setMixInAnnotation(VerificationMethodResults.class, JsonApiVerificationMethodResultsMixin.class);
    }

}
