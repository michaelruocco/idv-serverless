package uk.co.mruoc.idv.core.identity.service;

import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

public interface AliasLoaderService {

    Aliases loadAliases(final AliasLoaderRequest request);

    class AliasTypeNotSupportedException extends RuntimeException {

        private static final String MESSAGE_FORMAT = "alias type %s is not supported for channel %s";

        private final String aliasTypeName;
        private final String channelId;

        public AliasTypeNotSupportedException(final String aliasTypeName, final String channelId) {
            super(buildMessage(aliasTypeName, channelId));
            this.aliasTypeName = aliasTypeName;
            this.channelId = channelId;
        }

        private static String buildMessage(final String aliasTypeName, final String channelId) {
            return String.format(MESSAGE_FORMAT, aliasTypeName, channelId);
        }

        public String getAliasTypeName() {
            return aliasTypeName;
        }

        public String getChannelId() {
            return channelId;
        }

    }

}
