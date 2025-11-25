package org.example.personal_init.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {
    @Bean("emailQueue")
    fun emailQueue(): Queue =
        QueueBuilder
            .durable("mail")
            .build()

    @Bean("smsQueue")
    fun smsQueue(): Queue =
        QueueBuilder
            .durable("sms")
            .build()

    @Bean
    fun messageConverter(): MessageConverter =
        JacksonJsonMessageConverter()

}