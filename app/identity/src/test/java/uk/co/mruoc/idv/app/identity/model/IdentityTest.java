package uk.co.mruoc.idv.app.identity.model;

import org.junit.Test;
import uk.co.mruoc.idv.app.identity.model.alias.Alias;
import uk.co.mruoc.idv.app.identity.model.alias.AliasType;
import uk.co.mruoc.idv.app.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.app.identity.model.alias.UkcCardholderIdAlias;

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

        assertThat(identity.hasAlias(AliasType.IDV_ID)).isTrue();
        assertThat(identity.hasAlias(AliasType.UKC_CARDHOLDER_ID)).isTrue();
        assertThat(identity.hasAlias(AliasType.BUK_CUSTOMER_ID)).isFalse();
        assertThat(identity.hasAlias(AliasType.CREDIT_CARD_NUMBER)).isFalse();
        assertThat(identity.hasAlias(AliasType.DEBIT_CARD_NUMBER)).isFalse();
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
    public void shouldThrowExceptionIfAliasTypeNotFound() {
        final Alias idvId = new IdvIdAlias();
        final Identity identity = Identity.withAliases(idvId);
        final String expectedMessage = String.format("Identity(aliases=[AbstractAlias(type=IDV_ID, value=%s)]) does not have alias of type [UKC_CARDHOLDER_ID]", idvId.getValue());

        final Throwable thrown = catchThrowable(() -> identity.getAlias(AliasType.UKC_CARDHOLDER_ID));

        assertThat(thrown)
                .isInstanceOf(Identity.IdentityAliasOfTypeNotFoundException.class)
                .hasMessage(expectedMessage);
    }

}
