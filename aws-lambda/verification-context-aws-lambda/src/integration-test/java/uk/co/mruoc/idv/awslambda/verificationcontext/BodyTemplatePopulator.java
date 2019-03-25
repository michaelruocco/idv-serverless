package uk.co.mruoc.idv.awslambda.verificationcontext;

import org.apache.commons.lang3.StringUtils;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextResponseDocument;

public class BodyTemplatePopulator {

    public static String populate(final String template, final VerificationContextResponseDocument document) {
        final String[] placeholders = new String[] {
                "%VERIFICATION_CONTEXT_ID%",
                "%IDV_ID%",
                "%CREATED%",
                "%EXPIRY%"
        };
        final String[] values = new String[] {
                document.getId().toString(),
                document.getIdvId().toString(),
                document.getCreated().toString(),
                document.getExpiry().toString()
        };
        return StringUtils.replaceEachRepeatedly(template, placeholders, values);
    }

}
