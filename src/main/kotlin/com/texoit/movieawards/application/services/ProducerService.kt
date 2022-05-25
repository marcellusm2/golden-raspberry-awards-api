package com.texoit.movieawards.application.services

import com.texoit.movieawards.adapters.db.MovieRepository
import com.texoit.movieawards.domain.model.MinMaxAwardIntervals
import com.texoit.movieawards.domain.model.Movie
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import org.slf4j.LoggerFactory

@Singleton
class ProducerService(private val repository: MovieRepository) {

    companion object DataInitializer {
        private val log = LoggerFactory.getLogger(ProducerService::class.java)
    }

    fun listAll(): Flow<Movie> = repository.findAll()

    fun listWinners(): Flow<Movie> = repository.findWinners()

    fun getMinMaxAwardIntervals(): MinMaxAwardIntervals {
        log.info("searching min max list of winners")
        val filtred = repository.findIntervals()
        log.info("the search of list min max of winners has finish")
        return MinMaxAwardIntervals(
            min = filtred.groupBy { it.interval }.toSortedMap().entries.first().value,
            max = filtred.groupBy { it.interval }.toSortedMap().entries.last().value
        )
    }

    fun getMinAwardIntervals(): MinMaxAwardIntervals {
        log.info("searching min list of winners")
        val filtred = repository.findIntervals()
        log.info("the search of list min of winners has finish")
        return MinMaxAwardIntervals(
            min = filtred.groupBy { it.interval }.toSortedMap().entries.first().value,
            emptyList()
        )
    }

    fun getMaxAwardIntervals(): MinMaxAwardIntervals {
        log.info("searching max list of winners")
        val filtred = repository.findIntervals()
        log.info("the search of list max of winners has finish")
        return MinMaxAwardIntervals(
            emptyList(),
            max = filtred.groupBy { it.interval }.toSortedMap().entries.last().value
        )
    }
}