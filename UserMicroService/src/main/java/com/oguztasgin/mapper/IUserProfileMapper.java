package com.oguztasgin.mapper;

import com.oguztasgin.dto.request.UserProfileSaveRequestDto;
import com.oguztasgin.dto.request.UserSaveResquestDto;
import com.oguztasgin.rabbitmq.model.CreateUser;
import com.oguztasgin.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface IUserProfileMapper {

    IUserProfileMapper INSTANCE = Mappers.getMapper(IUserProfileMapper.class);

    UserProfile toUserProfile(final UserSaveResquestDto dto);
    UserProfile toUserProfile(final CreateUser createUser);

    UserProfileSaveRequestDto toUserProfileSaveRequestDto (final UserProfile userProfile);

}
