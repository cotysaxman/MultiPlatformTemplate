package com.exawizards.multiplatform_template.server.ktor.configuration.client_utils

import com.exawizards.multiplatform_template.server.ktor.configuration.Configuration.Routes
import com.exawizards.multiplatform_template.server.ktor.configuration.Http
import com.exawizards.multiplatform_template.server.ktor.configuration.ResponseType
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.HttpMethod
import io.ktor.util.reflect.*

suspend fun HttpClient.request(route: Routes) = request(route.fullPath) {
    method = mapMethod(route.method)
}.castResponse(route.responseType)

fun mapMethod(method: Http): HttpMethod = when (method) {
    Http.DELETE -> HttpMethod.Delete
    Http.GET -> HttpMethod.Get
    Http.HEAD -> HttpMethod.Head
    Http.OPTION -> HttpMethod.Options
    Http.PATCH -> HttpMethod.Patch
    Http.POST -> HttpMethod.Post
    Http.PUT -> HttpMethod.Put
}

private suspend fun HttpResponse.castResponse(responseType: ResponseType) = when (responseType) {
    ResponseType.TEXT -> body<String>()
}
