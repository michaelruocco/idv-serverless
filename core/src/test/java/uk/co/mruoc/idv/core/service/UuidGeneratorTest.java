package uk.co.mruoc.idv.core.service;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UuidGeneratorTest {

    private final UuidGenerator generator = new RandomUuidGenerator();

    @Test
    public void shouldCreateRandomUuid() {
        final UUID uuid = generator.randomUuid();

        assertThat(uuid).isNotNull();
    }

}
