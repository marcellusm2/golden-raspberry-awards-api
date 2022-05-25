package com.texoit.movieawards.application.services

import com.texoit.movieawards.adapters.db.MovieRepository
import com.texoit.movieawards.domain.model.MinMaxProducerIntervals
import com.texoit.movieawards.domain.model.ProducerInterval
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class ProducerService(private val repository: MovieRepository) {

    companion object DataInitializer {
        private val log = LoggerFactory.getLogger(ProducerService::class.java)
    }

    fun listAll(): List<ProducerInterval> = repository.all()

    fun listWinners(): List<ProducerInterval> = repository.allWinners()

    fun getMinMaxAwardIntervals(): MinMaxProducerIntervals {
        log.debug("searching min max list of winners")
        val filtred = repository.intervals()
        log.debug("the search of list min max of winners has finish")
        return MinMaxProducerIntervals(
            min = filtred.groupBy { it.interval!! }.toSortedMap().entries.first().value,
            max = filtred.groupBy { it.interval!! }.toSortedMap().entries.last().value
        )
    }

    fun getMinAwardIntervals(): MinMaxProducerIntervals {
        log.debug("searching min list of winners")
        val filtred = repository.intervals()
        log.debug("the search of list min of winners has finish")
        return MinMaxProducerIntervals(
            min = filtred.groupBy { it.interval!! }.toSortedMap().entries.first().value,
            emptyList()
        )
    }

    fun getMaxAwardIntervals(): MinMaxProducerIntervals {
        log.debug("searching max list of winners")
        val filtred = repository.intervals()
        log.debug("the search of list max of winners has finish")
        return MinMaxProducerIntervals(
            emptyList(),
            max = filtred.groupBy { it.interval!! }.toSortedMap().entries.last().value
        )
    }
}