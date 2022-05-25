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
    fun list(@RequestBean bean: ProducerFilterBean): HttpResponse<*> {
        return ok(listWithFilters(bean))
    }

    private fun listWithFilters(bean: ProducerFilterBean): Any {
        return when {
            bean.winner == true && IntervalFilterEnum.BOTH.value == bean.awardInterval -> service.getMinMaxAwardIntervals()
            bean.winner == true && IntervalFilterEnum.MIN.value  == bean.awardInterval -> service.getMinAwardIntervals()
            bean.winner == true && IntervalFilterEnum.MAX.value  == bean.awardInterval -> service.getMaxAwardIntervals()
            bean.winner == true -> service.listWinners()
            else -> service.listAll()
        }
    }

}