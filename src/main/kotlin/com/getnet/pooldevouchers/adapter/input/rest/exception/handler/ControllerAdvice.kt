package com.getnet.pooldevouchers.adapter.input.rest.exception.handler

import com.getnet.pooldevouchers.adapter.input.rest.exception.BadRequestException
import com.getnet.pooldevouchers.adapter.input.rest.exception.ForbiddenException
import com.getnet.pooldevouchers.adapter.input.rest.exception.NotFoundException
import com.getnet.pooldevouchers.adapter.input.rest.exception.error.ErrorDetailGeneric
import com.getnet.pooldevouchers.adapter.input.rest.exception.error.ResponseError
import com.getnet.pooldevouchers.adapter.input.rest.exception.error.ValidationError
import mu.KLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice(
    private val logger: KLogger,
) {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(
        badRequestException: BadRequestException,
    ): ResponseEntity<ResponseError<ErrorDetailGeneric>> =
        createErrorResponse(HttpStatus.BAD_REQUEST, badRequestException)

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(
        notFoundException: NotFoundException,
    ): ResponseEntity<ResponseError<ErrorDetailGeneric>> =
        createErrorResponse(HttpStatus.NOT_FOUND, notFoundException)

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(
        forbiddenException: ForbiddenException,
    ): ResponseEntity<ResponseError<ErrorDetailGeneric>> =
        createErrorResponse(HttpStatus.FORBIDDEN, forbiddenException)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        httpMessageNotReadableException: Exception,
    ): ResponseEntity<ResponseError<ErrorDetailGeneric>> =
        createErrorResponse(HttpStatus.BAD_REQUEST, httpMessageNotReadableException)

    @ExceptionHandler(Throwable::class)
    fun handleGenericException(
        throwable: Throwable,
    ): ResponseEntity<ResponseError<ErrorDetailGeneric>> =
        createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, throwable)

    @ExceptionHandler(MissingRequestHeaderException::class)
    fun handleMissingRequestHeaderException(
        missingRequestHeaderException: MissingRequestHeaderException,
    ): ResponseEntity<ResponseError<ErrorDetailGeneric>> =
        createErrorResponse(HttpStatus.BAD_REQUEST, missingRequestHeaderException)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleRequestNotValidException(
        missingRequestHeaderException: MethodArgumentNotValidException,
    ): ResponseEntity<ResponseError<ErrorDetailGeneric>> {
        logger.error(missingRequestHeaderException) { missingRequestHeaderException.message }

        val errors = missingRequestHeaderException.fieldErrors.map {
            ValidationError(fieldPath = it.field, error = it.defaultMessage)
        }
        val responseError =
            ResponseError<ErrorDetailGeneric>(status = HttpStatus.BAD_REQUEST.value(), validationErrors = errors)

        return status(HttpStatus.BAD_REQUEST).body(responseError)
    }

    private fun createErrorResponse(
        httpStatus: HttpStatus,
        throwable: Throwable,
    ): ResponseEntity<ResponseError<ErrorDetailGeneric>> {
        if (HttpStatus.NOT_FOUND == httpStatus) logger.warn(throwable) { throwable.message }
        else logger.error(throwable) { throwable.message }

        val errorDetail = ErrorDetailGeneric(
            errorCode = httpStatus.toString(),
            message = throwable.message,
            cause = throwable.cause.toString()
        )
        val responseError = ResponseError(status = httpStatus.value(), errors = listOf(errorDetail))
        return status(httpStatus).body(responseError)
    }
}
