package com.getnet.pooldevouchers.mock.dto

import com.getnet.pooldevouchers.core.application.dto.input.ResgateVoucherInputDTO
import com.getnet.pooldevouchers.core.application.dto.input.VoucherInputDTO
import com.getnet.pooldevouchers.core.application.dto.output.ResgateVoucherOutputDTO
import com.getnet.pooldevouchers.core.application.dto.output.VoucherOutputDTO
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

fun criarVoucherOutputDTO() =
    VoucherOutputDTO(
        id = UUID.randomUUID(),
        nome = "Ricardo",
        email = "ricardo@gmail.com",
        ofertaEspecial = "OFERTA_ANO_NOVO",
        desconto = 20,
        codigoVoucher = UUID.randomUUID().toString(),
        codigoDestOferta = UUID.randomUUID().toString(),
        dataValidade = Instant.now()
    )

fun criarVoucherInputDTO() =
    VoucherInputDTO(
        nome = "Ricardo",
        email = "ricardo@gmail.com",
        ofertaEspecial = "OFERTA_ANO_NOVO",
        desconto = 20
    )

fun criarResagateVoucherInputDTO() =
    ResgateVoucherInputDTO(
        email = "ricardo@gmail.com",
        voucher = UUID.randomUUID().toString()
    )

fun criarResgateVoucherOutputDTO(voucher: String) =
    ResgateVoucherOutputDTO(
        id = UUID.randomUUID(),
        email = "ricardo@gmail.com",
        ofertaEspecial = "OFERTA_ANO_NOVO",
        desconto = 20,
        codigoVoucher = voucher,
        dataValidade = Instant.now().plus(72, ChronoUnit.HOURS),
        dataUso = Instant.now()
    )
