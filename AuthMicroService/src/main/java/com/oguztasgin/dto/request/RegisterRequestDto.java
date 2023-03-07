package com.oguztasgin.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterRequestDto {
    @NotNull(message = "Kullanıcı adı girilmesi zorunludur.")
    @Size(min = 3,max = 18)
    String username;
    @NotNull(message = "Şifre boş geçilemez.")
    @Size(min = 8, max = 64)

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,}$",
            message = "Şifre enaz 8 Karakter, Enaz bir büyük bir küçük harf, Sayı ve özel karakterden oluşmalıdır.")
    String password;
    @NotNull(message = "Şifre boş geçilemez.")
    @Size(min = 8, max = 64)
    String repassword;
    @Email(message = "Lütfen geçerli bir e-mail adresi giriniz.")
    String email;
}