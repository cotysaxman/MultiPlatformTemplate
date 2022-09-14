package com.exawizards.multiplatform_template.configuration.dsl

import com.exawizards.multiplatform_template.configuration.models.Model

interface HttpRequest<T, S> {
    val path: String
}
interface Get<T : Model> : HttpRequest<Unit, T>
interface Post<T : Model, S: Model> : HttpRequest<T, S>
