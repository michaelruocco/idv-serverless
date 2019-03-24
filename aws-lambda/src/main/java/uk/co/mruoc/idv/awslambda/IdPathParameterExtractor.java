package uk.co.mruoc.idv.awslambda;

import java.util.Map;
import java.util.Optional;

public class IdPathParameterExtractor {

    public Optional<String> extractId(Map<String, String> pathParameters) {
        if (pathParameters == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(pathParameters.get("id"));
    }

}
