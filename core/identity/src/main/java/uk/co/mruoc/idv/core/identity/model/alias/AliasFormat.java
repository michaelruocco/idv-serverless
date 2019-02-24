package uk.co.mruoc.idv.core.identity.model.alias;

public enum AliasFormat {

    PLAIN_TEXT(false),
    ENCRYPTED(true),
    TOKENIZED(true);

    private final boolean sensitive;

    AliasFormat(final boolean sensitive) {
        this.sensitive = sensitive;
    }

    public boolean isSensitive() {
        return sensitive;
    }

}
