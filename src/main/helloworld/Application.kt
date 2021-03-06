package com.example.main.helloworld

import com.example.main.helloworld.repository.CustomerRepo
import com.example.main.helloworld.route.customer
import com.example.main.helloworld.route.helloWorld
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