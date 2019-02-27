package uk.co.mruoc.idv.core.identity.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;

import static org.assertj.core.api.Assertions.assertThat;

public class IdvIdGeneratorTest {

    private final IdvIdGenerator generator = new IdvIdGenerator();

    @Test
    public void shouldGenerateIdvIdWithRandomUuidValue() {
        final IdvIdAlias idvId = generator.generate();

        assertThat(idvId.getValueAsUuid()).isNotNull();
    }

}
