package com.thejoo.thejooservicemain.infrastructure.advice

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TheJooExceptionResponse(
    val errorCode: ExceptionCode,
    var errorMessage: String?,
)
