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
class DataInitializerService(@Property(name = "app.adapters.fs.movielist")
                      private val file: String,
                             private val csvDataLoader: DataLoader,
                             private val movieRepository: MovieRepository,
                             private val producerRepository: ProducerRepository,
                             private val studioRepository: StudioRepository) {

    companion object DataInitializer {
        private val log = LoggerFactory.getLogger(DataInitializerService::class.java)
        private val separator = Regex(", | and ")
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
            studios = getStudios(movieData[2])
            producers = getProducers(movieData[3])
            winner = movieData[4] == "yes"
        }
    }

    private fun getStudios(rawStudio: String): List<Studio> =
        rawStudio.split(separator).map { studio -> studioRepository.findByName(studio) ?: Studio(studio) }

    private fun getProducers(rawProducer: String): List<Producer> =
        rawProducer.split(separator).map { producer -> producerRepository.findByName(producer) ?: Producer(producer) }
}