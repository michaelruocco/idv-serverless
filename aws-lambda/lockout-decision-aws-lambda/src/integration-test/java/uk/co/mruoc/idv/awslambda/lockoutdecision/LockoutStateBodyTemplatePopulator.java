package uk.co.mruoc.idv.awslambda.lockoutdecision;

import org.apache.commons.lang3.StringUtils;
import uk.co.mruoc.idv.jsonapi.lockoutdecision.LockoutStateResponseDocument;

public class LockoutStateBodyTemplatePopulator {

    private static final String[] PLACEHOLDERS = new String[] {
            "%VERIFICATION_CONTEXT_ID%",
            "%IDV_ID%",
            "%CREATED%",
            "%EXPIRY%"
    };

    public static String populate(final String template, final LockoutStateResponseDocument document) {
        final String[] values = new String[] {
                document.getId().toString(),
                document.getIdvId().toString()
        };
        return StringUtils.replaceEachRepeatedly(template, PLACEHOLDERS, values);
    }

}
