package uk.co.mruoc.jsonapi;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonApiDataItemTest {

    private static final String TYPE = "fake-type";
    private static final String ATTRIBUTES = "fake-attributes";

    private final JsonApiDataItem<Object> item = new JsonApiDataItem<>(TYPE, ATTRIBUTES);

    @Test
    public void shouldReturnType() {
        assertThat(item.getType()).isEqualTo(TYPE);
    }

    @Test
    public void shouldReturnAttributes() {
        assertThat(item.getAttributes()).isEqualTo(ATTRIBUTES);
    }

    @Test
    public void shouldHaveNoArgsConstructorRequiredByJackson() {
        assertThat(new JsonApiDataItem<>()).isNotNull();
    }

    @Test
    public void shouldPrintDetails() {
        assertThat(item.toString()).isEqualTo("JsonApiDataItem(type=fake-type, attributes=fake-attributes)");
    }

}
