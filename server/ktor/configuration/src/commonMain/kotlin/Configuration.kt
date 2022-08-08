package com.exawizards.multiplatform_template.server.ktor.configuration

object Configuration {
    const val port: Int = 8081
    const val clientPort: Int = 8080
    const val host: String = "0.0.0.0"

    enum class Routes(val path: String, val method: Http, val responseType: ResponseType) {
        Root("/", Http.GET, ResponseType.TEXT);

        val fullPath = "http://${host}:${port}$path"

        companion object {
            val all = Routes.values()
        }
    }

    const val maxConnectionsCount = 1000
    const val maxConnectionsPerRoute = 100
    const val pipelineMaxSize = 20
    const val keepAliveTime = 5000
    const val connectTimeout = 5000
    const val connectAttempts = 5
}
