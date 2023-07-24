package com.getnet.pooldevouchers.adapter.input.rest.controller

import com.getnet.pooldevouchers.adapter.input.rest.controller.request.LoginDto
import com.getnet.pooldevouchers.adapter.input.rest.controller.request.RegisterDto
import com.getnet.pooldevouchers.adapter.input.rest.controller.response.LoginResponseDto
import com.getnet.pooldevouchers.adapter.input.rest.exception.BadRequestException
import com.getnet.pooldevouchers.adapter.input.rest.exception.error.ResponseError
import com.getnet.pooldevouchers.adapter.output.persistence.documents.authentication.User
import com.getnet.pooldevouchers.core.application.authentication.service.HashService
import com.getnet.pooldevouchers.core.application.authentication.service.TokenService
import com.getnet.pooldevouchers.core.application.authentication.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/pool-de-voucher/auth")
@SuppressWarnings("MagicNumber")
class AuthController(
    private val hashService: HashService,
    private val tokenService: TokenService,
    private val userService: UserService,
) {
    @PostMapping("/login")
    @Operation(
        tags = ["Auth"],
        summary = "Logar no sistema",
        description = "Logar no sistema Pool de Voucher",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Success Request",
                content = [Content(schema = Schema(implementation = LoginResponseDto::class))]
            ),
            ApiResponse(
                responseCode = "400", description = "Bad Request",
                content = [Content(schema = Schema(implementation = ResponseError::class))]
            )
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody payload: LoginDto): ResponseEntity<LoginResponseDto> {
        val user = userService.findByName(payload.name) ?: throw BadRequestException("Falha ao autenticar", null)

        if (!hashService.checkBcrypt(payload.password, user.password)) {
            throw BadRequestException("Falha ao autenticar", null)
        }
        return ResponseEntity.ok().body(LoginResponseDto(token = tokenService.createToken(user)))
    }

    @PostMapping("/register")
    @Operation(
        tags = ["Auth"],
        summary = "Registrar usuario",
        description = "Registrar usuario no sistema Pool de Voucher",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Success Request",
                content = [Content(schema = Schema(implementation = LoginResponseDto::class))]
            ),
            ApiResponse(
                responseCode = "400", description = "Bad Request",
                content = [Content(schema = Schema(implementation = ResponseError::class))]
            )
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    fun register(@RequestBody payload: RegisterDto): ResponseEntity<LoginResponseDto> {
        if (userService.existsByName(payload.name)) {
            throw BadRequestException("O nome de ussuario ja existe", null)
        }
        val user = User(
            name = payload.name,
            password = hashService.hashBcrypt(payload.password),
        )
        val savedUser = userService.save(user)
        return ResponseEntity.ok().body(LoginResponseDto(token = tokenService.createToken(savedUser),))
    }
}
