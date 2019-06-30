package uk.co.mruoc.idv.core.verificationattempts.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;

@Slf4j
@Builder
public class UpdateVerificationAttemptsService {

    private final VerificationAttemptsDao dao;



}
