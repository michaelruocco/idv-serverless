package uk.co.mruoc.idv.core.service;

import java.util.UUID;

public class RandomUuidGenerator implements UuidGenerator {

    @Override
    public UUID randomUuid() {
        return UUID.randomUUID();
    }

}
