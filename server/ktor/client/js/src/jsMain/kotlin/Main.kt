package com.exawizards.multiplatform_template.server.ktor.client.js

import com.exawizards.multiplatform_template.server.ktor.configuration.client_utils.getClient
import io.ktor.client.engine.js.*

val client = getClient(Js)
