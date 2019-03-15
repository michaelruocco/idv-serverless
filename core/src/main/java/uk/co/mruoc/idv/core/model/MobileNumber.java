package uk.co.mruoc.idv.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Builder
@Getter
@ToString
public class MobileNumber {

    private static final int NUMBER_OF_UNMASKED_CHARS = 3;

    private final UUID id;
    private final String masked;

    public static String mask(final String number) {
        return NumberMasker.mask(number, NUMBER_OF_UNMASKED_CHARS);
    }

}