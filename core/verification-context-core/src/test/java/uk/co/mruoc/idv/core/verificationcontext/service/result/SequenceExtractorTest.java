package uk.co.mruoc.idv.core.verificationcontext.service.result;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.service.result.SequenceExtractor.SequenceNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class SequenceExtractorTest {

    private static final String SEQUENCE_NAME = "sequenceName";

    private final VerificationContext context = mock(VerificationContext.class);

    private final SequenceExtractor extractor = new SequenceExtractor();

    @Test
    public void shouldThrowExceptionIfContextDoesNotContainSequence() {
        given(context.getSequence(SEQUENCE_NAME)).willReturn(Optional.empty());

        final Throwable cause = catchThrowable(() -> extractor.extractSequence(context, SEQUENCE_NAME));

        assertThat(cause).isInstanceOf(SequenceNotFoundException.class);
    }

    @Test
    public void shouldExtractSequenceByName() {
        final VerificationMethodSequence expectedSequence = mock(VerificationMethodSequence.class);
        given(context.getSequence(SEQUENCE_NAME)).willReturn(Optional.of(expectedSequence));

        final VerificationMethodSequence sequence = extractor.extractSequence(context, SEQUENCE_NAME);

        assertThat(sequence).isEqualTo(expectedSequence);
    }

    @Test
    public void extractSequenceByResultSequenceName() {
        final VerificationMethodResult result = mock(VerificationMethodResult.class);
        given(result.getSequenceName()).willReturn(SEQUENCE_NAME);
        final VerificationMethodSequence expectedSequence = mock(VerificationMethodSequence.class);
        given(context.getSequence(SEQUENCE_NAME)).willReturn(Optional.of(expectedSequence));

        final VerificationMethodSequence sequence = extractor.extractSequence(context, result);

        assertThat(sequence).isEqualTo(expectedSequence);
    }

}