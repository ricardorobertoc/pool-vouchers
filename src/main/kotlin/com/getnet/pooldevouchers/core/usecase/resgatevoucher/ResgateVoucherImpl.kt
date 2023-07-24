package com.getnet.pooldevouchers.core.usecase.resgatevoucher

import com.getnet.pooldevouchers.adapter.input.rest.exception.NotFoundException
import com.getnet.pooldevouchers.core.application.dto.input.ResgateVoucherInputDTO
import com.getnet.pooldevouchers.core.application.dto.output.ResgateVoucherOutputDTO
import com.getnet.pooldevouchers.core.application.interfaces.input.ResgateVoucher
import com.getnet.pooldevouchers.core.application.interfaces.output.persistence.VoucherInterface
import mu.KLogger
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class ResgateVoucherImpl(
    private val voucherInterface: VoucherInterface,
    private val logger: KLogger
) : ResgateVoucher {
    override fun execute(input: ResgateVoucherInputDTO): ResgateVoucherOutputDTO {
        logger.info { "Resgatando voucher para destinatario de email=${input.email}" }

        voucherInterface.recuperarVoucherNaoUsado(input).run {
            if (voucherExpirado(this.dataValidade)) {
                throw NotFoundException("Não encontrado voucher valiso para codido=${input.voucher}", null)
            }
            return voucherAtaulizado(this.id)
        }
    }

    private fun voucherExpirado(dataValidade: Instant): Boolean {
        return dataValidade < Instant.now()
    }

    private fun voucherAtaulizado(voucher: UUID): ResgateVoucherOutputDTO {
        return voucherInterface.atualizarVoucherComoUsado(voucher).also {
            logger.info { "Voucher=${it.codigoVoucher} atualizado para usado" }
        }
    }
}
