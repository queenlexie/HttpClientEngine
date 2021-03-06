package com.example.main.helloworld.route

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.helloWorld() {
    get("/") {
        call.respondText("Hello World!", ContentType.Text.Plain)
    }
}