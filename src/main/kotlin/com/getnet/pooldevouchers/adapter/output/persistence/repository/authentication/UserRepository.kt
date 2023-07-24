package com.getnet.pooldevouchers.adapter.output.persistence.repository.authentication

import com.getnet.pooldevouchers.adapter.output.persistence.documents.authentication.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : MongoRepository<User, UUID> {
    fun findByName(name: String): User?
    fun existsByName(name: String): Boolean
}
