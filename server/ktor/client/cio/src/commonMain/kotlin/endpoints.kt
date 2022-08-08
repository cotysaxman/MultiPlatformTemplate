package com.exawizards.multiplatform_template.server.ktor.client.cio

import com.exawizards.multiplatform_template.server.ktor.configuration.*
import com.exawizards.multiplatform_template.server.ktor.configuration.Configuration.Routes
import io.ktor.http.HttpMethod
import io.ktor.client.request.*


private fun mapMethod(method: Http): HttpMethod = when (method) {
    Http.DELETE -> HttpMethod.Delete
    Http.GET -> HttpMethod.Get
    Http.HEAD -> HttpMethod.Head
    Http.OPTION -> HttpMethod.Options
    Http.PATCH -> HttpMethod.Patch
    Http.POST -> HttpMethod.Post
    Http.PUT -> HttpMethod.Put
}
