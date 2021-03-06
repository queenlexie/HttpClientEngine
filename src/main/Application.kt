package com.example.main

import com.example.main.repository.CustomerRepo
import com.example.main.route.customer
import com.example.main.route.helloWorld
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            //gson()
            gson {
                setPrettyPrinting()
                disableHtmlEscaping()
            }
            //register(ContentType.Application.Json, CustomJsonConverter())
        }
        routing {
            helloWorld()
            customer(customerRepo = CustomerRepo())
        }
    }
    server.start(wait = true)
}