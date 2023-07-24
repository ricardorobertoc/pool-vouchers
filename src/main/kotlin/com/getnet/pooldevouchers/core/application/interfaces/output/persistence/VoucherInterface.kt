package com.getnet.pooldevouchers.core.application.interfaces.output.persistence

import com.getnet.pooldevouchers.core.application.dto.input.ResgateVoucherInputDTO
import com.getnet.pooldevouchers.core.application.dto.input.VoucherInputDTO
import com.getnet.pooldevouchers.core.application.dto.output.BuscarVouchersOutputDTO
import com.getnet.pooldevouchers.core.application.dto.output.ResgateVoucherOutputDTO
import com.getnet.pooldevouchers.core.application.dto.output.VoucherOutputDTO
import java.util.UUID

interface VoucherInterface {
    fun criarVoucher(voucherInput: VoucherInputDTO): VoucherOutputDTO

    fun existeVoucherDestinatarioOferta(voucherInput: VoucherInputDTO): Boolean

    fun recuperarVoucherNaoUsado(input: ResgateVoucherInputDTO): ResgateVoucherOutputDTO

    fun atualizarVoucherComoUsado(voucher: UUID): ResgateVoucherOutputDTO

    fun buscarVouchersUsuario(email: String): BuscarVouchersOutputDTO
}
