package com.thejoo.thejooservicemain.config.security

import com.auth0.jwt.interfaces.DecodedJWT

fun DecodedJWT.subjectAsLong() = this.subject.toLong()