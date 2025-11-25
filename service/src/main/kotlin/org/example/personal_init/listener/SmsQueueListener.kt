package org.example.personal_init.listener

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
@RabbitListener(queues = ["sms"])
class SmsQueueListener {
}