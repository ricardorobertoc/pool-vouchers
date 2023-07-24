package com.getnet.pooldevouchers.core.application.interfaces.input

import com.getnet.pooldevouchers.core.application.dto.input.BuscarVouchersInputDTO
import com.getnet.pooldevouchers.core.application.dto.output.BuscarVouchersOutputDTO

interface BuscarVouchers {
    fun execute(input: BuscarVouchersInputDTO): BuscarVouchersOutputDTO
}
