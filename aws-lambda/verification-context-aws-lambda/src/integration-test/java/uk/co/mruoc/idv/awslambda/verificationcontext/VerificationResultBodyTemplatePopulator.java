package uk.co.mruoc.idv.awslambda.verificationcontext;

import org.apache.commons.lang3.StringUtils;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextResponseDocument;
import uk.co.mruoc.idv.jsonapi.verificationcontext.result.VerificationResultResponseDocument;

public class VerificationResultBodyTemplatePopulator {

    private static final String[] PLACEHOLDERS = new String[] {
            "%ID%"
    };

    public static String populate(final String template, final VerificationResultResponseDocument document) {
        final String[] values = new String[] {
                document.getId().toString(),
        };
        return StringUtils.replaceEachRepeatedly(template, PLACEHOLDERS, values);
    }

}
