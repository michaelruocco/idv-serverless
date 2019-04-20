package uk.co.mruoc.idv.plugin.uk.awslambda.authorizer;

import org.assertj.core.api.Condition;

import java.util.regex.Pattern;

public class IsValidUUIDCondition extends Condition<String> {

    private static final String UUID_REGEX = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";
    private static final Pattern UUID_PATTERN = Pattern.compile(UUID_REGEX);

    @Override
    public boolean matches(final String value) {
        return UUID_PATTERN.matcher(value).matches();
    }

}
