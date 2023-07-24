package com.getnet.pooldevouchers.adapter.input.rest.exception.error

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

data class ResponseError<T>(
    val timestamp: Instant = Instant.now(),
    val status: Int,
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val errors: Collection<T> = emptyList(),
    val validationErrors: List<ValidationError> = emptyList(),
)

@SuppressWarnings("UnnecessaryAbstractClass")
abstract class ErrorDetail(
    open val errorCode: String? = null,
    open val message: String? = null,
    open val cause: String? = null,
)

data class ErrorDetailGeneric(
    override val errorCode: String? = null,
    override val message: String? = null,
    override val cause: String? = null,
) : ErrorDetail(
    errorCode = errorCode,
    message = message,
    cause = cause
)

data class ValidationError(
    val fieldPath: String,
    val error: String? = null,
)
