package com.getnet.pooldevouchers

import org.springframework.boot.actuate.autoconfigure.metrics.mongo.MongoMetricsAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import java.util.TimeZone

@EnableMongoRepositories
@ConfigurationPropertiesScan
@SpringBootApplication(exclude = [MongoMetricsAutoConfiguration::class])
class Application {
    companion object {
        fun setDefaultTimeZone() {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        }
    }
}

fun main(args: Array<String>) {
    Application.setDefaultTimeZone()
    @Suppress("SpreadOperator")
    runApplication<Application>(*args)
}
