package com.oguztasgin.utility;

import org.springframework.stereotype.Component;

@Component
public class TokenManager {

    public String createToken(Long id){
        String token = "tkn...:"+id;
        return token;
    }

    public Long getDecodeToken(String token){
        Long id = Long.parseLong(token.substring(token.indexOf(":")+1));
        return id;
    }
}