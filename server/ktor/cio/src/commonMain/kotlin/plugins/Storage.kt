package com.exawizards.multiplatform_template.server.ktor.cio.plugins

class Storage {
    var memoryStorage = listOf(
        "default first entry",
        "and a second",
    ).map(::Record)
        private set

    fun add(item: String) {
        memoryStorage = memoryStorage + Record(item)
    }
}

data class Record(val content: String)
