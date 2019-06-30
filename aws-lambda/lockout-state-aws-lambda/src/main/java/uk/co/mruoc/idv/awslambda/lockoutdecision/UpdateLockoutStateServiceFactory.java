package uk.co.mruoc.idv.awslambda.lockoutdecision;

import uk.co.mruoc.idv.core.lockoutstate.UpdateLockoutStateService;

public interface UpdateLockoutStateServiceFactory {

    UpdateLockoutStateService build();

}