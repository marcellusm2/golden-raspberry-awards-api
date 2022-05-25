package com.texoit.movieawards.adapters.db

import com.texoit.movieawards.domain.model.ProducerInterval
import com.texoit.movieawards.domain.model.Movie
import io.micronaut.data.annotation.Join
import io.micronaut.data.annotation.Query
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.jpa.kotlin.CoroutineJpaSpecificationExecutor
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

@R2dbcRepository(dialect = Dialect.H2)
interface MovieRepository: CoroutineCrudRepository<Movie, UUID>, CoroutineJpaSpecificationExecutor<Movie> {

    @Query("""SELECT movie_.`producer_id` AS id,
                     movie_producer_.`name` AS producer,
                     null AS `interval`,
                     null AS previous_win,
                     null AS following_win
                FROM `movie` movie_
           LEFT JOIN `producer` movie_producer_ ON movie_.`producer_id`=movie_producer_.`id`""")
    @Join(value = "producer", type = Join.Type.LEFT_FETCH)
    fun all(): List<ProducerInterval>

    @Query("""SELECT movie_.`producer_id` AS id,
                     movie_producer_.`name` AS producer,
                     null AS `interval`,
                     null AS previous_win,
                     null AS following_win
                FROM `movie` movie_
           LEFT JOIN `producer` movie_producer_ ON movie_.`producer_id`=movie_producer_.`id`
               WHERE movie_.`winner` IS true""")
    @Join(value = "producer", type = Join.Type.LEFT_FETCH)
    fun allWinners(): List<ProducerInterval>

    @Query("""SELECT '' AS id,
                     winner_producer.`name` AS producer,
                     previous.`year` AS previous_win,
                     following.`year` AS following_win,
                     following.`year` - previous.`year` AS `interval`
                FROM (
                        SELECT movie_.`producer_id`, producer_.`name`
                          FROM `movie` movie_, `producer` producer_
                         WHERE movie_.`producer_id` = producer_.`id`
                           AND movie_.`winner` IS true
                      GROUP BY movie_.`producer_id`
                        HAVING COUNT(movie_.`producer_id`) > 1
                ) as winner_producer
                LEFT JOIN (
                        SELECT movie_.`producer_id`, MIN(movie_.`year`) as year
                          FROM `movie` movie_
                         WHERE movie_.`winner` IS true
                      GROUP BY movie_.`producer_id`
                ) as previous ON previous.`producer_id` = winner_producer.`producer_id`
                LEFT JOIN (
                        SELECT movie_.`producer_id`, MAX(movie_.`year`) as year
                          FROM `movie` movie_
                         WHERE movie_.`winner` IS true
                      GROUP BY movie_.`producer_id`
                ) as following ON following.`producer_id` = winner_producer.`producer_id`""")
    @Join(value = "producer", type = Join.Type.LEFT_FETCH)
    fun intervals(): List<ProducerInterval>
}