package uk.co.mruoc.idv.core.model;

import org.apache.commons.lang3.StringUtils;

public class NumberMasker {

    private NumberMasker() {
        // utility class
    }
    
    public static String mask(final String value, int numberOfUnmaskedCharacters) {
        int maskLength = value.length() - numberOfUnmaskedCharacters;
        if (maskLength <= 0) {
            return value;
        }
        return StringUtils.repeat("*", maskLength) + value.substring(maskLength, value.length());
    }

}