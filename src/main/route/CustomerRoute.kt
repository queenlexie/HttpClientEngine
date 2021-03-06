package com.example.main.route

import com.example.main.model.Customer
import com.example.main.model.Response
import com.example.main.repository.CustomerRepo
import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.application.*
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.customer(customerRepo: CustomerRepo) {
    route("/customer") {

        // http://127.0.0.1:8080/customer
        get {
            call.respond(HttpStatusCode.OK, customerRepo.customerList)
        }

        // http://127.0.0.1:8080/customer/1
        get("/{customerId}") {
            val customerId = call.parameters["customerId"]?.toInt()
            val requestCustomer = customerRepo.customerList.firstOrNull { it.id == customerId }
            if (requestCustomer != null) {
                call.respond(HttpStatusCode.OK, requestCustomer)
            } else {
                call.respond(HttpStatusCode.NotFound,
                    Response("Record Not Found")
                )
            }
        }

        // http://127.0.0.1:8080/customer/create
        post("/create") {
            val customer = call.receive<Customer>()
            customerRepo.customerList.add(customer)
            call.respond(HttpStatusCode.OK, Response("Record Created"))
        }

        // http://127.0.0.1:8080/customer/delete/1
        delete("/delete/{customerId}") {
            val customerId = call.parameters["customerId"]?.toInt()
            val isRemoved = customerRepo.customerList.removeIf { it.id == customerId }
            if (isRemoved) {
                call.respond(Response("Record Deleted"))
            } else {
                call.respond(Response("No Such Customer Found"))
            }
        }

        // http://127.0.0.1:8080/customer/update/1
        put("/update/{customerId}") {
            val customerId = call.parameters["customerId"]?.toInt()
            val customerModel = call.receive<Customer>()
            var isUpdated = false
            customerRepo.customerList.mapIndexed { index, customer ->
                if (customer.id == customerId) {
                    customerRepo.customerList[index] = customerModel
                    isUpdated = true
                }
            }
            if (isUpdated) {
                call.respond(Response("Record Updated"))
            } else {
                call.respond(Response("No Such Customer Found"))
            }
        }
    }
}