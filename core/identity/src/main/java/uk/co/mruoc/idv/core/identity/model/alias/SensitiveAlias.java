package uk.co.mruoc.idv.core.identity.model.alias;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class SensitiveAlias extends AbstractAlias {

    public SensitiveAlias(final AliasType type, final AliasFormat format, final String value) {
        super(type, format, value);
        validateFormat(format);
    }

    @Override
    public boolean isSensitive() {
        return true;
    }

    private static void validateFormat(final AliasFormat format) {
        if (!format.isSensitive()) {
            throw new MustHaveSensitiveAliasFormatException(format);
        }
    }

    public static class MustHaveSensitiveAliasFormatException extends RuntimeException {

        public MustHaveSensitiveAliasFormatException(final AliasFormat format) {
            super(format.toString());
        }

    }

}
