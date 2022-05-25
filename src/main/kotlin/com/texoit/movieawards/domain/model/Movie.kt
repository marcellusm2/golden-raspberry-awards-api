package com.texoit.movieawards.domain.model

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.*
import java.util.UUID

@Introspected
@MappedEntity
data class Movie (
    @field:Id
    @AutoPopulated
    var id: UUID?,
    var year: Int?,
    var title: String,
    @Relation(Relation.Kind.MANY_TO_ONE, cascade = [Relation.Cascade.ALL])
    var studio: Studio?,
    @Relation(Relation.Kind.MANY_TO_ONE, cascade = [Relation.Cascade.ALL])
    var producer: Producer?,
    var winner: Boolean?
) {
    constructor() : this(null, null, "", null, null, null)
}