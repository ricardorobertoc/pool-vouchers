package com.getnet.pooldevouchers.common

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

class CustomMongoContainer : MongoDBContainer(DockerImageName.parse("mongo:3.0.3")), ITestConfiguration {

    init { start() }

    override fun configure(applicationContext: ConfigurableApplicationContext) {
        TestPropertyValues.of("spring.data.mongodb.uri=${this.replicaSetUrl}").applyTo(applicationContext)
    }
}
