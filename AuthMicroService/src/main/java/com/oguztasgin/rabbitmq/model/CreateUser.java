package com.oguztasgin.rabbitmq.model;

import lombok.*;

import java.io.Serializable;

/**
 * Dikkat!!!!!
 * Burada gönderilecek veriler işlenmelidir. Ancak bu bilgilerin iletilebilmesi için
 * serileştirilebilir olması gerekiyor.
 *
 * 1- Serilestirme yapılmasi gerekiyor
 * 2- Gönderilen sinifi karşılacayak oaln sınıf için tanımlamalar paket adına kadar
 *  aynı olmalıdır.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateUser implements Serializable {
    Long authid;
    String username;
    String email;
}
