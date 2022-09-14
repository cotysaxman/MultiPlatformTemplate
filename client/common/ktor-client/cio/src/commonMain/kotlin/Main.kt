package com.exawizards.multiplatform_template.ktor_client.cio

import com.exawizards.multiplatform_template.ktor_client.dsl.getClient
import io.ktor.client.engine.cio.*

val client = getClient(CIO)
