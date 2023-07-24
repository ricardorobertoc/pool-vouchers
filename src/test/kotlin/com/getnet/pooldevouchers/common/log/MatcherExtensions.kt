package com.getnet.pooldevouchers.common.log

import io.mockk.MockKMatcherScope

fun MockKMatcherScope.hasLogMessage(expectedMessage: String): () -> String = match {
    val actualMsg = it()
    val valid = actualMsg == expectedMessage
    if (!valid) {
        println("hasLogMessage just for debug: expected: '$expectedMessage'")
        println("hasLogMessage just for debug: actual: '$actualMsg'")
        println()
    }
    valid
}
