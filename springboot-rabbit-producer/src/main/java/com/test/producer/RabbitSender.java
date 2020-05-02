package com.test.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.entity.Merchant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: harrypotter
 */
@Component
@PropertySource("classpath:test.properties")
public class RabbitSender {

    @Value("${com.test.directexchange}")
    private String directExchange;

    @Value("${com.test.topicexchange}")
    private String topicExchange;

    @Value("${com.test.fanoutexchange}")
    private String fanoutExchange;

    @Value("${com.test.directroutingkey}")
    private String directRoutingKey;

    @Value("${com.test.topicroutingkey1}")
    private String topicRoutingKey1;

    @Value("${com.test.topicroutingkey2}")
    private String topicRoutingKey2;


    // 自定义的模板，所有的消息都会转换成JSON发送
    @Autowired
    AmqpTemplate testTemplate;

    public void send() throws JsonProcessingException {
        Merchant merchant =  new Merchant(1001,"a direct msg : 中原镖局","汉中省解放路266号");
        testTemplate.convertAndSend(directExchange,directRoutingKey, merchant);

        testTemplate.convertAndSend(topicExchange,topicRoutingKey1,"a topic msg : shanghai.test.teacher");
        testTemplate.convertAndSend(topicExchange,topicRoutingKey2,"a topic msg : changsha.test.student");

        // 发送JSON字符串
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(merchant);
        System.out.println(json);
        testTemplate.convertAndSend(fanoutExchange,"", json);
    }


}
