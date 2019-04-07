package uk.co.mruoc.idv.awslambda.authorizer.service;

import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;

public interface KeyProvider {

    Key provideKey();

    SignatureAlgorithm provideAlgorithm();

}
