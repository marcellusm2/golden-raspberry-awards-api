package com.texoit.movieawards.adapters.rest

import com.texoit.movieawards.domain.model.MinMaxAwardIntervals
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@MicronautTest
class ProducerEndpointTest {

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun testOneWinnerProducer() {
        val rsp = invoke()
        assertEquals(HttpStatus.OK, rsp.status)
        assertNotNull(rsp.body())
    }

    private fun invoke(): HttpResponse<MutableList<MinMaxAwardIntervals>> {
        val request: HttpRequest<Any> = HttpRequest.GET("/producers")
        return client.toBlocking().exchange(request,
            Argument.listOf(MinMaxAwardIntervals::class.java))
    }
}