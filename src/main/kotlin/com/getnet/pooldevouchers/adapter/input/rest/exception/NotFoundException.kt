package com.getnet.pooldevouchers.adapter.input.rest.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class NotFoundException(reason: String, cause: Throwable?) :
    ResponseStatusException(HttpStatus.NOT_FOUND, reason, cause)
