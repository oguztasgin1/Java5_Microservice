package com.oguztasgin.config.security;

import org.apache.http.protocol.HTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
/**
 * Uygulam içinde kullanılan tum methodlara yetkilendirme işlemlerini
 * aktif etmek
 * için gerekli anatastonun eklenmesi gereklidir
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserMicroServiceSecurityConfig {
    @Bean
    JwtTokenFilter getJwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests() // Gelen isteklere kimlik doğrulama işlemi yapalım
                .antMatchers("/api/v1/user/**") // buraya yazdığım istek url si ile eşleşiyor ise
                .authenticated()// bunlara güvenlik uygula.
                .anyRequest() // tüm istekleri ifade eder
                .permitAll(); // her gelen e izin ver.
        /**
         * Eğer bir yer erişim kısıtlı ve oturum açma isteği var ise bunun için bir form çıkartır.
         */
        // httpSecurity.csrf().disable();
        // httpSecurity.formLogin();
        httpSecurity.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}

