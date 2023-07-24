package com.getnet.pooldevouchers.adapter.configuration.log

import ch.qos.logback.classic.spi.ILoggingEvent
import com.fasterxml.jackson.core.JsonGenerator
import net.logstash.logback.composite.AbstractFieldJsonProvider
import java.util.regex.Pattern

@Suppress("unused")
class MessageAttributeProvider : AbstractFieldJsonProvider<ILoggingEvent>() {

    companion object {
        private val pattern = Pattern.compile("([\\w\\-]+)=([^\\s!,)?(]+)")
    }

    override fun writeTo(generator: JsonGenerator, event: ILoggingEvent) {
        val matcher = pattern.matcher(event.message)
        while (matcher.find()) {
            generator.writeFieldName(matcher.group(1))
            generator.writeString(matcher.group(2))
        }
    }
}
