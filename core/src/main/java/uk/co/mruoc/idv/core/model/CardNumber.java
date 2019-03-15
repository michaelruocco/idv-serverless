package uk.co.mruoc.idv.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CardNumber {

    private static final int NUMBER_OF_UNMASKED_CHARS = 4;

    private final String masked;
    private final String tokenized;
    private final String encrypted;

    public CardNumber filterSensitiveValues() {
        return CardNumber.builder()
                .masked(masked)
                .build();
    }

    public static String mask(final String number) {
        return NumberMasker.mask(number, NUMBER_OF_UNMASKED_CHARS);
    }

}