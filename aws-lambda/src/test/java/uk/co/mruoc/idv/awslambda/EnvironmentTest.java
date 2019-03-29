package uk.co.mruoc.idv.awslambda;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import static org.assertj.core.api.Assertions.assertThat;

public class EnvironmentTest {

    private static final String STAGE = "stage";
    private static final String REGION = "region";
    private static final String EVENT_TOPIC_ARN = "eventTopicArn";

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Before
    public void setUp() {
        environmentVariables.set("STAGE", STAGE);
        environmentVariables.set("REGION", REGION);
        environmentVariables.set("EVENT_TOPIC_ARN", EVENT_TOPIC_ARN);
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

    @Test
    public void shouldLoadEventTopicArn() {
        final Environment environment = new Environment();

        final String eventTopicArn = environment.getEventTopicArn();

        assertThat(eventTopicArn).isEqualTo(EVENT_TOPIC_ARN);
    }

}
