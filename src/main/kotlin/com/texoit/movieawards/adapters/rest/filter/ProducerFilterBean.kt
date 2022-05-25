package com.texoit.movieawards.adapters.rest.filter

import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpRequest
import io.micronaut.http.annotation.QueryValue

@Introspected
data class ProducerFilterBean(
    val httpRequest: HttpRequest<Any>,
    @field:QueryValue @field:Nullable
    val winner: Boolean?,
    @field:QueryValue @field:Nullable
    val awardInterval: String?
)
