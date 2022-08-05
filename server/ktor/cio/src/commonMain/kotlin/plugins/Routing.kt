package com.exawizards.multiplatform_template.server.ktor.cio.plugins

val routes = listOf(
    Route("/", HttpVerb.GET, HttpResponse.TEXT("Hello World!"))
)

enum class HttpVerb { GET }
sealed class HttpResponse {
    class TEXT(val text: String) : HttpResponse()
}
data class Route(val path: String, val verb: HttpVerb, val response: HttpResponse)
