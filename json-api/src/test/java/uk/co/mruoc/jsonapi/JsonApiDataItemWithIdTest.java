package uk.co.mruoc.jsonapi;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonApiDataItemWithIdTest {

    private static final UUID ID = UUID.fromString("786fa43d-6bcd-4a0c-ab7e-21348eb77faf");
    private static final String TYPE = "fake-type";
    private static final String ATTRIBUTES = "fake-attributes";

    private final JsonApiDataItemWithId<Object> item = new JsonApiDataItemWithId<>(ID, TYPE, ATTRIBUTES);

    @Test
    public void shouldReturnId() {
        assertThat(item.getId()).isEqualTo(ID);
    }

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
        assertThat(new JsonApiDataItemWithId<>()).isNotNull();
    }

    @Test
    public void shouldPrintDetails() {
        assertThat(item.toString()).isEqualTo("JsonApiDataItemWithId(super=JsonApiDataItem" +
                "(type=fake-type, attributes=fake-attributes), id=786fa43d-6bcd-4a0c-ab7e-21348eb77faf)");
    }

}
