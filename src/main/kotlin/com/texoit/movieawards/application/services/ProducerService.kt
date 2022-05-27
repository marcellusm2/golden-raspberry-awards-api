package com.texoit.movieawards.application.services

import com.texoit.movieawards.adapters.db.MovieRepository
import com.texoit.movieawards.adapters.db.ProducerRepository
import com.texoit.movieawards.domain.model.MinMaxProducerIntervals
import com.texoit.movieawards.domain.model.Producer
import com.texoit.movieawards.domain.model.ProducerInterval
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import org.slf4j.LoggerFactory

@Singleton
class ProducerService(private val producerRrepository: ProducerRepository,
                      private val movieRepository: MovieRepository) {

    companion object DataInitializer {
        private val log = LoggerFactory.getLogger(ProducerService::class.java)
    }

    fun listAll(): List<ProducerInterval> = movieRepository.all()

    fun listWinners(): List<ProducerInterval> = movieRepository.allWinners()

    fun getMinMaxAwardIntervals(): MinMaxProducerIntervals? {
        log.debug("searching min max list of winners")
        val filtered = movieRepository.intervals().groupBy { it.interval!! }.toSortedMap().entries
        log.debug("the search of list min max of winners has finish")
        return when {
            filtered.isNotEmpty() -> MinMaxProducerIntervals(filtered.first().value, filtered.last().value)
            else -> null
        }
    }

    fun getMinAwardIntervals(): MinMaxProducerIntervals? {
        log.debug("searching min list of winners")
        val filtered = movieRepository.intervals().groupBy { it.interval!! }.toSortedMap().entries
        log.debug("the search of list min of winners has finish")
        return when {
            filtered.isNotEmpty() -> MinMaxProducerIntervals(filtered.first().value, emptyList())
            else -> null
        }
    }

    fun getMaxAwardIntervals(): MinMaxProducerIntervals? {
        log.debug("searching max list of winners")
        val filtered = movieRepository.intervals().groupBy { it.interval!! }.toSortedMap().entries
        log.debug("the search of list max of winners has finish")
        return when {
            filtered.isNotEmpty() -> MinMaxProducerIntervals(emptyList(), filtered.last().value)
            else -> null
        }
    }
}