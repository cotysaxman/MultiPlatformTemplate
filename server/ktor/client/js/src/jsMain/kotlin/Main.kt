package com.exawizards.multiplatform_template.server.ktor.client.js

import com.exawizards.multiplatform_template.server.ktor.configuration.Configuration.Routes
import com.exawizards.multiplatform_template.server.ktor.configuration.client_utils.request
import io.ktor.client.*
import io.ktor.client.engine.js.*

object Client {
    private val instance = HttpClient(Js)

    suspend fun from(route: Routes) = instance.request(route)
}
