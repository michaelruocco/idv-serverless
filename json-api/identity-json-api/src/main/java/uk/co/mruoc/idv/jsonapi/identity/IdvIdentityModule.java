package uk.co.mruoc.idv.jsonapi.identity;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;

public class IdvIdentityModule extends SimpleModule {

    public IdvIdentityModule() {
        addDeserializer(Identity.class, new IdentityDeserializer());

        setMixInAnnotation(DefaultAlias.class, DefaultAliasMixin.class);
        setMixInAnnotation(IdvIdAlias.class, IdvIdAliasMixin.class);
        setMixInAnnotation(Identity.class, IdentityMixin.class);
    }

}
