package uk.co.mruoc.idv.core.verificationcontext.model;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;

public interface VerificationContextRequest {

    Channel getChannel();

    Alias getProvidedAlias();

    Activity getActivity();

    String getChannelId();

}
