/*
 * Copyright 2014-2024 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package test.server.tests

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal fun Application.loggingTestServer() {
    routing {
        route("logging") {
            get {
                call.respondText("home page")
            }
            post {
                if ("Response data" != call.receiveText()) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    call.respondText("/", status = HttpStatusCode.Created)
                }
            }
            get("301") {
                call.respondRedirect("/logging")
            }

            route("non-utf") {
                post {
                    call.respondBytes(
                        bytes = byteArrayOf(-77, 111),
                        contentType = ContentType.parse("application/octet-stream"),
                        status = HttpStatusCode.Created
                    )
                }
            }
        }
    }
}
