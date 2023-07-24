package com.getnet.pooldevouchers.adapter.output.persistence.documents.authentication

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(value = "User")
@CompoundIndex(def = "{'id': 1}")
data class User(
    @Id val id: UUID = UUID.randomUUID(),
    val name: String,
    val password: String,
)
