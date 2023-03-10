package com.oguztasgin.rabbitmq.consumer;

import com.oguztasgin.mapper.IUserProfileMapper;
import com.oguztasgin.rabbitmq.model.CreateUser;

import com.oguztasgin.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserConsumer {
    private final UserProfileService userProfileService;
    /**
     * Bir serveris üsrekli olarak belirlenen kuyruklari dinlemeli
     */
    @RabbitListener(queues = "queue-auth-create-user")
    public void createUserConsumerListener(CreateUser createUser){
        System.out.println("Gelen Mesaj....: "+ createUser.toString());
        userProfileService.save(createUser);
    }
}
