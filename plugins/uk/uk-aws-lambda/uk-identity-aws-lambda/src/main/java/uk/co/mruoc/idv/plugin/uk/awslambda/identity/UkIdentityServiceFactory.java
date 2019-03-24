package uk.co.mruoc.idv.plugin.uk.awslambda.identity;

import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.identity.IdentityServiceFactory;
import uk.co.mruoc.idv.core.identity.service.IdentityDaoFactory;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.IdvIdGenerator;
import uk.co.mruoc.idv.dao.identity.DynamoIdentityDaoFactory;
import uk.co.mruoc.idv.plugin.uk.identity.service.UkAliasLoaderService;

public class UkIdentityServiceFactory implements IdentityServiceFactory {

    private static IdentityService IDENTITY_SERVICE;

    private final IdentityDaoFactory daoFactory;

    public UkIdentityServiceFactory() {
        this(new DynamoIdentityDaoFactory(new Environment()));
    }

    public UkIdentityServiceFactory(final IdentityDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public IdentityService getIdentityService() {
        if (IDENTITY_SERVICE == null) {
            IDENTITY_SERVICE = buildService();
        }
        return IDENTITY_SERVICE;
    }

    private IdentityService buildService() {
        return IdentityService.builder()
                .dao(daoFactory.build())
                .aliasLoaderService(new UkAliasLoaderService())
                .idvIdGenerator(new IdvIdGenerator())
                .build();
    }

}
