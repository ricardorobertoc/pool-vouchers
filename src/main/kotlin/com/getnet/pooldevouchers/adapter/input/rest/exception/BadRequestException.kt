package com.getnet.pooldevouchers.adapter.input.rest.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class BadRequestException(reason: String, cause: Throwable? = null) :
    ResponseStatusException(HttpStatus.BAD_REQUEST, reason, cause)
