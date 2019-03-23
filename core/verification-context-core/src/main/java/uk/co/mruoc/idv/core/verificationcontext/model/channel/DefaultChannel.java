package uk.co.mruoc.idv.core.verificationcontext.model.channel;

import lombok.ToString;

@ToString
public class
DefaultChannel implements Channel {

    private final String id;

    public DefaultChannel(final String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

}
