package com.texoit.movieawards.adapters.rest

import com.texoit.movieawards.adapters.rest.filter.IntervalFilterEnum
import com.texoit.movieawards.adapters.rest.filter.ProducerFilterBean
import com.texoit.movieawards.application.services.ProducerService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.RequestBean

@Controller
class ProducerEndpoint(private val service: ProducerService) {

    @Get(uri = "/producers{?winner,awardInterval}", produces = [MediaType.APPLICATION_JSON])
    fun list(@RequestBean filter: ProducerFilterBean): HttpResponse<*> {
        return ok(listWithFilters(filter))
    }

    private fun listWithFilters(filter: ProducerFilterBean): Any {
        return when {
            filter.winner == true && IntervalFilterEnum.BOTH.value == filter.awardInterval -> service.getMinMaxAwardIntervals()
            filter.winner == true && IntervalFilterEnum.MIN.value  == filter.awardInterval -> service.getMinAwardIntervals()
            filter.winner == true && IntervalFilterEnum.MAX.value  == filter.awardInterval -> service.getMaxAwardIntervals()
            filter.winner == true -> service.listWinners()
            else -> service.listAll()
        }
    }

}