package com.texoit.movieawards.adapters.rest

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
    fun testAllProducers() {
        val rsp = invokeGet("/producers")

        assertEquals(HttpStatus.OK, rsp.status)
        assertNotNull(rsp.body())
    }

    @Test
    fun testWinnerProducers() {
        val rsp = invokeGet("/producers?winner=true")

        assertEquals(HttpStatus.OK, rsp.status)
        assertNotNull(rsp.body())
    }

    @Test
    fun testMinIntervalsWinnersProducers() {
        val rsp = invokeGet("/producers?winner=true&awardInterval=min")

        assertEquals(HttpStatus.OK, rsp.status)

        rsp.body()?.let {
            assertNotNull(it)
            assertTrue(it.toString()
                .contains(
                    "producer=Allan Carr, interval=4, previousWin=1980, followingWin=1984"
                )
            )
            assertTrue(it.toString()
                .contains(
                    "producer=Joel Silver, interval=4, previousWin=1989, followingWin=1993"
                )
            )
        }
    }

    @Test
    fun testMaxIntervalsWinnersProducers() {
        val rsp = invokeGet("/producers?winner=true&awardInterval=max")

        assertEquals(HttpStatus.OK, rsp.status)
        rsp.body()?.let {
            assertNotNull(it)
            assertTrue(it.toString()
                .contains(
                    "producer=Matthew Vaughn, interval=13, previousWin=2002, followingWin=2015"
                )
            )
        }
    }

    @Test
    fun testMinMaxIntervalsWinnersProducers() {
        val rsp = invokeGet("/producers?winner=true&awardInterval=both")

        println(rsp.body())
        assertEquals(HttpStatus.OK, rsp.status)
        rsp.body()?.let {
            assertNotNull(it)
            assertTrue(it.toString()
                .contains(
                    "producer=Allan Carr, interval=4, previousWin=1980, followingWin=1984"
                )
            )
            assertTrue(it.toString()
                .contains(
                    "producer=Joel Silver, interval=4, previousWin=1989, followingWin=1993"
                )
            )
            assertTrue(it.toString()
                .contains(
                    "producer=Matthew Vaughn, interval=13, previousWin=2002, followingWin=2015"
                )
            )
        }
    }

    private fun invokeGet(path: String): HttpResponse<MutableList<Any>> {
        val request: HttpRequest<Any> = HttpRequest.GET(path)
        return client.toBlocking().exchange(request,
            Argument.listOf(Any::class.java))
    }
}