package com.getnet.pooldevouchers.common

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class IntegrationTestsInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val wireMock = CustomWireMock()
    private val mongoContainer = CustomMongoContainer()

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        wireMock.configure(applicationContext)
        mongoContainer.configure(applicationContext)
    }
}
