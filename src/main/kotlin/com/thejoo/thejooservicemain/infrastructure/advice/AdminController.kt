package com.thejoo.thejooservicemain.infrastructure.advice

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@RestController
@PreAuthorize("hasRole('ADMIN')")
annotation class AdminController()
