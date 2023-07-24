package com.getnet.pooldevouchers.core.application.interfaces.input

import com.getnet.pooldevouchers.core.application.dto.input.VoucherInputDTO
import com.getnet.pooldevouchers.core.application.dto.output.VoucherOutputDTO

interface CriacaoVoucher {
    fun execute(voucherInput: VoucherInputDTO): VoucherOutputDTO
}
