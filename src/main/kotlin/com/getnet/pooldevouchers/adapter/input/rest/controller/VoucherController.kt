package com.getnet.pooldevouchers.adapter.input.rest.controller

import com.getnet.pooldevouchers.adapter.input.rest.controller.request.BuscarVouchersRequest
import com.getnet.pooldevouchers.adapter.input.rest.controller.request.ResgateVoucherRequest
import com.getnet.pooldevouchers.adapter.input.rest.controller.request.VoucherRequest
import com.getnet.pooldevouchers.adapter.input.rest.controller.request.toInput
import com.getnet.pooldevouchers.adapter.input.rest.controller.response.BuscarVouchersResponse
import com.getnet.pooldevouchers.adapter.input.rest.controller.response.ResgateVoucherResponse
import com.getnet.pooldevouchers.adapter.input.rest.controller.response.VoucherResponse
import com.getnet.pooldevouchers.adapter.input.rest.exception.error.ResponseError
import com.getnet.pooldevouchers.core.application.dto.output.toResponse
import com.getnet.pooldevouchers.core.application.interfaces.input.BuscarVouchers
import com.getnet.pooldevouchers.core.application.interfaces.input.CriacaoVoucher
import com.getnet.pooldevouchers.core.application.interfaces.input.ResgateVoucher
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import mu.KLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/v1/pool-de-voucher/voucher")
class VoucherController(
    private val criacaoVoucher: CriacaoVoucher,
    private val resgateVoucher: ResgateVoucher,
    private val buscarVouchers: BuscarVouchers,
    private val logger: KLogger
) {

    @PostMapping
    @Operation(
        tags = ["Voucher"],
        summary = "Criar voucher",
        description = "Criar voucher para destinatario e ofeta especial",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Success Request",
                content = [Content(schema = Schema(implementation = VoucherResponse::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Forbidden Exception",
                content = [Content(schema = Schema(implementation = ResponseError::class))]
            )
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    fun criarVoucher(@RequestBody resquest: VoucherRequest): ResponseEntity<VoucherResponse> {
        logger.debug { "criando voucher" }
        return criacaoVoucher.execute(resquest.toInput()).run {
            logger.info { "voucher criado para destinatario de email=${this.email}" }
            ResponseEntity.ok().body(toResponse())
        }
    }

    @PutMapping
    @Operation(
        tags = ["Voucher"],
        summary = "Resgatar voucher",
        description = "Resgatar voucher valido para destinatario informado por email",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Success Request",
                content = [Content(schema = Schema(implementation = VoucherResponse::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Not Found",
                content = [Content(schema = Schema(implementation = ResponseError::class))]
            )
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    fun resgatarVoucher(@RequestBody resquest: ResgateVoucherRequest): ResponseEntity<ResgateVoucherResponse> {
        logger.debug { "resgatando voucher para uso" }
        return resgateVoucher.execute(resquest.toInput()).run {
            logger.info { "voucher resgatado para destinatario de email=${resquest.email}" }
            ResponseEntity.ok().body(toResponse())
        }
    }

    @GetMapping
    @Operation(
        tags = ["Voucher"],
        summary = "Buscar vouchers",
        description = "Retonar todos os seus códigos de voucher válidos com o nome das ofertas",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Success Request",
                content = [Content(schema = Schema(implementation = VoucherResponse::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Not Found",
                content = [Content(schema = Schema(implementation = ResponseError::class))]
            )
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    fun buscarVouchers(@RequestBody resquest: BuscarVouchersRequest): ResponseEntity<BuscarVouchersResponse> {
        logger.debug { "buscando todos códigos de voucher validos" }
        return buscarVouchers.execute(resquest.toInput()).run {
            logger.info { "retornando todos códigos de voucher validos para email=${resquest.email}" }
            ResponseEntity.ok().body(toResponse())
        }
    }
}
