package com.getnet.pooldevouchers.core.application.authentication.service

import com.getnet.pooldevouchers.adapter.output.persistence.documents.authentication.User
import com.getnet.pooldevouchers.adapter.output.persistence.repository.authentication.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepo: UserRepository,
) {
    fun findById(id: UUID): User? {
        return userRepo.findByIdOrNull(id)
    }

    fun findByName(name: String): User? {
        return userRepo.findByName(name)
    }

    fun existsByName(name: String): Boolean {
        return userRepo.existsByName(name)
    }

    fun save(user: User): User {
        return userRepo.save(user)
    }
}
