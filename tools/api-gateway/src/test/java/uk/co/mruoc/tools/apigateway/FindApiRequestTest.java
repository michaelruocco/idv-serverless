package uk.co.mruoc.tools.apigateway;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FindApiRequestTest {

    @Test
    public void shouldReturnRegion() {
        final String region = "region";

        final FindApiRequest request = FindApiRequest.builder()
                .region(region)
                .build();

        assertThat(request.getRegion()).isEqualTo(region);
    }

    @Test
    public void shouldReturnName() {
        final String name = "name";

        final FindApiRequest request = FindApiRequest.builder()
                .name(name)
                .build();

        assertThat(request.getName()).isEqualTo(name);
    }

    @Test
    public void shouldReturnStage() {
        final String stage = "stage";

        final FindApiRequest request = FindApiRequest.builder()
                .stage(stage)
                .build();

        assertThat(request.getStage()).isEqualTo(stage);
    }

    @Test
    public void shouldReturnNameAndStage() {
        final String name = "name";
        final String stage = "stage";

        final FindApiRequest request = FindApiRequest.builder()
                .name(name)
                .stage(stage)
                .build();

        assertThat(request.getStageAndName()).isEqualTo("stage-name");
    }

}
