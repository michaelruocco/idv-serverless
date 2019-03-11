package uk.co.mruoc.idv.dao.identity;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class AliasConverter {

    private static final char DELIMITER = '|';

    private AliasConverter() {
        // utility class
    }

    public static Set<String> toStrings(final Aliases aliases) {
        return aliases.stream().map(AliasConverter::toString).collect(Collectors.toSet());
    }

    public static String toString(final Alias alias) {
        return alias.getTypeName() + DELIMITER + alias.getFormat() + DELIMITER + alias.getValue();
    }

    public static Collection<Alias> toAliases(final Collection<String> values) {
        return values.stream().map(AliasConverter::toAlias).collect(Collectors.toList());
    }

    public static Alias toAlias(final String value) {
        final String[] parts = value.split("\\" + DELIMITER);
        final String type = parts[0];
        final String format = parts[1];
        final String aliasValue = parts[2];
        final AliasType aliasType = AliasType.toAliasType(type);
        return new DefaultAlias(aliasType, format, aliasValue);
    }

}
