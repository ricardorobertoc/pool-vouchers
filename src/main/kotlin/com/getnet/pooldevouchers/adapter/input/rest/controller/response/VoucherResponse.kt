package com.getnet.pooldevouchers.adapter.input.rest.controller.response

import java.time.Instant
import java.util.*

data class VoucherResponse(
    val id: UUID,
    val nome: String,
    val email: String,
    val ofertaEspecial: String,
    val desconto: Int,
    val codigoVoucher: String,
    val codigoDestOferta: String,
    var dataValidade: Instant
)
