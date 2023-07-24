package com.getnet.pooldevouchers.common

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.common.Slf4jNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent

class CustomWireMock : ITestConfiguration {

    override fun configure(applicationContext: ConfigurableApplicationContext) {
        val wmServer = WireMockServer(
            WireMockConfiguration()
                .dynamicPort()
                .extensions(ResponseTemplateTransformer(true))
                .notifier(Slf4jNotifier(true))
        )

        wmServer.start()

        applicationContext.beanFactory.registerSingleton("wireMock", wmServer)

        applicationContext.addApplicationListener {
            if (it is ContextClosedEvent) {
                wmServer.stop()
            }
        }

        val httpClientUrls = mapOf(
            "client.some-client-example.host" to ""
        ).map { "${it.key}=http://localhost:${wmServer.port()}${it.value}" }

        TestPropertyValues.of(httpClientUrls).applyTo(applicationContext)
    }
}
