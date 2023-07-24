package com.getnet.pooldevouchers.adapter.output.persistence.mapper

import com.getnet.pooldevouchers.adapter.output.persistence.documents.Voucher
import com.getnet.pooldevouchers.core.application.dto.output.ResgateVoucherOutputDTO
import com.getnet.pooldevouchers.core.application.dto.output.VoucherOutputDTO

fun Voucher.toVoucherOutputDTO() =
    VoucherOutputDTO(
        id = id,
        nome = nome,
        email = email,
        ofertaEspecial = ofertaEspecial,
        desconto = desconto,
        codigoVoucher = codigoVoucher,
        codigoDestOferta = codigoDestOferta,
        dataValidade = dataValidade
    )

fun Voucher.toResgateVoucherOutputDTO() =
    ResgateVoucherOutputDTO(
        id = id,
        email = email,
        codigoVoucher = codigoVoucher,
        ofertaEspecial = ofertaEspecial,
        desconto = desconto,
        dataValidade = dataValidade!!,
        dataUso = dataUso
    )
