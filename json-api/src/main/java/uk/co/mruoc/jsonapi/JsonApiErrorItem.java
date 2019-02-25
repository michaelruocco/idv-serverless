package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // required by jackson
public class JsonApiErrorItem {

    private final UUID id = UUID.randomUUID();
    private final String code;
    private final String title;
    private final String detail;
    private final Map<String, Object> meta;

    @JsonIgnore
    private final int statusCode;

    public String getStatus() {
        return Integer.toString(statusCode);
    }

}
