package com.getnet.pooldevouchers.adapter.output.persistence.repository

import com.getnet.pooldevouchers.adapter.output.persistence.documents.Voucher
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface VoucherRepository : MongoRepository<Voucher, UUID> {
    fun existsByEmailAndOfertaEspecial(email: String, ofertaEspecial: String): Boolean

    fun findByCodigoVoucherAndEmailAndUsadoIsFalse(codigoVoucher: String, email: String): Optional<Voucher>

    fun findByEmailAndUsadoIsFalse(email: String): List<Voucher> = emptyList()
}
