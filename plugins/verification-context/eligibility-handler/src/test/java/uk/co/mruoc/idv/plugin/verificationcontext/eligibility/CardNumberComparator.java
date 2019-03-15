package uk.co.mruoc.idv.plugin.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.model.CardNumber;

import java.util.Comparator;

public class CardNumberComparator implements Comparator<CardNumber> {

    @Override
    public int compare(final CardNumber o1, final CardNumber o2) {
        if (same(o1.getMasked(), o2.getMasked()) &&
                same(o1.getEncrypted(), o2.getEncrypted()) &&
                same(o1.getTokenized(), o2.getTokenized())) {
            return 0;
        }
        return 1;
    }

    private boolean same(final String value1, final String value2) {
        if (value1 == null && value2 == null) {
            return true;
        }
        if (value1 == null) {
            return false;
        }
        return value1.equals(value2);
    }

}
