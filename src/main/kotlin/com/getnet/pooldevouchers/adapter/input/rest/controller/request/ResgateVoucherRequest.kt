package com.getnet.pooldevouchers.adapter.input.rest.controller.request

import com.getnet.pooldevouchers.core.application.dto.input.ResgateVoucherInputDTO
import java.util.UUID
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ResgateVoucherRequest(
    @field:NotNull
    val voucher: UUID,
    @field:NotEmpty
    @field:Email
    val email: String,
)

fun ResgateVoucherRequest.toInput() =
    ResgateVoucherInputDTO(
        voucher = voucher.toString(),
        email = email
    )
