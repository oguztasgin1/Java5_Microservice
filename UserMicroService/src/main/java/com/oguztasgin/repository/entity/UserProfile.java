package com.oguztasgin.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tbluser_profile")
public class UserProfile extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    /**
     * Kullanıcı kayıt olurken oluşan ve user,pass bilgileri tutulan
     * kayda ait eşleştirmede kullanılacaktır.
     */
    Long authid;
    String username;
    String email;
    String photo;
    String about;
    String phone;
    String age;
    String website;


}
