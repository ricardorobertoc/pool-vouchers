package com.getnet.pooldevouchers.adapter.input.rest.controller.response

import java.time.Instant

data class ResgateVoucherResponse(
    val email: String,
    val codigoVoucher: String,
    val ofertaEspecial: String,
    val desconto: Int,
    var dataUso: Instant
)
