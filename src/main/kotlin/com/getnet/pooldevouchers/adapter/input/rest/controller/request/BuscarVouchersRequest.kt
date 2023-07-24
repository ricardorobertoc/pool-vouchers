package com.getnet.pooldevouchers.adapter.input.rest.controller.request

import com.getnet.pooldevouchers.core.application.dto.input.BuscarVouchersInputDTO
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class BuscarVouchersRequest(
    @field:NotEmpty
    @field:Email
    val email: String,
)

fun BuscarVouchersRequest.toInput() =
    BuscarVouchersInputDTO(
        email = email
    )
