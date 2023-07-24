package com.getnet.pooldevouchers.core.usecase

import com.getnet.pooldevouchers.common.UnitTest
import com.getnet.pooldevouchers.common.log.hasLogMessage
import com.getnet.pooldevouchers.core.application.interfaces.output.persistence.VoucherInterface
import com.getnet.pooldevouchers.core.usecase.criacaovoucher.CriacaoVoucherImpl
import com.getnet.pooldevouchers.mock.dto.criarVoucherInputDTO
import com.getnet.pooldevouchers.mock.dto.criarVoucherOutputDTO
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifyOrder
import mu.KLogger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CriacaoVoucherTest(
    @RelaxedMockK private val voucherInterface: VoucherInterface,
    @RelaxedMockK private val logger: KLogger,
) : UnitTest() {

    @InjectMockKs
    lateinit var usecase: CriacaoVoucherImpl

    @AfterEach
    fun tearDown() {
        confirmVerified(
            voucherInterface,
            logger,
        )
    }

    @Test
    fun `deve criar voucher quando nao existir voucher para destinatario e oferta`() {
        val voucherOutput = criarVoucherOutputDTO()
        val voucherInput = criarVoucherInputDTO()

        every { voucherInterface.existeVoucherDestinatarioOferta(any()) } returns false
        every { voucherInterface.criarVoucher(any()) } returns voucherOutput

        usecase.execute(voucherInput).run {
            Assertions.assertEquals(voucherInput.email, this.email)
        }

        verifyOrder {
            logger.info(hasLogMessage("Criando voucher para destinatario de email=${voucherInput.email}"))
            voucherInterface.existeVoucherDestinatarioOferta(any())
            voucherInterface.criarVoucher(any())
            logger.info(hasLogMessage("Criado voucher para destinatario de email=${voucherInput.email}"))
        }
    }
}
