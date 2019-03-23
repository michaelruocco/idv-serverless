package uk.co.mruoc.idv.core.identity.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;

import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class IdentityTest {

    private final Alias alias1 = new TokenizedCreditCardNumberAlias("1111111111111111");
    private final Alias alias2 = new TokenizedCreditCardNumberAlias("2222222222222222");

    @Test
    public void cannotCreateIdentityWithoutIdvId() {
        final Throwable thrown = catchThrowable(() -> Identity.withAliases(alias1));

        assertThat(thrown).isInstanceOf(Identity.IdentityMustHaveExactlyOneIdvIdException.class);
    }

    @Test
    public void cannotCreateIdentityWithMoreThanOneIdvId() {
        final Throwable thrown = catchThrowable(() -> Identity.withAliases(new IdvIdAlias(), new IdvIdAlias()));

        assertThat(thrown).isInstanceOf(Identity.IdentityMustHaveExactlyOneIdvIdException.class);
    }

    @Test
    public void cannotAddMoreThanOneIdvId() {
        final Alias idvId = new IdvIdAlias();
        final Identity identity = Identity.withAliases(idvId);

        final Throwable thrown = catchThrowable(() -> identity.addAliases(new IdvIdAlias()));

        assertThat(thrown).isInstanceOf(Identity.IdentityMustHaveExactlyOneIdvIdException.class);
    }

    @Test
    public void cannotRemoveIdvId() {
        final Alias idvId = new IdvIdAlias();
        final Identity identity = Identity.withAliases(idvId);

        final Throwable thrown = catchThrowable(() -> identity.removeAliases(AliasType.Names.IDV_ID));

        assertThat(thrown).isInstanceOf(Identity.IdentityMustHaveExactlyOneIdvIdException.class);
    }

    @Test
    public void canCreateIdentity() {
        final Alias idvId = new IdvIdAlias();

        final Identity identity = Identity.withAliases(idvId);

        assertThat(identity.getAliases()).containsExactly(idvId);
    }

    @Test
    public void shouldAddAliases() {
        final Alias idvId = new IdvIdAlias(UUID.randomUUID());
        final Identity identity = Identity.withAliases(idvId);

        final Identity addedAliasIdentity = identity.addAliases(alias1);

        assertThat(addedAliasIdentity.getAliases()).containsExactlyInAnyOrder(idvId, alias1);
    }

    @Test
    public void shouldNotRetainDuplicateAliasesWhenAddingAliases() {
        final Alias idvId = new IdvIdAlias(UUID.randomUUID());
        final Identity identity = Identity.withAliases(idvId, alias1);

        final Identity addedAliasIdentity = identity.addAliases(alias1);

        assertThat(addedAliasIdentity.getAliases()).containsExactlyInAnyOrder(idvId, alias1);
    }

    @Test
    public void shouldMergeAliasesAndRetainOriginalIdvIdWhenMergingIdentities() {
        final Alias idvId = new IdvIdAlias(UUID.randomUUID());
        final Identity identity = Identity.withAliases(idvId, alias1);

        final Identity identityToMerge = Identity.withAliases(new IdvIdAlias(), alias2);

        final Identity mergedIdentity = identity.merge(identityToMerge);

        assertThat(mergedIdentity.getAliases()).containsExactlyInAnyOrder(idvId, alias1, alias2);
    }

    @Test
    public void shouldNotRetainDuplicateAliasesWhenMergingIdentities() {
        final Alias idvId = new IdvIdAlias(UUID.randomUUID());
        final Identity identity = Identity.withAliases(idvId, alias1);

        final Identity identityToMerge = Identity.withAliases(new IdvIdAlias(), alias1);

        final Identity mergedIdentity = identity.merge(identityToMerge);

        assertThat(mergedIdentity.getAliases()).containsExactlyInAnyOrder(idvId, alias1);
    }

    @Test
    public void shouldReturnTrueIfIdentitiesHaveSameAliases() {
        final Alias idvId = new IdvIdAlias(UUID.randomUUID());
        final Identity identity1 = Identity.withAliases(idvId, alias1);
        final Identity identity2 = Identity.withAliases(idvId, alias1);

        assertThat(identity1.hasSameAliases(identity2)).isTrue();
    }


    @Test
    public void shouldReturnFalseIfIdentitiesDoNotHaveSameAliases() {
        final Alias idvId = new IdvIdAlias(UUID.randomUUID());
        final Identity identity1 = Identity.withAliases(idvId, alias1);
        final Identity identity2 = Identity.withAliases(idvId, alias2);

        assertThat(identity1.hasSameAliases(identity2)).isFalse();
    }

    @Test
    public void shouldReturnFalseIfIdentitiesHaveDifferentNumberOfAliases() {
        final Alias idvId = new IdvIdAlias(UUID.randomUUID());
        final Identity identity1 = Identity.withAliases(idvId, alias1);
        final Identity identity2 = Identity.withAliases(idvId);

        assertThat(identity1.hasSameAliases(identity2)).isFalse();
    }

    @Test
    public void hasAliasShouldWhetherIdentityHasAliasMatchingType() {
        final Identity identity = Identity.withAliases(new IdvIdAlias(), alias1);

        assertThat(identity.hasAlias(AliasType.Names.IDV_ID)).isTrue();
        assertThat(identity.hasAlias(AliasType.Names.CREDIT_CARD_NUMBER)).isTrue();
        assertThat(identity.hasAlias(AliasType.Names.DEBIT_CARD_NUMBER)).isFalse();
    }

    @Test
    public void shouldReturnIdvId() {
        final Alias idvId = new IdvIdAlias();

        final Identity identity = Identity.withAliases(idvId);

        assertThat(identity.getIdvIdAlias()).isEqualTo(idvId);
    }

    @Test
    public void shouldReturnIdvIdIfPopulatedUsingDefaultAlias() {
        final AliasType type = new DefaultAliasType(AliasType.Names.IDV_ID);
        final Alias idvId = new DefaultAlias(type, Alias.Formats.CLEAR_TEXT, UUID.randomUUID().toString());

        final Identity identity = Identity.withAliases(idvId);

        assertThat(identity.getIdvIdAlias()).isEqualTo(idvId);
    }

    @Test
    public void shouldReturnIdvIdValue() {
        final IdvIdAlias idvId = new IdvIdAlias();

        final Identity identity = Identity.withAliases(idvId);

        assertThat(identity.getIdvId()).isEqualTo(idvId.getValueAsUuid());
    }

    @Test
    public void shouldReturnEmptyCollectionIfAliasOfTypeNotFound() {
        final Identity identity = Identity.withAliases(new IdvIdAlias());

        final Collection<Alias> aliases = identity.getAliasesByType(AliasType.Names.CREDIT_CARD_NUMBER);

        assertThat(aliases).isEmpty();
    }

    @Test
    public void shouldRemoveAlias() {
        final Alias idvId = new IdvIdAlias();
        final Identity identity = Identity.withAliases(idvId, alias1);

        final Identity identityWithoutCardholderId = identity.removeAliases(AliasType.Names.CREDIT_CARD_NUMBER);

        assertThat(identityWithoutCardholderId.getAliases()).containsExactly(idvId);
    }

    @Test
    public void shouldNotAllowDuplicateAliases() {
        final Alias idvId = new IdvIdAlias();

        final Identity identity = Identity.withAliases(idvId, alias1, alias1);

        assertThat(identity.getAliases()).containsExactlyInAnyOrder(idvId, alias1);
    }

    @Test
    public void shouldNotAllowDuplicateAliasesToBeAdded() {
        final Alias idvId = new IdvIdAlias();
        final Identity identity = Identity.withAliases(idvId, alias1);

        final Identity aliasAddedIdentity = identity.addAliases(alias1);

        assertThat(aliasAddedIdentity.getAliases()).containsExactlyInAnyOrder(idvId, alias1);
    }

    @Test
    public void shouldPrintAllAliases() {
        final Alias idvId = new IdvIdAlias(UUID.fromString("786fa43d-6bcd-4a0c-ab7e-21348eb77faf"));
        final Identity identity = Identity.withAliases(idvId, alias1);

        final String value = identity.toString();

        assertThat(value).isEqualTo("Identity(aliases=Aliases(aliases=[" +
                "DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=786fa43d-6bcd-4a0c-ab7e-21348eb77faf), " +
                "DefaultAlias(type=DefaultAliasType(name=CREDIT_CARD_NUMBER), format=TOKENIZED, value=1111111111111111)" +
                "]))");
    }

    @Test
    public void shouldReturnAliasCount() {
        final Alias idvId = new IdvIdAlias(UUID.fromString("786fa43d-6bcd-4a0c-ab7e-21348eb77faf"));
        final Identity identity = Identity.withAliases(idvId, alias1);

        final int aliasCount = identity.getAliasCount();

        assertThat(aliasCount).isEqualTo(2);
    }

}
