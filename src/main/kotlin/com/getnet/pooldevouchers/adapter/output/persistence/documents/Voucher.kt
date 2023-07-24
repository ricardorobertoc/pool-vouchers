package com.getnet.pooldevouchers.adapter.output.persistence.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

@Document(value = "Voucher")
@CompoundIndex(def = "{'id': 1}")
data class Voucher(
    @Id val id: UUID = UUID.randomUUID(),
    val nome: String,
    val email: String,
    val ofertaEspecial: String,
    val desconto: Int,
    val codigoVoucher: String = UUID.randomUUID().toString(),
    val codigoDestOferta: String = UUID.randomUUID().toString(),
    var dataValidade: Instant? = null,
    val dataUso: Instant? = null,
    val usado: Boolean = false
) {
    fun validade(time: Long) {
        dataValidade = Instant.now().plus(time, ChronoUnit.HOURS)
    }
}
