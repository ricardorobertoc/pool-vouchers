package com.getnet.pooldevouchers.core.usecase

import com.getnet.pooldevouchers.common.UnitTest
import com.getnet.pooldevouchers.common.log.hasLogMessage
import com.getnet.pooldevouchers.core.application.interfaces.output.persistence.VoucherInterface
import com.getnet.pooldevouchers.core.usecase.resgatevoucher.ResgateVoucherImpl
import com.getnet.pooldevouchers.mock.dto.criarResagateVoucherInputDTO
import com.getnet.pooldevouchers.mock.dto.criarResgateVoucherOutputDTO
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifyOrder
import mu.KLogger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ResagateVoucherTest(
    @RelaxedMockK private val voucherInterface: VoucherInterface,
    @RelaxedMockK private val logger: KLogger,
) : UnitTest() {

    @InjectMockKs
    lateinit var usecase: ResgateVoucherImpl

    @AfterEach
    fun tearDown() {
        confirmVerified(
            voucherInterface,
            logger,
        )
    }

    @Test
    fun `deve criar voucher quando nao existir voucher para destinatario e oferta`() {
        val input = criarResagateVoucherInputDTO()
        val voucherOutput = criarResgateVoucherOutputDTO(input.voucher)

        every { voucherInterface.recuperarVoucherNaoUsado(any()) } returns voucherOutput
        every { voucherInterface.atualizarVoucherComoUsado(any()) } returns voucherOutput

        usecase.execute(input).run {
            Assertions.assertEquals(input.email, this.email)
        }

        verifyOrder {
            logger.info(hasLogMessage("Resgatando voucher para destinatario de email=${input.email}"))
            voucherInterface.recuperarVoucherNaoUsado(any())
            voucherInterface.atualizarVoucherComoUsado(any())
            logger.info(hasLogMessage("Voucher=${input.voucher} atualizado para usado"))
        }
    }
}
