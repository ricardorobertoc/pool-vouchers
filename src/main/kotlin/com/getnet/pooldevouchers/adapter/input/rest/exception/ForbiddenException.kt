package com.getnet.pooldevouchers.adapter.input.rest.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ForbiddenException(reason: String, cause: Throwable?) :
    ResponseStatusException(HttpStatus.FORBIDDEN, reason, cause)
