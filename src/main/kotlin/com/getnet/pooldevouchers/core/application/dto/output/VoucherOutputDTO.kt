package com.getnet.pooldevouchers.core.application.dto.output

import com.getnet.pooldevouchers.adapter.input.rest.controller.response.VoucherResponse
import java.time.Instant
import java.util.*

data class VoucherOutputDTO(
    val id: UUID,
    val nome: String,
    val email: String,
    val ofertaEspecial: String,
    val desconto: Int,
    val codigoVoucher: String,
    val codigoDestOferta: String,
    var dataValidade: Instant?
)

fun VoucherOutputDTO.toResponse() =
    dataValidade?.let {
        VoucherResponse(
            id = id,
            nome = nome,
            email = email,
            ofertaEspecial = ofertaEspecial,
            desconto = desconto,
            codigoVoucher = codigoVoucher,
            codigoDestOferta = codigoDestOferta,
            dataValidade = it
        )
    }
