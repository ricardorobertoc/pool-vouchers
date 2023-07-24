package com.getnet.pooldevouchers

import com.fasterxml.jackson.databind.ObjectMapper
import com.getnet.pooldevouchers.common.IntegrationTests
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus

internal class ApplicationIT(
    @Autowired private val env: Environment,
    @Autowired private val objectMapper: ObjectMapper
) : IntegrationTests() {

    @Test
    fun `application should be healthy`() {
        val url = "http://localhost:${env.getProperty("local.management.port")}/health"
        val response = TestRestTemplate().getForEntity(url, String::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("UP", objectMapper.readTree(response.body).get("status").textValue())
    }
}
