package pl.seed.testcontainers

import org.testcontainers.containers.MongoDBContainer

object MongoContainerSingleton {
    val instance: MongoDBContainer by lazy { startMongoContainer() }

    private fun startMongoContainer(): MongoDBContainer =
        MongoDBContainer("mongo:4.4.2")
            .withReuse(true) // remember to put testcontainers.reuse.enable=true into ~/.testcontainers.properties
            .apply { start() }
}
