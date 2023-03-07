package com.oguztasgin.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Date;
import java.util.Optional;

@ControllerAdvice
public class JwtTokenManager {
    private final Long exTime = 1000L*60*15;
    private final String sifreAnahtari = "q|h-&vTxZAZCM_!";
    public Optional<String> createToken(Long id){
        String token="";
        try{
            /**
            -> withClaim -> içinde Key-value şeklinde bilgiler tutulur.
             Bu bilgiler Payload olarak tutulur ve herkes tarafından görülür.
             Bu nedenle buraya özel bilgiler eklenmez.
             -> withIssuer -> jwt yi oluşturan kişinin kimliğini tutmak için kullanılır.
             -> withExpiresAt -> Jwt nin gecirlilik suresi 30sn - sonsuz.
             -> sign() -> hazırlanan icerigin imzanalması gerekir.
             Bunun icin bir şifre belirlenir ve bunun la kriptolama yapılır.
             */
            token = JWT.create().withAudience()
                    .withClaim("id", id)
                    .withClaim("howtopage", "AUTHPAGE")
                    .withClaim("yetki", "ADMIN")
                    .withIssuer("Java5")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis()+ exTime))
                    .sign(Algorithm.HMAC512(sifreAnahtari));
            return Optional.of(token);
        }catch (Exception exception){
            return Optional.empty();
        }
    }

    public Boolean validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(sifreAnahtari);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Java5").build();
            DecodedJWT decodedJWT =  verifier.verify(token);
            if (decodedJWT==null) return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public Optional<Long> getIdFromToken(String token){
        try{
        Algorithm algorithm = Algorithm.HMAC512(sifreAnahtari);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("Java5").build();
        DecodedJWT decodedJWT =  verifier.verify(token);
            if (decodedJWT==null) return Optional.empty();
            return Optional.of(decodedJWT.getClaim("id").asLong());
        }catch (Exception e){
        return Optional.empty();
        }
    }
}
