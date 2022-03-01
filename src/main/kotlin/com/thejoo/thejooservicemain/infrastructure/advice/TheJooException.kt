package com.thejoo.thejooservicemain.infrastructure.advice

import org.springframework.http.HttpStatus

data class TheJooException(
    var statusCode: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    val errorCode: ExceptionCode,
    var errorMessage: String?,
) : RuntimeException("[$errorCode] $errorMessage") {
    val statusCodeValue: Int = statusCode.value()

    fun toResponseBody() = TheJooExceptionResponse(
        errorCode = errorCode,
        errorMessage = errorMessage,
    )

    companion object {
        fun ofBadRequest(errorCode: ExceptionCode = ExceptionCode.BAD_REQUEST, errorMessage: String? = null) =
            TheJooException(
                statusCode = HttpStatus.BAD_REQUEST,
                errorCode = errorCode,
                errorMessage = errorMessage,
            )

        fun ofUnauthorized(errorMessage: String? = "유저 인증 실패") =
            TheJooException(
                statusCode = HttpStatus.UNAUTHORIZED,
                errorCode = ExceptionCode.UNAUTHORIZED,
                errorMessage = errorMessage,
            )

        fun ofEntityNotFound(errorMessage: String?) =
            TheJooException(
                statusCode = HttpStatus.NOT_FOUND,
                errorCode = ExceptionCode.ENTITY_NOT_FOUND,
                errorMessage = errorMessage
            )

        fun ofUnexpectedException(errorMessage: String? = null) =
            ofInternalServerError(
                errorCode = ExceptionCode.INTERNAL_SERVER_ERROR,
                errorMessage = errorMessage,
            )

        fun ofForbidden(errorMessage: String? = null) =
            TheJooException(
                statusCode = HttpStatus.FORBIDDEN,
                errorCode = ExceptionCode.ACCESS_DENIED,
                errorMessage = errorMessage,
            )

        @SuppressWarnings // <-- TODO: Remove once used
        fun ofInternalServerError(errorCode: ExceptionCode, errorMessage: String?) =
            TheJooException(
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR,
                errorCode = errorCode,
                errorMessage = errorMessage,
            )
    }
}

enum class ExceptionCode {
    /**
     * GENERAL
     */
    BAD_REQUEST,
    ENTITY_NOT_FOUND,
    UNAUTHORIZED,
    ACCESS_DENIED,
    INTERNAL_SERVER_ERROR,
    INVALID_PRINCIPAL,

    /**
     * PROMOTION APPLY EXCEPTIONS
     */
    STORE_OWNER_MISMATCH,
    INVALID_TOKEN_PROVIDED,
    ALREADY_APPLIED_PROMOTION,
    ;
}
