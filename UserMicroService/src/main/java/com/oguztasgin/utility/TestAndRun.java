package com.oguztasgin.utility;

import com.oguztasgin.manager.IElasticServiceManager;
import com.oguztasgin.mapper.IUserProfileMapper;
import com.oguztasgin.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestAndRun {

    private final UserProfileService userProfileService;
    private final IElasticServiceManager elasticServiceManager;
    /**
     * Bir sınıftan nesne olusturulken çalışmasını istedigimiz kodlar counstructer a yazılır.
     */

    /**     Bu sınıftan bir nesne yaratırken çalışmasını istedigim kodları yazacagım.
     //     Ancak, bu sınıftan bir constructor gibi hareket etmesi için
     //     @PostConstruct anatasyonu ile işaretlenmesi gereklidir.
     */

    @PostConstruct
    public void init(){
        new Thread(()->{
            //run(); // Surekli aynı dataları kaydetmesin diye
        }).start();

    }

    /**
     * Buraya çalıştırmak istedigim kodları yazacagım.
     * Ama kafamıza gore @PostConstruct yazarsak construcktor da olanları beklememiz gerekecek.
     *
     * Önce kod çalışmalı sonra ayağa kalsın dersen thread kullanma ama uygulama yağa kalksın o arkada çalışsın dersen
     * thred kullan.
     */

    public void run(){
        /**
         * Tum kullanıcı datalarını çekip kullanıyoruz sonra FeignClient ile bu kullanıcı
         * bilgilerini elasticservice gonderiyoruz. Burada gondermek istedigimiz bilgi Dto
         * şeklinde olduğu için UserProfile da Dto ya Mapper ile
         * dönüşüm yapmamız gerekiyor.
         */
        try{
            userProfileService.findAll().forEach(x->{
                elasticServiceManager.save(IUserProfileMapper.INSTANCE.toUserProfileSaveRequestDto(x));
            });
            System.out.println("İşlem sona erdi");
        }catch (Exception exception){
            System.out.println(exception.toString());
        }
    }
}
