package com.getnet.pooldevouchers.core.application.dto.input

import com.getnet.pooldevouchers.adapter.output.persistence.documents.Voucher

data class VoucherInputDTO(
    val nome: String,
    val email: String,
    val ofertaEspecial: String,
    val desconto: Int
)

fun VoucherInputDTO.toEntity() =
    Voucher(
        nome = nome,
        email = email,
        ofertaEspecial = ofertaEspecial,
        desconto = desconto
    )
