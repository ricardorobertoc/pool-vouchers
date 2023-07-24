package com.getnet.pooldevouchers.adapter.configuration.authentication

import com.getnet.pooldevouchers.adapter.output.persistence.documents.authentication.User
import org.springframework.security.core.Authentication
fun Authentication.toUser(): User {
    return principal as User
}
