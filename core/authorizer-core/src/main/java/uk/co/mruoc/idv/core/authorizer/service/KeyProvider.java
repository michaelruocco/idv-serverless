package uk.co.mruoc.idv.core.authorizer.service;

import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;

public interface KeyProvider {

    Key provideKey();

    SignatureAlgorithm provideAlgorithm();

}
