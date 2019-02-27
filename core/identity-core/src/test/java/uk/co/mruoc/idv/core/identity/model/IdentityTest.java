package uk.co.mruoc.idv.core.identity.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.BukCustomerIdAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.UkcCardholderIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.UkcCardholderIdAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.CreditCardNumberAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.DebitCardNumberAliasType;

import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class IdentityTest {

    @Test
    public void cannotCreateIdentityWithoutIdvId() {
        final Throwable thrown = catchThrowable(() -> Identity.withAliases(new UkcCardholderIdAlias("12345678")));

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

        final Throwable thrown = catchThrowable(() -> identity.removeAliases(IdvIdAliasType.NAME));

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
        final Alias cardholderId = new UkcCardholderIdAlias("12345678");
        final Identity identity = Identity.withAliases(idvId);

        final Identity addedAliasIdentity = identity.addAliases(cardholderId);

        assertThat(addedAliasIdentity.getAliases()).containsExactlyInAnyOrder(idvId, cardholderId);
    }

    @Test
    public void shouldNotRetainDuplicateAliasesWhenAddingAliases() {
        final Alias idvId = new IdvIdAlias(UUID.randomUUID());
        final Alias cardholderId = new UkcCardholderIdAlias("12345678");
        final Identity identity = Identity.withAliases(idvId, cardholderId);

        final Identity addedAliasIdentity = identity.addAliases(cardholderId);

        assertThat(addedAliasIdentity.getAliases()).containsExactlyInAnyOrder(idvId, cardholderId);
    }

    @Test
    public void shouldMergeAliasesAndRetainOriginalIdvIdWhenMergingIdentities() {
        final Alias idvId = new IdvIdAlias(UUID.randomUUID());
        final Alias cardholderId1 = new UkcCardholderIdAlias("12345678");
        final Identity identity = Identity.withAliases(idvId, cardholderId1);

        final Alias cardholderId2 = new UkcCardholderIdAlias("87654321");
        final Identity identityToMerge = Identity.withAliases(new IdvIdAlias(), cardholderId2);

        final Identity mergedIdentity = identity.merge(identityToMerge);

        assertThat(mergedIdentity.getAliases()).containsExactlyInAnyOrder(idvId, cardholderId1, cardholderId2);
    }

    @Test
    public void shouldNotRetainDuplicateAliasesWhenMergingIdentities() {
        final Alias idvId = new IdvIdAlias(UUID.randomUUID());
        final Alias cardholderId1 = new UkcCardholderIdAlias("12345678");
        final Identity identity = Identity.withAliases(idvId, cardholderId1);

        final Identity identityToMerge = Identity.withAliases(new IdvIdAlias(), cardholderId1);

        final Identity mergedIdentity = identity.merge(identityToMerge);

        assertThat(mergedIdentity.getAliases()).containsExactlyInAnyOrder(idvId, cardholderId1);
    }

    @Test
    public void shouldReturnTrueIfIdentitiesHaveSameAliases() {
        final Alias idvId = new IdvIdAlias(UUID.randomUUID());
        final Alias cardholderId = new UkcCardholderIdAlias("12345678");
        final Identity identity1 = Identity.withAliases(idvId, cardholderId);
        final Identity identity2 = Identity.withAliases(idvId, cardholderId);

        assertThat(identity1.hasSameAliases(identity2)).isTrue();
    }


    @Test
    public void shouldReturnFalseIfIdentitiesDoNotHaveSameAliases() {
        final Alias idvId = new IdvIdAlias(UUID.randomUUID());
        final Identity identity1 = Identity.withAliases(idvId, new UkcCardholderIdAlias("12345678"));
        final Identity identity2 = Identity.withAliases(idvId, new UkcCardholderIdAlias("12345677"));

        assertThat(identity1.hasSameAliases(identity2)).isFalse();
    }

    @Test
    public void shouldReturnFalseIfIdentitiesHaveDifferentNumberOfAliases() {
        final Alias idvId = new IdvIdAlias(UUID.randomUUID());
        final Identity identity1 = Identity.withAliases(idvId, new UkcCardholderIdAlias("12345678"));
        final Identity identity2 = Identity.withAliases(idvId);

        assertThat(identity1.hasSameAliases(identity2)).isFalse();
    }

    @Test
    public void hasAliasShouldWhetherIdentityHasAliasMatchingType() {
        final Identity identity = Identity.withAliases(new IdvIdAlias(), new UkcCardholderIdAlias("12345678"));

        assertThat(identity.hasAlias(IdvIdAliasType.NAME)).isTrue();
        assertThat(identity.hasAlias(UkcCardholderIdAliasType.NAME)).isTrue();
        assertThat(identity.hasAlias(BukCustomerIdAliasType.NAME)).isFalse();
        assertThat(identity.hasAlias(CreditCardNumberAliasType.NAME)).isFalse();
        assertThat(identity.hasAlias(DebitCardNumberAliasType.NAME)).isFalse();
    }

    @Test
    public void shouldReturnIdvId() {
        final Alias idvId = new IdvIdAlias();

        final Identity identity = Identity.withAliases(idvId);

        assertThat(identity.getIdvId()).isEqualTo(idvId);
    }

    @Test
    public void shouldReturnIdvIdValue() {
        final IdvIdAlias idvId = new IdvIdAlias();

        final Identity identity = Identity.withAliases(idvId);

        assertThat(identity.getIdvIdValue()).isEqualTo(idvId.getValueAsUuid());
    }

    @Test
    public void shouldReturnEmptyCollectionIfAliasOfTypeNotFound() {
        final Identity identity = Identity.withAliases(new IdvIdAlias());

        final Collection<Alias> aliases = identity.getAliasesByType(UkcCardholderIdAliasType.NAME);

        assertThat(aliases).isEmpty();
    }

    @Test
    public void shouldRemoveAlias() {
        final Alias idvId = new IdvIdAlias();
        final Identity identity = Identity.withAliases(idvId, new UkcCardholderIdAlias("12345678"));

        final Identity identityWithoutCardholderId = identity.removeAliases(UkcCardholderIdAliasType.NAME);

        assertThat(identityWithoutCardholderId.getAliases()).containsExactly(idvId);
    }

    @Test
    public void shouldNotAllowDuplicateAliases() {
        final Alias idvId = new IdvIdAlias();
        final Alias cardholderId = new UkcCardholderIdAlias("12345678");

        final Identity identity = Identity.withAliases(idvId, cardholderId, cardholderId);

        assertThat(identity.getAliases()).containsExactlyInAnyOrder(idvId, cardholderId);
    }

    @Test
    public void shouldNotAllowDuplicateAliasesToBeAdded() {
        final Alias idvId = new IdvIdAlias();
        final Alias cardholderId = new UkcCardholderIdAlias("12345678");
        final Identity identity = Identity.withAliases(idvId, cardholderId);

        final Identity aliasAddedIdentity = identity.addAliases(cardholderId);

        assertThat(aliasAddedIdentity.getAliases()).containsExactlyInAnyOrder(idvId, cardholderId);
    }

    @Test
    public void shouldPrintAllAliases() {
        final Alias idvId = new IdvIdAlias(UUID.fromString("786fa43d-6bcd-4a0c-ab7e-21348eb77faf"));
        final Identity identity = Identity.withAliases(idvId, new UkcCardholderIdAlias("12345678"));

        final String value = identity.toString();

        assertThat(value).isEqualTo("Identity(aliases=Aliases(aliases=[" +
                "DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=786fa43d-6bcd-4a0c-ab7e-21348eb77faf), " +
                "DefaultAlias(type=DefaultAliasType(name=UKC_CARDHOLDER_ID), format=CLEAR_TEXT, value=12345678)" +
                "]))");
    }

}