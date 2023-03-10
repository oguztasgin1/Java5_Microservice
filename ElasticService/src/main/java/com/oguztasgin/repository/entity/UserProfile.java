package com.oguztasgin.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "userprofile")
public class UserProfile extends BaseEntity{
    @Id
    String Id;
    Long userid;
    Long authid;
    String username;
    String email;
    String photo;
    String about;
    String phone;
    String age;
    String website;

}
