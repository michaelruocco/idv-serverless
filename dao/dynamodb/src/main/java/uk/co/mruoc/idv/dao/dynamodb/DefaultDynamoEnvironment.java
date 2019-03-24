package uk.co.mruoc.idv.dao.dynamodb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DefaultDynamoEnvironment implements DynamoEnvironment {

    private final String region;
    private final String stage;

}
