package com.oguztasgin.config.security;

import com.oguztasgin.repository.entity.UserProfile;
import com.oguztasgin.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetail implements UserDetailsService {
    @Autowired
    UserProfileService userProfileService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails getUserByAuthId(Long authid){
        Optional<UserProfile> userProfile = userProfileService.findByAuthId(authid);
        if(userProfile.isEmpty()) return null;
        /**
         * Kullanıcı yetkileri için genellikle ayrı bir tabloda yetki listesi yapılır.
         *
         */
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("EDITOR"));
        authorities.add(new SimpleGrantedAuthority("BIGUSER"));
        authorities.add(new SimpleGrantedAuthority("YUKSEK_YERDE_DAYIM_VAR"));

        /**
         * Burada oluşturulan user nesnesi, bizim vertabanında tuttuğumuz kullanıcı
         * bilgileri ile doldurulmalıdır. eğer kullanıcıların aktif, pasif, bloke
         * gibi durumları var ise bunlarda buraya girilmelidir.
         */
        return User.builder()
                .username(userProfile.get().getUsername())
                .password("")
                .accountExpired(false)
                .accountLocked(false)
                .authorities(authorities)
                .build();
    }
}
