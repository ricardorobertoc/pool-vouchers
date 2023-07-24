package com.getnet.pooldevouchers.port.adapter.output.persistence.repository

import com.getnet.pooldevouchers.adapter.output.persistence.repository.VoucherRepository
import com.getnet.pooldevouchers.common.IntegrationTests
import com.getnet.pooldevouchers.mock.document.criarVoucherFake
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class VoucherRepositoryIT(
    @Autowired private val voucherRepository: VoucherRepository
) : IntegrationTests() {

    @AfterEach
    fun cleanUp() {
        voucherRepository.deleteAll()
    }

    @Test
    fun `deve criar voucher`() {
        criarVoucherFake().run {
            validade(72)
            voucherRepository.save(this).run {
                Assertions.assertNotNull(this)
                Assertions.assertEquals(criarVoucherFake().email, this.email)
                Assertions.assertEquals(criarVoucherFake().ofertaEspecial, this.ofertaEspecial)
            }
        }
    }

    @Test
    fun `deve validar de existe voucher para email e oferta informados`() {
        criarVoucherFake().run {
            validade(72)
            voucherRepository.save(this).run {
                voucherRepository.existsByEmailAndOfertaEspecial(this.email, this.ofertaEspecial).run {
                    Assertions.assertNotNull(this)
                    Assertions.assertEquals(true, this)
                }
            }
        }
    }

    @Test
    fun `deve recuperar voucher nao usado para codigo e email informados`() {
        criarVoucherFake().run {
            validade(72)
            voucherRepository.save(this).run {
                voucherRepository.findByCodigoVoucherAndEmailAndUsadoIsFalse(this.codigoVoucher, this.email).run {
                    Assertions.assertNotNull(this)
                    Assertions.assertEquals(criarVoucherFake().ofertaEspecial, this.get().ofertaEspecial)
                    Assertions.assertEquals(criarVoucherFake().email, this.get().email)
                    Assertions.assertEquals(criarVoucherFake().desconto, this.get().desconto)
                }
            }
        }
    }
}
