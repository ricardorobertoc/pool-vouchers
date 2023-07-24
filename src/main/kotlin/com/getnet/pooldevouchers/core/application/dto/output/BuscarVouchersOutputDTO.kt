package com.getnet.pooldevouchers.core.application.dto.output

import com.getnet.pooldevouchers.adapter.input.rest.controller.response.BuscarVouchersResponse
import com.getnet.pooldevouchers.adapter.input.rest.controller.response.DadosVoucher

class BuscarVouchersOutputDTO(
    val listaVouchers: List<DadosVoucherDTO>,
)

data class DadosVoucherDTO(
    val codigoVoucher: String,
    val oferta: String,
)

fun BuscarVouchersOutputDTO.toResponse(): BuscarVouchersResponse {
    val listaDadosVoucher = mutableListOf<DadosVoucher>()
    listaVouchers.forEach {
        DadosVoucher(
            codigoVoucher = it.codigoVoucher,
            oferta = it.oferta
        ).run {
            listaDadosVoucher.add(this)
        }
    }
    return BuscarVouchersResponse(listaVouchers = listaDadosVoucher)
}
