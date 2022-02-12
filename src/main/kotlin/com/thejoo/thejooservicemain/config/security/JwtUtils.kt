package com.thejoo.thejooservicemain.config.security

import com.auth0.jwt.interfaces.DecodedJWT

fun DecodedJWT.subjectAsLong() = subject.toLong()
