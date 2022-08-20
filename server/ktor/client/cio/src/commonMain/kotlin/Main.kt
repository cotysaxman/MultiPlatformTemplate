package com.exawizards.multiplatform_template.server.ktor.client.cio

import com.exawizards.multiplatform_template.server.ktor.configuration.client_utils.getClient
import io.ktor.client.engine.cio.*

val client = getClient(CIO)
