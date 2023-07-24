package com.getnet.pooldevouchers.adapter.configuration.database

import com.getnet.pooldevouchers.adapter.configuration.converters.ZonedDateTimeConverters
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@Configuration
class MongoConfiguration {

    @Bean
    fun customConversions(): MongoCustomConversions = MongoCustomConversions(
        listOf<Converter<*, *>?>(
            ZonedDateTimeConverters.ZonedDateTimeToDateConverter.INSTANCE,
            ZonedDateTimeConverters.DateToZonedDateTimeConverter.INSTANCE
        )
    )
}
