package com.getnet.pooldevouchers.common

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers
@SuppressWarnings("UnnecessaryAbstractClass")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = [IntegrationTestsInitializer::class])
abstract class IntegrationTests
