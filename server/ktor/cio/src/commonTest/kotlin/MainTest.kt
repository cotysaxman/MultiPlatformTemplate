package com.exawizards.multiplatform_template.server.ktor.cio

import com.exawizards.multiplatform_template.server.ktor.cio.plugins.mainModule
import kotlin.test.Test
import kotlin.test.assertEquals
import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.testApplication

class MainTest {
    @Test
    fun testRoot() = testApplication {
        application {
            mainModule()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World from JVM!", bodyAsText())
        }
    }
}