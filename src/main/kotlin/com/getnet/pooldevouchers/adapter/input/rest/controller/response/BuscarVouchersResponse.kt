package com.getnet.pooldevouchers.adapter.input.rest.controller.response

data class BuscarVouchersResponse(
    val listaVouchers: List<DadosVoucher>? = emptyList()
)

data class DadosVoucher(
    val codigoVoucher: String,
    val oferta: String,
)
