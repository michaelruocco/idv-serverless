package uk.co.mruoc.idv.core.identity.service;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;

public class AliasLoadFailedException extends RuntimeException {

    private final Alias alias;

    public AliasLoadFailedException(final Alias alias) {
        super(alias.toString());
        this.alias = alias;
    }

    public AliasLoadFailedException(final Alias alias, final Throwable cause) {
        super(alias.toString(), cause);
        this.alias = alias;
    }

    public Alias getAlias() {
        return alias;
    }

}
