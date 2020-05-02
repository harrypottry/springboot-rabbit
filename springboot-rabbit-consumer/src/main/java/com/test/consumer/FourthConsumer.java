package com.test.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: harrypotter
 */
@Component
@PropertySource("classpath:test.properties")
@RabbitListener(queues = "${com.test.fourthqueue}", containerFactory="rabbitListenerContainerFactory")
public class FourthConsumer {
    @RabbitHandler
    public void process(String msg){
        System.out.println("Fourth Queue received msg : " + msg);
    }
}
