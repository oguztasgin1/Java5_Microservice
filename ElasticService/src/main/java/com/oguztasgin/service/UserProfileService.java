package com.oguztasgin.service;

import com.oguztasgin.dto.request.UserProfileSaveRequestDto;
import com.oguztasgin.mapper.IUserProfileMapper;
import com.oguztasgin.repository.IUserProfileRepository;
import com.oguztasgin.repository.entity.UserProfile;
import com.oguztasgin.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile, String> {
    private final IUserProfileRepository repository;
    public UserProfileService(IUserProfileRepository repository){
        super(repository);
        this.repository=repository;
    }
    public void saveDto(UserProfileSaveRequestDto dto) {
        repository.save(IUserProfileMapper.INSTANCE.toUserProfile(dto));
    }
}
