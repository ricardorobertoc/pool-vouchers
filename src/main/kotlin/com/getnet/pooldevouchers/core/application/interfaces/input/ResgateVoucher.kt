package com.getnet.pooldevouchers.core.application.interfaces.input

import com.getnet.pooldevouchers.core.application.dto.input.ResgateVoucherInputDTO
import com.getnet.pooldevouchers.core.application.dto.output.ResgateVoucherOutputDTO

interface ResgateVoucher {
    fun execute(input: ResgateVoucherInputDTO): ResgateVoucherOutputDTO
}
