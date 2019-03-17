package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(force = true) // required by jackson
@JsonPropertyOrder({ "status", "title", "detail", "meta" })
public class JsonApiErrorItem {

    private final String status;
    private final String title;
    private final String detail;
    private final Map<String, Object> meta;

    @JsonIgnore
    private final int statusCode;

    @Builder
    public JsonApiErrorItem(final String title, final String detail, final Map<String, Object> meta, final int statusCode) {
        this.status = Integer.toString(statusCode);
        this.title = title;
        this.detail = detail;
        this.meta = meta;
        this.statusCode = statusCode;
    }

}
