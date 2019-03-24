package uk.co.mruoc.idv.awslambda;

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
        final Environment environment = new Environment();

        final String stage = environment.getStage();

        assertThat(stage).isEqualTo(STAGE);
    }

    @Test
    public void shouldLoadRegion() {
        final Environment environment = new Environment();

        final String region = environment.getRegion();

        assertThat(region).isEqualTo(REGION);
    }

}
