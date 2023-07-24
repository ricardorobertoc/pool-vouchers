package com.getnet.pooldevouchers.adapter.configuration.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfiguration {
    @Bean
    fun customOpenAPI(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("Pool de Vouchers")
                .version("1.0.0")
                .description("API responsible for pool vouchers")
        )
}
