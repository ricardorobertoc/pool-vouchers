package com.getnet.pooldevouchers.core.application.dto.output

import com.getnet.pooldevouchers.adapter.input.rest.controller.response.ResgateVoucherResponse
import java.time.Instant
import java.util.UUID

data class ResgateVoucherOutputDTO(
    val id: UUID,
    val email: String,
    val codigoVoucher: String,
    val ofertaEspecial: String,
    val desconto: Int,
    var dataUso: Instant?,
    var dataValidade: Instant
)

fun ResgateVoucherOutputDTO.toResponse() =
    ResgateVoucherResponse(
        email = email,
        codigoVoucher = codigoVoucher,
        ofertaEspecial = ofertaEspecial,
        desconto = desconto,
        dataUso = dataUso!!
    )
