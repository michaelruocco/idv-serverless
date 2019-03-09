package uk.co.mruoc.idv.awslambda.identity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import static org.assertj.core.api.Assertions.assertThat;

public class EnvironmentTest {

    private static final String STAGE = "dev";
    private static final String REGION = "eu-west-1";

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Before
    public void setUp() {
        environmentVariables.set("STAGE", STAGE);
        environmentVariables.set("REGION", REGION);
    }

    @Test
    public void shouldLoadStage() {
        final String stage = Environment.getStage();

        assertThat(stage).isEqualTo(STAGE);
    }

    @Test
    public void shouldLoadRegion() {
        final String region = Environment.getRegion();

        assertThat(region).isEqualTo(REGION);
    }

}
