package uk.co.mruoc.idv.dao.dynamodb;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultDynamoEnvironmentTest {

    private static final String REGION = "region";
    private static final String STAGE = "stage";

    private final DynamoEnvironment environment = new DefaultDynamoEnvironment(REGION, STAGE);

    @Test
    public void shouldReturnRegion() {
        assertThat(environment.getRegion()).isEqualTo(REGION);
    }

    @Test
    public void shouldReturnStage() {
        assertThat(environment.getStage()).isEqualTo(STAGE);
    }

}
