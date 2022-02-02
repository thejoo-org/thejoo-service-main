package com.thejoo.thejooservicemain.infrastructure.advice

import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

@Order(0)
@RestControllerAdvice
class ExceptionHandlingAdvice {
    @ResponseBody
    @ExceptionHandler(TheJooException::class)
    fun handleTheJooException(exception: TheJooException): ResponseEntity<TheJooExceptionResponse> =
        ResponseEntity(exception.toResponseBody(), exception.statusCode)

    // TODO: There must be a better way than this
//    @ResponseBody
//    @ExceptionHandler(RuntimeException::class)
//    fun handleRuntimeException(exception: RuntimeException): ResponseEntity<TheJooExceptionResponse> =
//        TheJooException.ofUnexpectedException("Unexpected Error Occurred")
//            .let(::handleTheJooException)
}
