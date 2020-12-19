package pl.seed.testcontainers

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import kotlin.annotation.AnnotationTarget.CLASS

/**
 * Ensures a test instance of MongoDB is running.
 *
 * Does not work when added to a base (abstract) class. For such case,
 * directly apply the combination of `@SpringBootTest` and `@ContextConfiguration`.
 */
@Target(CLASS)
@SpringBootTest
@ContextConfiguration(initializers = [MongoInitializer::class])
annotation class MongoSpringBootTest
