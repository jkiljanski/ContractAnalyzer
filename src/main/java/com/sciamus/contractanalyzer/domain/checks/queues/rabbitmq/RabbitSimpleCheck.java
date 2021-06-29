package com.sciamus.contractanalyzer.domain.checks.queues.rabbitmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitSimpleCheck {

    @Autowired
    private  RabbitTemplate rabbitTemplate;

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
