package com.texoit.movieawards.application.config

import com.texoit.movieawards.application.services.DataInitializerService
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import jakarta.inject.Singleton

@Singleton
class StartUpListener(private val service: DataInitializerService) {

    @EventListener
    fun onStartUp(e: ServerStartupEvent) {
        service.setup()
    }

}