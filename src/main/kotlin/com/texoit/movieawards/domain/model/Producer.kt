package com.texoit.movieawards.domain.model

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.*
import java.util.*

@Introspected
@MappedEntity
data class Producer (
    @field:Id
    @AutoPopulated
    var id: UUID?,
    val name: String
) {
    constructor(name: String) : this(null, name)
}