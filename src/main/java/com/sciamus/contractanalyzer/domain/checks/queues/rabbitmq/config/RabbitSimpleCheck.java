package com.sciamus.contractanalyzer.domain.checks.queues.rabbitmq.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class RabbitSimpleCheck {

    private  RabbitTemplate rabbitTemplate;

    public RabbitSimpleCheck(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/rabbit-simple")
    public void sendToQueue() {

        rabbitTemplate.convertAndSend("queue","Bążur!");


    }
    @RabbitListener(queues = "queue")
    public void listen(String message) {
        final Logger logger = LogManager.getLogger(RabbitSimpleCheck.class);
        logger.info("Received: " + message);

    }



}
