package uk.co.mruoc.idv.jsonapi.verificationcontext;

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
import uk.co.mruoc.idv.jsonapi.verificationcontext.activity.ActivityDeserializer;
import uk.co.mruoc.idv.jsonapi.verificationcontext.activity.LoginActivityMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.activity.OnlinePurchaseActivityMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.channel.ChannelDeserializer;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.CardNumberMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.DefaultVerificationMethodMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.MobilePinsentryVerificationMethodMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.OtpSmsVerificationMethodMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.PhysicalPinsentryVerificationMethodMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.VerificationMethodDeserializer;

public class IdvVerificationContextModule extends SimpleModule {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public IdvVerificationContextModule() {
        setUpActivity();
        setUpMethod();
        setUpChannel();
        setUpContext();
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

}
