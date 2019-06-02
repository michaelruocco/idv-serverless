package uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa;

import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultLockoutPolicy;
import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultTimeBasedIntervals;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateCalculator;
import uk.co.mruoc.idv.core.lockoutdecision.model.TimeBasedInterval;
import uk.co.mruoc.idv.core.lockoutdecision.model.TimeBasedIntervals;
import uk.co.mruoc.idv.core.lockoutdecision.model.TimeBasedLockoutStateCalculator;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class As3TimeBasedLockoutPolicy extends DefaultLockoutPolicy {

    private static final String ALL = "ALL";

    public As3TimeBasedLockoutPolicy() {
        super(buildStateCalculator(),
                buildAliasTypes(),
                buildActivities(),
                buildMethods());
    }

    private static LockoutStateCalculator buildStateCalculator() {
        final TimeBasedIntervals intervals = new DefaultTimeBasedIntervals(Arrays.asList(
                new TimeBasedInterval(3, Duration.ofMinutes(15)),
                new TimeBasedInterval(6, Duration.ofHours(1)),
                new TimeBasedInterval(9, Duration.ofHours(4)),
                new TimeBasedInterval(12, Duration.ofHours(24))
        ));
        return new TimeBasedLockoutStateCalculator(intervals);
    }

    private static Collection<String> buildAliasTypes() {
        return Collections.singleton(ALL);
    }

    private static Collection<String> buildActivities() {
        return Collections.singleton(ALL);
    }

    private static Collection<String> buildMethods() {
        return Collections.singleton(ALL);
    }

}
