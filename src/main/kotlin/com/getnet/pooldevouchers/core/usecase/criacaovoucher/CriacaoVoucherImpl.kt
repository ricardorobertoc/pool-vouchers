package com.getnet.pooldevouchers.core.usecase.criacaovoucher

import com.getnet.pooldevouchers.adapter.input.rest.exception.ForbiddenException
import com.getnet.pooldevouchers.core.application.dto.input.VoucherInputDTO
import com.getnet.pooldevouchers.core.application.dto.output.VoucherOutputDTO
import com.getnet.pooldevouchers.core.application.interfaces.input.CriacaoVoucher
import com.getnet.pooldevouchers.core.application.interfaces.output.persistence.VoucherInterface
import mu.KLogger
import org.springframework.stereotype.Service

@Service
class CriacaoVoucherImpl(
    private val voucherInterface: VoucherInterface,
    private val logger: KLogger
) : CriacaoVoucher {
    override fun execute(voucherInput: VoucherInputDTO): VoucherOutputDTO {
        logger.info { "Criando voucher para destinatario de email=${voucherInput.email}" }

        if (voucherInterface.existeVoucherDestinatarioOferta(voucherInput)) {
            throw ForbiddenException("Ja existe voucher para destinatario de email ${voucherInput.email} " +
                "e oferta ${voucherInput.ofertaEspecial}", null)
        }

        return voucherInterface.criarVoucher(voucherInput).also {
            logger.info { "Criado voucher para destinatario de email=${it.email}" }
        }
    }
}
