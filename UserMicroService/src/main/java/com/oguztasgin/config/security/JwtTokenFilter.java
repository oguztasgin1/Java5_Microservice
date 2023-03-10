package com.oguztasgin.config.security;

import com.oguztasgin.exception.EErrorType;
import com.oguztasgin.exception.UserException;
import com.oguztasgin.utility.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenManager jwtTokenManager;
    @Autowired
    JwtUserDetail jwtUserDetail;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        /**
         * Burası uygulamamıza gelen her isteğin yakalandığı yerdir. Gelen isteklerde Header içinde token bilgisininin
         * ve oturumun oluş olmadığının kontrol edilmesi gerekir. burada bu işlemleri yapıyoruz. İlk olarak header
         * içinden Bearer [TOKEN] alınır.
         */
        final String authHeaderBearerToken = request.getHeader("Authorization");
        if(authHeaderBearerToken!=null && authHeaderBearerToken.startsWith("Bearer ")
                &&  SecurityContextHolder.getContext().getAuthentication() == null
        ){
            /**
             * Header içinden Bearer çıkartılarak Token bilgisi alınır.
             */
            String token = authHeaderBearerToken.substring(7);
            /**
             * token doğrulama işlemi yapılarak geçerliliği kontrol edilir.
             */
            Optional<Long> authid =  jwtTokenManager.getIdFromToken(token);
            /**
             * Eğer token geçerli değil ise istisna fırlatılır.
             */
            if(authid.isEmpty())
                throw new UserException(EErrorType.INVALID_TOKEN);
            /**
             * Bu kısımda kullanıcı için geçerli isteğine ait bir Session açmamız gerekir. bunu
             * yapapilmek için Spring bizden kendinin takip edebileceği bir kimlik kartı talep eder
             * bunu yapmak için, UserDetail nesnesi talep eder.
             */
            UserDetails userDetails = jwtUserDetail.getUserByAuthId(authid.get());
            /**
             * İçeride işlem yapacak olan kullanıcıya ait bilgileri ve yetkileri içeren
             * özel bir token oluşturuyorsunuz.
             */
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,null,userDetails.getAuthorities());
            /**
             * Bu kısımda Spring tarafından istenilen bir kimlik kartı eklemelisiniz.
             */
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }


        filterChain.doFilter(request,response);
    }

}
