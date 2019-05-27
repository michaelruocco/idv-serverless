package uk.co.mruoc.idv.plugin.uk.awslambda.identity;

import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.identity.IdentityServiceFactory;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.identity.service.IdentityDaoFactory;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.IdvIdGenerator;
import uk.co.mruoc.idv.dao.identity.DynamoIdentityDaoFactory;
import uk.co.mruoc.idv.plugin.uk.identity.service.UkAliasLoaderService;

public class UkIdentityServiceFactory implements IdentityServiceFactory {

    private final IdentityDao dao;

    private IdentityService service;

    public UkIdentityServiceFactory() {
        this(buildDao());
    }

    public UkIdentityServiceFactory(final IdentityDao dao) {
        this.dao = dao;
        this.service = buildService();
    }

    @Override
    public IdentityService build() {
        return service;
    }

    private IdentityService buildService() {
        return IdentityService.builder()
                .dao(dao)
                .aliasLoaderService(new UkAliasLoaderService())
                .idvIdGenerator(new IdvIdGenerator())
                .build();
    }

    private static IdentityDao buildDao() {
        final IdentityDaoFactory factory = new DynamoIdentityDaoFactory(new Environment());
        return factory.build();
    }

}
