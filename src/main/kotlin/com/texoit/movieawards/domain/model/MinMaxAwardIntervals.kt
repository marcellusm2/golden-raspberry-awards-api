package com.texoit.movieawards.domain.model

data class MinMaxAwardIntervals(
    val min: List<AwardInterval>,
    val max: List<AwardInterval>,
)