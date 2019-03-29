package uk.co.mruoc.idv.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.json.JsonConverter.JsonConversionException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class JacksonJsonConverterTest {

    private final ObjectMapper mapper = mock(ObjectMapper.class);

    private final JsonConverter converter = new JacksonJsonConverter(mapper);

    @Test
    public void shouldThrowExceptionIfMapperFailsToConvertToJson() throws JsonProcessingException {
        final Object object = new Object();
        doThrow(JsonProcessingException.class).when(mapper).writeValueAsString(object);

        final Throwable cause = catchThrowable(() -> converter.toJson(object));

        assertThat(cause).isInstanceOf(JsonConversionException.class)
                .hasCauseInstanceOf(JsonProcessingException.class);
    }

    @Test
    public void shouldThrowExceptionIfMapperFailsToConvertToObject() throws IOException {
        final Class<Object> type = Object.class;
        final String json = "json";
        doThrow(IOException.class).when(mapper).readValue(json, type);

        final Throwable cause = catchThrowable(() -> converter.toObject(json, type));

        assertThat(cause).isInstanceOf(JsonConversionException.class)
                .hasCauseInstanceOf(IOException.class);
    }

    @Test
    public void shouldConvertObjectToJson() throws JsonProcessingException {
        final Object object = new Object();
        final String expectedJson = "expectedJson";
        given(mapper.writeValueAsString(object)).willReturn(expectedJson);

        final String json = converter.toJson(object);

        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldConvertJsonToObject() throws IOException {
        final Class<Object> type = Object.class;
        final String json = "json";
        final Object expectedObject = new Object();
        given(mapper.readValue(json, type)).willReturn(expectedObject);

        final Object object = converter.toObject(json, type);

        assertThat(object).isEqualTo(expectedObject);
    }

}
