package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import org.junit.Test;
import uk.co.mruoc.idv.core.model.CardNumber;
import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.MethodSequencesRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.CardCredentialsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.MobilePinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PhysicalPinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationMethodsService;
import uk.co.mruoc.idv.plugin.uk.channel.As3Channel;
import uk.co.mruoc.idv.plugin.uk.channel.BbosChannel;
import uk.co.mruoc.idv.plugin.uk.channel.RsaChannel;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.as3.As3LoginVerificationPolicy;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.bbos.BbosLoginVerificationPolicy;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa.RsaOnlinePurchaseVerificationPolicy;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa.RsaPasscode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UkVerificationMethodsServiceTest {

    private static final int DURATION = 300000;

    private final VerificationMethodsService service = new UkVerificationMethodsService();

    @Test
    public void shouldReturnTwoMethodsForAs3Login() {
        final MethodSequencesRequest request = buildAs3LoginRequest();

        final Collection<VerificationMethodSequence> methods = service.loadMethodSequences(request);

        assertThat(methods).hasSize(2);
    }

    @Test
    public void shouldReturnPushNotificationAsFirstMethodForAs3Login() {
        final MethodSequencesRequest request = buildAs3LoginRequest();

        final List<VerificationMethodSequence> methods = new ArrayList<>(service.loadMethodSequences(request));

        assertThat(methods.get(0)).isEqualToComparingFieldByFieldRecursively(buildAs3LoginPushNotificationSequence());
    }

    @Test
    public void shouldReturnPhysicalPinsentryIdentifyAsSecondMethodForAs3Login() {
        final MethodSequencesRequest request = buildAs3LoginRequest();

        final List<VerificationMethodSequence> methods = new ArrayList<>(service.loadMethodSequences(request));


        assertThat(methods.get(1)).isEqualToComparingFieldByFieldRecursively(buildAs3LoginPhysicalPinsentrySequence());
    }

    @Test
    public void shouldReturnOneMethodForBbosLogin() {
        final MethodSequencesRequest request = buildBbosLoginRequest();

        final Collection<VerificationMethodSequence> methods = service.loadMethodSequences(request);

        assertThat(methods).hasSize(1);
    }

    @Test
    public void shouldReturnMobilePinsentryIdentityForBbosLogin() {
        final MethodSequencesRequest request = buildBbosLoginRequest();

        final List<VerificationMethodSequence> methods = new ArrayList<>(service.loadMethodSequences(request));

        assertThat(methods.get(0)).isEqualToComparingFieldByFieldRecursively(buildBbosLoginMobilePinsentrySequence());
    }

    @Test
    public void shouldReturnTwoMethodsForRsaOnlinePurchase() {
        final MethodSequencesRequest request = buildRsaOnlinePurchaseRequest();

        final Collection<VerificationMethodSequence> methods = service.loadMethodSequences(request);

        assertThat(methods).hasSize(2);
    }

    @Test
    public void shouldReturnPhysicalPinsentryRespondForRsaOnlinePurchase() {
        final MethodSequencesRequest request = buildRsaOnlinePurchaseRequest();

        final List<VerificationMethodSequence> methods = new ArrayList<>(service.loadMethodSequences(request));

        assertThat(methods.get(0)).isEqualToComparingFieldByFieldRecursively(buildRsaOnlinePurchasePhysicalPinsentrySequence());
    }

    @Test
    public void shouldReturnOtpSmsForRsaOnlinePurchase() {
        final MethodSequencesRequest request = buildRsaOnlinePurchaseRequest();

        final List<VerificationMethodSequence> methods = new ArrayList<>(service.loadMethodSequences(request));

        assertThat(methods.get(1)).isEqualToComparingFieldByFieldRecursively(buildRsaOnlinePurchaseOtpSmsSequence());
    }

    private static MethodSequencesRequest buildAs3LoginRequest() {
        return MethodSequencesRequest.builder()
                .channel(new As3Channel())
                .policy(new As3LoginVerificationPolicy())
                .build();
    }

    private static MethodSequencesRequest buildBbosLoginRequest() {
        return MethodSequencesRequest.builder()
                .channel(new BbosChannel())
                .policy(new BbosLoginVerificationPolicy())
                .build();
    }

    private static MethodSequencesRequest buildRsaOnlinePurchaseRequest() {
        return MethodSequencesRequest.builder()
                .channel(new RsaChannel())
                .policy(new RsaOnlinePurchaseVerificationPolicy())
                .build();
    }

    private static VerificationMethodSequence buildAs3LoginPushNotificationSequence() {
        return new VerificationMethodSequence(new PushNotificationVerificationMethod(DURATION));
    }

    private static VerificationMethodSequence buildAs3LoginPhysicalPinsentrySequence() {
        final CardNumber cardNumber = buildCardNumber();
        final VerificationMethod method = new PhysicalPinsentryVerificationMethod(DURATION, PinsentryFunction.IDENTIFY, Collections.singleton(cardNumber));
        return new VerificationMethodSequence(method);
    }

    private static VerificationMethodSequence buildBbosLoginMobilePinsentrySequence() {
        final VerificationMethod method = new MobilePinsentryVerificationMethod(DURATION, PinsentryFunction.IDENTIFY);
        return new VerificationMethodSequence(method);
    }

    private static VerificationMethodSequence buildRsaOnlinePurchasePhysicalPinsentrySequence() {
        final CardNumber cardNumber = buildCardNumber();
        final VerificationMethod method = new PhysicalPinsentryVerificationMethod(DURATION, PinsentryFunction.RESPOND, Collections.singleton(cardNumber));
        return new VerificationMethodSequence(method);
    }

    private static VerificationMethodSequence buildRsaOnlinePurchaseOtpSmsSequence() {
        final VerificationMethod cardCredentials = new CardCredentialsVerificationMethod(DURATION);
        final MobileNumber mobileNumber = buildMobileNumber();
        final VerificationMethod otpSms = new OtpSmsVerificationMethod(DURATION, new RsaPasscode(), Collections.singleton(mobileNumber));
        return new VerificationMethodSequence("OTP_SMS", Arrays.asList(cardCredentials, otpSms));
    }

    private static CardNumber buildCardNumber() {
        return CardNumber.builder()
                .tokenized("3213485412348005")
                .masked("************8005")
                .build();
    }

    private static MobileNumber buildMobileNumber() {
        return MobileNumber.builder()
                .id(UUID.fromString("48be7f28-37c2-42b6-956c-a32241310ee6"))
                .masked("********789")
                .build();
    }

}
