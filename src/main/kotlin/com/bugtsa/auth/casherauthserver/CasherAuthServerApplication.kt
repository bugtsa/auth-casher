package com.bugtsa.auth.casherauthserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class CasherAuthServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(CasherAuthServerApplication::class.java, *args)
}
