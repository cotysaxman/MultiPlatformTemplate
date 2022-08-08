package com.exawizards.multiplatform_template.server.ktor.client.cio

import io.ktor.client.*
import io.ktor.client.engine.cio.*

object Client {
    val instance = HttpClient(CIO)
}
