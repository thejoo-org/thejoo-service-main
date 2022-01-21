package com.thejoo.thejooservicemain.infrastructure

import com.fasterxml.jackson.annotation.JsonInclude
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.Exception
import java.lang.RuntimeException
import javax.persistence.EntityNotFoundException

@Order(0)
@RestControllerAdvice
class ExceptionHandlingAdvice {
    private val log = LoggerFactory.getLogger(ExceptionHandler::class.java)

    // TODO: FIX THIS
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(JpaObjectRetrievalFailureException::class)
    fun handleJpaObjectRetrievalFailureException(exception: JpaObjectRetrievalFailureException): TheJooException {
        log.error(exception.message, exception)
        return when (exception.rootCause) {
            EntityNotFoundException::class -> TheJooException(
                errorCode = ExceptionCode.ENTITY_NOT_FOUND,
                errorMessage = "Entity Not Found",
            )
            else -> handleRuntimeException(exception)
        }
    }

    // TODO: FIX THIS
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(exception: Exception): TheJooException {
        log.error(exception.message, exception)
        return TheJooException(
            errorCode = ExceptionCode.ENTITY_NOT_FOUND,
            errorMessage = "Entity Not Found",
        )
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: Exception): TheJooException {
        log.error(exception.message, exception)
        return TheJooException(
            errorCode = ExceptionCode.INTERNAL_SERVER_ERROR,
            errorMessage = "Unexpected Error Occurred",
        )
    }
}

@JsonInclude
data class TheJooException(
    val errorCode: ExceptionCode,
    val errorMessage: String,
)

enum class ExceptionCode {
    ENTITY_NOT_FOUND,
    INTERNAL_SERVER_ERROR,
    ;
}