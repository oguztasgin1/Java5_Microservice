package com.oguztasgin.dto.request;

import com.oguztasgin.repository.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class UserProfileSaveRequestDto extends BaseEntity {
    Long id;
    Long authid;
    String username;
    String email;
    String photo;
    String about;
    String phone;
    String age;
    String website;
}
