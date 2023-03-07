package com.oguztasgin.config.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    /**
     * Oluşturacağımız kuyruk icin gerekli olan islemleendirmeleri ayarlıyoruz.
     */

    private String exchangeDirectAuth = "exchange-direct-auth";
    private String keyAuth = "key-auth";
    private String queueAuthCreateUser = "queue-auth-create-user";

    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(exchangeDirectAuth);
    }

    @Bean
    Queue createAuthUserQueue(){
        return new Queue(queueAuthCreateUser);
    }

    @Bean
    public Binding bindingCreateAuthUser(final Queue createAuthUserQueue,
                                         final DirectExchange directExchange){
        return BindingBuilder.bind(createAuthUserQueue).to(directExchange).with(keyAuth);
    }
}
