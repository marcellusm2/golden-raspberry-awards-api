package com.texoit.movieawards.domain.model

import io.micronaut.core.annotation.Introspected

@Introspected
data class ProducerInterval(
    val id: String,
    val producer: String,
    val interval: Int?,
    val previousWin: Int?,
    val followingWin: Int?
)