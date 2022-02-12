package com.thejoo.thejooservicemain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@ConfigurationPropertiesScan
@EnableAsync
@EnableScheduling
@SpringBootApplication
class ThejooServiceMainApplication

fun main(args: Array<String>) {
    runApplication<ThejooServiceMainApplication>(*args)
}
