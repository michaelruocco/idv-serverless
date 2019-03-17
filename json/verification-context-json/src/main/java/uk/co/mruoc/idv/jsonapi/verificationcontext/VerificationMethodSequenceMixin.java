package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.mruoc.idv.core.verificationcontext.model.method.CardCredentialsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.MobilePinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PhysicalPinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;

@JsonPropertyOrder({ "name", "duration", "sequence" })
public abstract class VerificationMethodSequenceMixin {

    @JsonIgnore
    public abstract PhysicalPinsentryVerificationMethod getPhysicalPinsentry();

    @JsonIgnore
    public abstract MobilePinsentryVerificationMethod getMobilePinsentry();

    @JsonIgnore
    public abstract PushNotificationVerificationMethod getPushNotification();

    @JsonIgnore
    public abstract CardCredentialsVerificationMethod getCardCredentials();

    @JsonIgnore
    public abstract OtpSmsVerificationMethod getOneTimePasscodeSms();

}
