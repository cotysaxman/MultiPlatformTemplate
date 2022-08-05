package com.exawizards.multiplatform_template.server.ktor.cio

import com.exawizards.multiplatform_template.server.ktor.cio.plugins.configureRouting
import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*

class MainTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World from Native!", bodyAsText())
        }
    }
}
