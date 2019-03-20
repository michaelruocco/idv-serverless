package uk.co.mruoc.tools.apigateway;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindApiRequest {

    private final String region;
    private final String name;
    private final String stage;

    public String getNameAndStage() {
        return String.format("%s-%s", name, stage);
    }

}
