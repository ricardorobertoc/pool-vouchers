package com.getnet.pooldevouchers.port.adapter.input.rest.controller

import com.getnet.pooldevouchers.adapter.input.rest.controller.request.ResgateVoucherRequest
import com.getnet.pooldevouchers.adapter.input.rest.controller.response.ResgateVoucherResponse
import com.getnet.pooldevouchers.adapter.input.rest.controller.response.VoucherResponse
import com.getnet.pooldevouchers.adapter.input.rest.exception.error.ResponseError
import com.getnet.pooldevouchers.adapter.output.persistence.documents.authentication.User
import com.getnet.pooldevouchers.adapter.output.persistence.repository.VoucherRepository
import com.getnet.pooldevouchers.adapter.output.persistence.repository.authentication.UserRepository
import com.getnet.pooldevouchers.common.BaseHTTPClientIT
import com.getnet.pooldevouchers.core.application.authentication.service.TokenService
import com.getnet.pooldevouchers.mock.document.criarResgateVoucherRequest
import com.getnet.pooldevouchers.mock.document.criarVoucherFake
import com.getnet.pooldevouchers.mock.document.criarVoucherOutraOferta
import com.getnet.pooldevouchers.mock.document.criarVoucherRequest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import java.util.*

class VoucherControllerIT(
    @Autowired val voucherRepository: VoucherRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired val tokenService: TokenService,
    @Autowired val testRestTemplate: TestRestTemplate,
) : BaseHTTPClientIT() {

    @AfterEach
    fun cleanUp() {
        voucherRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun `deve criar voucher, retornar codigo 200 com VoucherResponse`() {
        criarVoucherFake().run {
            validade(72)
            voucherRepository.save(this).run {
                Assertions.assertNotNull(this)
            }
        }

        val user = userRepository.save(User(name = "teste", password = "teste123"))
        val jwtToken = tokenService.createToken(user)

        val request = criarVoucherRequest()
        val requestEntity = RequestEntity.post("/v1/pool-de-voucher/voucher")
            .header("Authorization", "Bearer $jwtToken").body(request)

        testRestTemplate.exchange(requestEntity, VoucherResponse::class.java).let { response ->
            Assertions.assertNotNull(response)
            Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        }
    }

    @Test
    fun `deve lancar ForbiddenException quando ja existir voucher para destinatario e oferta`() {
        criarVoucherOutraOferta().run {
            validade(24)
            voucherRepository.save(this).run {
                Assertions.assertNotNull(this)
            }
        }

        val request = criarVoucherRequest()
        val user = userRepository.save(User(name = "teste", password = "teste123"))
        val jwtToken = tokenService.createToken(user)

        val requestEntity = RequestEntity.post("/v1/pool-de-voucher/voucher")
            .header("Authorization", "Bearer $jwtToken").body(request)

        testRestTemplate.exchange(requestEntity, ResponseError::class.java).let { response ->
            Assertions.assertNotNull(response)
            Assertions.assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
            val errorResult = response.body!!.errors.first() as LinkedHashMap<*, *>
            Assertions.assertTrue(
                errorResult["message"].toString().contains(
                    "403 FORBIDDEN \"Ja existe voucher para destinatario de email " +
                        "${criarVoucherOutraOferta().email} e oferta ${criarVoucherOutraOferta().ofertaEspecial}\""
                )
            )
        }
    }

    @Test
    fun `deve recuparar voucher, retornar codigo 200 com ResagateVoucherResponse`() {

        var request: ResgateVoucherRequest?
        criarVoucherFake().run {
            validade(72)
            voucherRepository.save(this).run {
                Assertions.assertNotNull(this)
            }
            request = criarResgateVoucherRequest(this.codigoVoucher)
        }

        val user = userRepository.save(User(name = "teste", password = "teste123"))
        val jwtToken = tokenService.createToken(user)

        val requestEntity = RequestEntity.put("/v1/pool-de-voucher/voucher")
            .header("Authorization", "Bearer $jwtToken").body(request!!)

        testRestTemplate.exchange(requestEntity, ResgateVoucherResponse::class.java).let { response ->
            Assertions.assertNotNull(response)
            Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        }
    }
}
