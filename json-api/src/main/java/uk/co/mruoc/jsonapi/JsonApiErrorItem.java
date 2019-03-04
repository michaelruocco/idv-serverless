package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // required by jackson
@JsonPropertyOrder({ "title", "detail", "meta" })
public class JsonApiErrorItem {

    private final String title;
    private final String detail;
    private final Map<String, Object> meta;

    @JsonIgnore
    private final int statusCode;

}
