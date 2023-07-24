package com.getnet.pooldevouchers.core.usecase.buscarvouchers

import com.getnet.pooldevouchers.core.application.dto.input.BuscarVouchersInputDTO
import com.getnet.pooldevouchers.core.application.dto.output.BuscarVouchersOutputDTO
import com.getnet.pooldevouchers.core.application.interfaces.input.BuscarVouchers
import com.getnet.pooldevouchers.core.application.interfaces.output.persistence.VoucherInterface
import mu.KLogger
import org.springframework.stereotype.Service

@Service
class BuscarVouchersImpl(
    private val voucherInterface: VoucherInterface,
    private val logger: KLogger
) : BuscarVouchers {

    override fun execute(input: BuscarVouchersInputDTO): BuscarVouchersOutputDTO {
        logger.info { "buscando todos códigos de voucher validos para email=${input.email}" }

        return voucherInterface.buscarVouchersUsuario(input.email).also {
            logger.info { "retornando todos códigos de voucher validos para email=${input.email}" }
        }
    }
}
