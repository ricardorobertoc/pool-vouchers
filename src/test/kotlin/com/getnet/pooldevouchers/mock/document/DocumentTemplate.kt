package com.getnet.pooldevouchers.mock.document

import com.getnet.pooldevouchers.adapter.input.rest.controller.request.ResgateVoucherRequest
import com.getnet.pooldevouchers.adapter.input.rest.controller.request.VoucherRequest
import com.getnet.pooldevouchers.adapter.output.persistence.documents.Voucher
import java.util.UUID

fun criarVoucherFake() =
    Voucher(
        nome = "Ricardo",
        email = "ricardo@gmail.com",
        ofertaEspecial = "OFERTA_NATAL",
        desconto = 10
    )

fun criarVoucherOutraOferta() =
    Voucher(
        nome = "Ricardo",
        email = "ricardo@gmail.com",
        ofertaEspecial = "OFERTA_ANO_NOVO",
        desconto = 20
    )

fun criarVoucherRequest() =
    VoucherRequest(
        nome = "Ricardo",
        email = "ricardo@gmail.com",
        ofertaEspecial = "OFERTA_ANO_NOVO",
        desconto = 20
    )

fun criarResgateVoucherRequest(voucher: String) =
    ResgateVoucherRequest(
        email = "ricardo@gmail.com",
        voucher = UUID.fromString(voucher),
    )
