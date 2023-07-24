package com.getnet.pooldevouchers.common

import org.springframework.context.ConfigurableApplicationContext

interface ITestConfiguration {
    fun configure(applicationContext: ConfigurableApplicationContext)
}
