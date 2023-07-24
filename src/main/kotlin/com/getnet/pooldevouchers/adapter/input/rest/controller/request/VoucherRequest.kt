package com.getnet.pooldevouchers.adapter.input.rest.controller.request

import com.getnet.pooldevouchers.core.application.dto.input.VoucherInputDTO
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class VoucherRequest(
    @field:NotEmpty
    val nome: String,
    @field:NotEmpty
    @field:Email
    val email: String,
    @field:NotEmpty
    val ofertaEspecial: String,
    @field:NotNull
    val desconto: Int
)

fun VoucherRequest.toInput() =
    VoucherInputDTO(
        nome = nome,
        email = email,
        ofertaEspecial = ofertaEspecial,
        desconto = desconto
    )
