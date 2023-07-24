package com.getnet.pooldevouchers.adapter.input.rest.controller.response

import org.springframework.web.server.ResponseStatusException

class ApiException(code: Int, message: String) : ResponseStatusException(code, message, null)

data class LoginResponseDto(
    val token: String,
)

data class ItemDto(
    val id: Long,
    val name: String,
    val count: Int,
    val note: String?,
)
