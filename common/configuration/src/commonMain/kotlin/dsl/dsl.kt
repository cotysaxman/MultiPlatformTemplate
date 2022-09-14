package com.exawizards.multiplatform_template.configuration.dsl

import com.exawizards.multiplatform_template.configuration.models.Model

interface RoutesDsl

@Suppress("UnusedReceiverParameter")
inline fun <reified INPUT : Model, reified OUTPUT : Model> RoutesDsl.post(
    path: String
) = object : Post<INPUT, OUTPUT> {
    override val path = path
}

@Suppress("UnusedReceiverParameter")
inline fun <reified OUTPUT : Model> RoutesDsl.get(
    path: String
) = object : Get<OUTPUT> {
    override val path = path
}
