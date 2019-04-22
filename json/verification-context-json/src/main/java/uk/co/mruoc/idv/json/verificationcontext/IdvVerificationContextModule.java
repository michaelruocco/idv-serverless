package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.core.model.CardNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.OnlinePurchaseActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.DefaultVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.MobilePinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PhysicalPinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.json.verificationcontext.activity.ActivityDeserializer;
import uk.co.mruoc.idv.json.verificationcontext.activity.LoginActivityMixin;
import uk.co.mruoc.idv.json.verificationcontext.method.CardNumberMixin;
import uk.co.mruoc.idv.json.verificationcontext.method.DefaultVerificationMethodMixin;
import uk.co.mruoc.idv.json.verificationcontext.method.MobilePinsentryVerificationMethodMixin;
import uk.co.mruoc.idv.json.verificationcontext.method.OtpSmsVerificationMethodMixin;
import uk.co.mruoc.idv.json.verificationcontext.method.PhysicalPinsentryVerificationMethodMixin;
import uk.co.mruoc.idv.json.verificationcontext.method.VerificationMethodDeserializer;
import uk.co.mruoc.idv.json.verificationcontext.activity.OnlinePurchaseActivityMixin;
import uk.co.mruoc.idv.json.verificationcontext.channel.ChannelDeserializer;
import uk.co.mruoc.idv.json.verificationcontext.result.VerificationMethodResultMixin;
import uk.co.mruoc.idv.json.verificationcontext.result.VerificationMethodResultsDeserializer;

public class IdvVerificationContextModule extends SimpleModule {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public IdvVerificationContextModule() {
        setUpActivity();
        setUpMethod();
        setUpChannel();
        setUpContext();
        setUpResult();
    }

    private void setUpActivity() {
        addDeserializer(Activity.class, new ActivityDeserializer(MAPPER));

        setMixInAnnotation(OnlinePurchaseActivity.class, OnlinePurchaseActivityMixin.class);
        setMixInAnnotation(LoginActivity.class, LoginActivityMixin.class);
    }

    private void setUpMethod() {
        addDeserializer(VerificationMethod.class, new VerificationMethodDeserializer(MAPPER));

        setMixInAnnotation(VerificationMethodSequence.class, VerificationMethodSequenceMixin.class);
        setMixInAnnotation(DefaultVerificationMethod.class, DefaultVerificationMethodMixin.class);
        setMixInAnnotation(OtpSmsVerificationMethod.class, OtpSmsVerificationMethodMixin.class);
        setMixInAnnotation(PhysicalPinsentryVerificationMethod.class, PhysicalPinsentryVerificationMethodMixin.class);
        setMixInAnnotation(MobilePinsentryVerificationMethod.class, MobilePinsentryVerificationMethodMixin.class);
        setMixInAnnotation(CardNumber.class, CardNumberMixin.class);
    }

    private void setUpChannel() {
        addDeserializer(Channel.class, new ChannelDeserializer());
    }

    private void setUpContext() {
        addDeserializer(VerificationContext.class, new VerificationContextDeserializer());

        setMixInAnnotation(VerificationContext.class, VerificationContextMixin.class);
    }

    private void setUpResult() {
        addDeserializer(VerificationMethodResults.class, new VerificationMethodResultsDeserializer());

        setMixInAnnotation(VerificationMethodResult.class, VerificationMethodResultMixin.class);
    }

}
