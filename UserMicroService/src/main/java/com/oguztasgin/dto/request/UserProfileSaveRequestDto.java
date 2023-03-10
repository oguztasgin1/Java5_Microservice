package com.oguztasgin.dto.request;

import com.oguztasgin.repository.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
