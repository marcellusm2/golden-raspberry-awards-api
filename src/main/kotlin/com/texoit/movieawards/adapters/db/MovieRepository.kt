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

    @Query("""SELECT producer_.id,
                     producer_.name AS producer,
                     null AS `interval`,
                     null AS previous_win,
                     null AS following_win
                FROM movie_producer movie_producer_
                JOIN movie movie_ ON movie_.id = movie_producer_.movie_id
                JOIN producer producer_ ON producer_.id = movie_producer_.producer_id""")
    fun all(): List<ProducerInterval>

    @Query("""SELECT producer_.id,
                     producer_.name AS producer,
                     null AS `interval`,
                     null AS previous_win,
                     null AS following_win
                FROM movie_producer movie_producer_
                JOIN movie movie_ ON movie_.id = movie_producer_.movie_id
                JOIN producer producer_ ON producer_.id = movie_producer_.producer_id
               WHERE movie_.winner IS true""")
    fun allWinners(): List<ProducerInterval>

    @Query("""SELECT '' AS id,
                     winner_producer.name AS producer,
                     previous.year AS previous_win,
                     following.year AS following_win,
                     following.year - previous.year AS `interval`
                FROM (
                        SELECT producer_.id AS producer_id, producer_.name
                          FROM movie_producer movie_producer_
                          JOIN movie movie_ ON movie_.id = movie_producer_.movie_id
                          JOIN producer producer_ ON producer_.id = movie_producer_.producer_id
                         WHERE movie_.winner IS true
                      GROUP BY producer_.id
                        HAVING COUNT(producer_.id) > 1
                ) AS winner_producer
                JOIN (
                        SELECT movie_producer_.producer_id, MIN(movie_.year) AS year
                          FROM movie_producer movie_producer_
                          JOIN movie movie_ ON movie_.id = movie_producer_.movie_id
                         WHERE movie_.winner IS true
                      GROUP BY movie_producer_.producer_id
                ) AS previous ON previous.producer_id = winner_producer.producer_id
                JOIN (
                        SELECT movie_producer_.producer_id, MAX(movie_.year) AS year
                          FROM movie_producer movie_producer_
                          JOIN movie movie_ ON movie_.id = movie_producer_.movie_id
                         WHERE movie_.winner IS true
                      GROUP BY movie_producer_.producer_id
                ) AS following ON following.producer_id = winner_producer.producer_id""")
    fun intervals(): List<ProducerInterval>
}