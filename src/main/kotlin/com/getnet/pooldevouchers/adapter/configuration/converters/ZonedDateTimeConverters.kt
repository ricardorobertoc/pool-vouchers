package com.getnet.pooldevouchers.adapter.configuration.converters

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Date

@Component
class ZonedDateTimeConverters {

    enum class ZonedDateTimeToDateConverter :
        Converter<ZonedDateTime, Date> {
        INSTANCE;

        override fun convert(source: ZonedDateTime): Date = Date.from(source.toInstant())
    }

    enum class DateToZonedDateTimeConverter : Converter<Date, ZonedDateTime> {
        INSTANCE;

        override fun convert(source: Date): ZonedDateTime = source.toInstant().atZone(ZoneOffset.UTC)
    }
}
