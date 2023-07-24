package com.getnet.pooldevouchers.adapter.output.persistence

import com.getnet.pooldevouchers.adapter.input.rest.exception.NotFoundException
import com.getnet.pooldevouchers.adapter.output.persistence.mapper.toResgateVoucherOutputDTO
import com.getnet.pooldevouchers.adapter.output.persistence.mapper.toVoucherOutputDTO
import com.getnet.pooldevouchers.adapter.output.persistence.repository.VoucherRepository
import com.getnet.pooldevouchers.core.application.configuration.properties.VoucherProperties
import com.getnet.pooldevouchers.core.application.dto.input.ResgateVoucherInputDTO
import com.getnet.pooldevouchers.core.application.dto.input.VoucherInputDTO
import com.getnet.pooldevouchers.core.application.dto.input.toEntity
import com.getnet.pooldevouchers.core.application.dto.output.BuscarVouchersOutputDTO
import com.getnet.pooldevouchers.core.application.dto.output.DadosVoucherDTO
import com.getnet.pooldevouchers.core.application.dto.output.ResgateVoucherOutputDTO
import com.getnet.pooldevouchers.core.application.dto.output.VoucherOutputDTO
import com.getnet.pooldevouchers.core.application.interfaces.output.persistence.VoucherInterface
import mu.KLogger
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class VoucherImpl(
    private val voucherRepository: VoucherRepository,
    private val properties: VoucherProperties,
    private val logger: KLogger,
) : VoucherInterface {

    override fun existeVoucherDestinatarioOferta(voucherInput: VoucherInputDTO): Boolean {
        return voucherRepository.existsByEmailAndOfertaEspecial(voucherInput.email, voucherInput.ofertaEspecial).also {
            logger.info {
                "Verificacao se existe voucher para email=${voucherInput.email} " +
                    "e oferta=${voucherInput.ofertaEspecial}, com resultado=$it"
            }
        }
    }

    override fun criarVoucher(voucherInput: VoucherInputDTO): VoucherOutputDTO {
        logger.info { "Salvando voucher para destinatario de email=${voucherInput.email}" }

        voucherInput.toEntity().run {
            validade(properties.time)
            voucherRepository.save(this).let {
                return toVoucherOutputDTO().also {
                    logger.info { "Salvo voucher para destinatario de email=${it.email}" }
                }
            }
        }
    }

    override fun recuperarVoucherNaoUsado(input: ResgateVoucherInputDTO): ResgateVoucherOutputDTO {
        logger.info { "Recuperando voucher nao usado para codigo=${input.voucher}" }

        return voucherRepository.findByCodigoVoucherAndEmailAndUsadoIsFalse(input.voucher, input.email).orElseThrow {
            throw NotFoundException("Não encontrado voucher nao usado para codido=${input.voucher}", null)
        }.toResgateVoucherOutputDTO().also {
            logger.info { "Recuperado voucher nao usado para codido=${input.voucher}" }
        }
    }

    override fun atualizarVoucherComoUsado(voucher: UUID): ResgateVoucherOutputDTO {
        logger.info { "Atualizando voucher=$voucher para usado" }

        return voucherRepository.findById(voucher).get().run {
            voucherRepository.save(this.copy(dataUso = Instant.now(), usado = true))
        }.toResgateVoucherOutputDTO().also {
            logger.info { "Voucher=$voucher atualizando para usado" }
        }
    }

    override fun buscarVouchersUsuario(email: String): BuscarVouchersOutputDTO {
        logger.info { "Buscando vouchers validos para email=$email" }

        val vouchers = voucherRepository.findByEmailAndUsadoIsFalse(email)
            .filter { Instant.now() < it.dataValidade }

        if (vouchers.isEmpty()) {
            throw NotFoundException("Não encontrado vouchers validos para email=$email", null)
        }

        val listaDadosVoucher = mutableListOf<DadosVoucherDTO>()
        vouchers.forEach {
            DadosVoucherDTO(
                codigoVoucher = it.codigoVoucher,
                oferta = it.ofertaEspecial
            ).run {
                listaDadosVoucher.add(this)
            }
        }
        return BuscarVouchersOutputDTO(listaVouchers = listaDadosVoucher)
    }
}
