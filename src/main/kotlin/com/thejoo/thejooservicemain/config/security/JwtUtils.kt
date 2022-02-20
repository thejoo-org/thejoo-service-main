package com.thejoo.thejooservicemain.config.security

import com.auth0.jwt.JWTCreator
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.Verification
import com.thejoo.thejooservicemain.config.AuthTokenType

fun DecodedJWT.subjectAsLong() = subject.toLong()

fun JWTCreator.Builder.withClaim(name: String, value: AuthTokenType) = withClaim(name, value.name)

fun Verification.withClaim(name: String, value: AuthTokenType) = withClaim(name, value.name)
