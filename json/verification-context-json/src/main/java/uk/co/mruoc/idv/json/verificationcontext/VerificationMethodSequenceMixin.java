package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.mruoc.idv.core.verificationcontext.model.method.CardCredentialsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.MobilePinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PhysicalPinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;

@JsonPropertyOrder({ "name", "status", "duration", "registerAttemptStrategy", "methods" })
public interface VerificationMethodSequenceMixin {

    @JsonIgnore
    PhysicalPinsentryVerificationMethod getPhysicalPinsentry();

    @JsonIgnore
    MobilePinsentryVerificationMethod getMobilePinsentry();

    @JsonIgnore
    PushNotificationVerificationMethod getPushNotification();

    @JsonIgnore
    CardCredentialsVerificationMethod getCardCredentials();

    @JsonIgnore
    OtpSmsVerificationMethod getOneTimePasscodeSms();

}
