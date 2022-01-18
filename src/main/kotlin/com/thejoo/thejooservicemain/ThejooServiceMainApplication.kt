package com.thejoo.thejooservicemain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@ConfigurationPropertiesScan
@EnableJpaRepositories
@SpringBootApplication
class ThejooServiceMainApplication

fun main(args: Array<String>) {
	runApplication<ThejooServiceMainApplication>(*args)
}
