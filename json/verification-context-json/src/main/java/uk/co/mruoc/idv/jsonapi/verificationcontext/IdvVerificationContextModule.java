package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.core.model.CardNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.DefaultActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.OnlinePurchaseActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.method.CardCredentialsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.DefaultVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.MobilePinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PhysicalPinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.jsonapi.verificationcontext.activity.ActivityDeserializer;
import uk.co.mruoc.idv.jsonapi.verificationcontext.activity.LoginActivityMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.activity.OnlinePurchaseActivityMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.CardCredentialsVerificationMethodDeserializer;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.CardNumberMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.DefaultVerificationMethodDeserializer;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.DefaultVerificationMethodMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.MobilePinsentryVerificationMethodDeserializer;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.MobilePinsentryVerificationMethodMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.OtpSmsVerificationMethodDeserializer;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.OtpSmsVerificationMethodMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.PhysicalPinsentryVerificationMethodDeserializer;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.PhysicalPinsentryVerificationMethodMixin;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.PushNotificationVerificationMethodDeserializer;

public class IdvVerificationContextModule extends SimpleModule {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public IdvVerificationContextModule() {
        setUpActivity();
        setUpMethod();

        addDeserializer(VerificationContext.class, new VerificationContextDeserializer());
    }

    private void setUpActivity() {
        addDeserializer(Activity.class, new ActivityDeserializer(MAPPER));

        setMixInAnnotation(OnlinePurchaseActivity.class, OnlinePurchaseActivityMixin.class);
        setMixInAnnotation(LoginActivity.class, LoginActivityMixin.class);
    }

    private void setUpMethod() {
        addDeserializer(CardCredentialsVerificationMethod.class, new CardCredentialsVerificationMethodDeserializer());
        addDeserializer(PushNotificationVerificationMethod.class, new PushNotificationVerificationMethodDeserializer());
        addDeserializer(OtpSmsVerificationMethod.class, new OtpSmsVerificationMethodDeserializer());
        addDeserializer(PhysicalPinsentryVerificationMethod.class, new PhysicalPinsentryVerificationMethodDeserializer());
        addDeserializer(MobilePinsentryVerificationMethod.class, new MobilePinsentryVerificationMethodDeserializer());
        addDeserializer(DefaultVerificationMethod.class, new DefaultVerificationMethodDeserializer(MAPPER));

        setMixInAnnotation(VerificationMethodSequence.class, VerificationMethodSequenceMixin.class);
        setMixInAnnotation(DefaultVerificationMethod.class, DefaultVerificationMethodMixin.class);
        setMixInAnnotation(OtpSmsVerificationMethod.class, OtpSmsVerificationMethodMixin.class);
        setMixInAnnotation(PhysicalPinsentryVerificationMethod.class, PhysicalPinsentryVerificationMethodMixin.class);
        setMixInAnnotation(MobilePinsentryVerificationMethod.class, MobilePinsentryVerificationMethodMixin.class);
        setMixInAnnotation(CardNumber.class, CardNumberMixin.class);
    }

}
