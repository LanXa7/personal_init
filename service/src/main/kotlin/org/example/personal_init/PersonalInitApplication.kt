package org.example.personal_init

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PersonalInitApplication

fun main(args: Array<String>) {
    runApplication<PersonalInitApplication>(*args)
}
