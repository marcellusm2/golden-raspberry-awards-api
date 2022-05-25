package com.texoit.movieawards.application.services

import com.texoit.movieawards.adapters.db.MovieRepository
import com.texoit.movieawards.adapters.db.ProducerRepository
import com.texoit.movieawards.adapters.db.StudioRepository
import com.texoit.movieawards.adapters.fs.DataLoader
import com.texoit.movieawards.domain.model.Movie
import com.texoit.movieawards.domain.model.Producer
import com.texoit.movieawards.domain.model.Studio
import io.micronaut.context.annotation.Property
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

@Singleton
class DataInitializerService(@Property(name = "app.adapters.fs.movie-list")
                      private val file: String,
                             private val csvDataLoader: DataLoader,
                             private val movieRepository: MovieRepository,
                             private val producerRepository: ProducerRepository,
                             private val studioRepository: StudioRepository) {

    companion object DataInitializer {
        private val log = LoggerFactory.getLogger(DataInitializerService::class.java)
    }

    fun setup() {
        log.info("starting data initialization")
        runBlocking {
            csvDataLoader.load(file)
                .map { line -> movieRepository.save(getMovie(line)) }
        }
        log.info("data initialization finished")
    }

    private fun getMovie(movieData: Array<String>): Movie {
        return Movie().apply {
            year = movieData[0].toInt()
            title = movieData[1]
            studio = getStudio(movieData)
            producer = getProducer(movieData)
            winner = movieData[4] == "yes"
        }
    }

    private fun getProducer(movieData: Array<String>) =
        producerRepository.findByName(movieData[3]) ?: Producer(movieData[3])

    private fun getStudio(movieData: Array<String>) =
        studioRepository.findByName(movieData[2]) ?: Studio(movieData[2])

}