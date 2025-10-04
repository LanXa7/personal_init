package org.example.personal_init

import org.babyfish.jimmer.client.EnableImplicitApi
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableImplicitApi
@SpringBootApplication
class PersonalInitApplication

fun main(args: Array<String>) {
    runApplication<PersonalInitApplication>(*args) {
        setDefaultProperties(mapOf("kotlin.version" to KotlinVersion.CURRENT.toString()))
    }
}
