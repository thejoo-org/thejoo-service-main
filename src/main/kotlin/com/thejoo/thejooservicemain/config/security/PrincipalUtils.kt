package com.thejoo.thejooservicemain.config.security

import java.security.Principal

fun Principal.nameAsLong() = this.name.toLong()