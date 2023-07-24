package com.getnet.pooldevouchers.core.application.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "voucher-properties")
data class VoucherProperties(
    val time: Long
)
