package com.getnet.pooldevouchers.common

import io.mockk.clearAllMocks
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

@SuppressWarnings("UnnecessaryAbstractClass")
@ExtendWith(MockKExtension::class)
abstract class UnitTest {
    @BeforeEach
    fun clearMocks() {
        clearAllMocks()
    }
}
