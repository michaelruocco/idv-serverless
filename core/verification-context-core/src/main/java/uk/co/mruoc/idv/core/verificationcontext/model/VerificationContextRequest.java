package uk.co.mruoc.idv.core.verificationcontext.model;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;

public interface VerificationContextRequest {

    Channel getChannel();

    Alias getProvidedAlias();

    Activity getActivity();

}
