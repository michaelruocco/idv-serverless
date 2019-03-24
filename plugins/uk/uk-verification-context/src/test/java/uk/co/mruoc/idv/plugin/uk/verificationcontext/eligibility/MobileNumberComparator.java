package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.model.MobileNumber;

import java.util.Comparator;

public class MobileNumberComparator implements Comparator<MobileNumber> {

    @Override
    public int compare(final MobileNumber o1, final MobileNumber o2) {
        if (same(o1.getMasked(), o2.getMasked()) &&
                same(o1.getId(), o2.getId())) {
            return 0;
        }
        return 1;
    }

    private boolean same(final Object value1, final Object value2) {
        if (value1 == null && value2 == null) {
            return true;
        }
        if (value1 == null) {
            return false;
        }
        return value1.equals(value2);
    }

}
