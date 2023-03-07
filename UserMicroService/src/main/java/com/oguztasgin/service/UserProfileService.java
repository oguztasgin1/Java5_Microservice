package com.oguztasgin.service;

import com.oguztasgin.dto.request.UserSaveResquestDto;
import com.oguztasgin.exception.EErrorType;
import com.oguztasgin.exception.UserException;
import com.oguztasgin.mapper.IUserProfileMapper;
import com.oguztasgin.repository.IUserProfileRepository;
import com.oguztasgin.repository.entity.UserProfile;
import com.oguztasgin.utility.JwtTokenManager;
import com.oguztasgin.utility.ServiceManager;
import com.oguztasgin.utility.TokenManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {
    private final IUserProfileRepository repository;

    private final JwtTokenManager jwtTokenManager;
    public UserProfileService(IUserProfileRepository repository,
                             JwtTokenManager jwtTokenManager){
        super(repository);
        this.repository=repository;
        this.jwtTokenManager = jwtTokenManager;
    }
    public Boolean saveDto(UserSaveResquestDto dto){
        UserProfile userProfile= IUserProfileMapper.INSTANCE.toUserProfile(dto);
        save(userProfile);
        return true;
    }

    public List<UserProfile> findAll(String token){
        Optional<Long> authid = jwtTokenManager.getIdFromToken(token);
        if (authid.isEmpty())
            throw  new UserException(EErrorType.INVALID_TOKEN);
        Optional<UserProfile> userProfile = repository.findOptionalByAuthid(authid.get());
        if(userProfile.isEmpty())
            throw new UserException(EErrorType.INVALID_TOKEN, "Token için gönderilen kullanıcı sistemde kayıtlı değildir.");
        return findAll();
    }
}
