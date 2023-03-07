package com.oguztasgin.rabbitmq.producer;

import com.oguztasgin.rabbitmq.model.CreateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserProducer {
    /**
     *  Nasıl ki posta gondermek icin belli kosullar gerekli
     *  (posta pulu, gonderen, alıcı)
     *
     */
    private final RabbitTemplate rabbitTemplate;

    public void createSendMessage(CreateUser createUser){
        rabbitTemplate.convertAndSend("exchange-direct-auth", "key-auth",
                createUser);
    }
}
