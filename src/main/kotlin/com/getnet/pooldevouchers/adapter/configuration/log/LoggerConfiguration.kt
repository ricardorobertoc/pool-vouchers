package com.getnet.pooldevouchers.adapter.configuration.log

import mu.KotlinLogging
import org.springframework.beans.factory.InjectionPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class LoggerConfiguration {

    @Bean
    @Scope("prototype")
    fun createLogger(injectionPoint: InjectionPoint) = KotlinLogging.logger(injectionPoint.member.declaringClass.name)
}
