package com.oguztasgin.mapper;

import com.oguztasgin.dto.request.UserProfileSaveRequestDto;
import com.oguztasgin.dto.request.UserSaveResquestDto;
import com.oguztasgin.rabbitmq.model.CreateUser;
import com.oguztasgin.repository.entity.UserProfile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-10T00:59:17+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.4.1 (Oracle Corporation)"
)
@Component
public class IUserProfileMapperImpl implements IUserProfileMapper {

    @Override
    public UserProfile toUserProfile(UserSaveResquestDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder<?, ?> userProfile = UserProfile.builder();

        userProfile.authid( dto.getAuthid() );
        userProfile.username( dto.getUsername() );
        userProfile.email( dto.getEmail() );

        return userProfile.build();
    }

    @Override
    public UserProfile toUserProfile(CreateUser createUser) {
        if ( createUser == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder<?, ?> userProfile = UserProfile.builder();

        userProfile.authid( createUser.getAuthid() );
        userProfile.username( createUser.getUsername() );
        userProfile.email( createUser.getEmail() );

        return userProfile.build();
    }

    @Override
    public UserProfileSaveRequestDto toUserProfileSaveRequestDto(UserProfile userProfile) {
        if ( userProfile == null ) {
            return null;
        }

        UserProfileSaveRequestDto.UserProfileSaveRequestDtoBuilder<?, ?> userProfileSaveRequestDto = UserProfileSaveRequestDto.builder();

        userProfileSaveRequestDto.state( userProfile.isState() );
        userProfileSaveRequestDto.createDate( userProfile.getCreateDate() );
        userProfileSaveRequestDto.updateDate( userProfile.getUpdateDate() );
        userProfileSaveRequestDto.id( userProfile.getId() );
        userProfileSaveRequestDto.authid( userProfile.getAuthid() );
        userProfileSaveRequestDto.username( userProfile.getUsername() );
        userProfileSaveRequestDto.email( userProfile.getEmail() );
        userProfileSaveRequestDto.photo( userProfile.getPhoto() );
        userProfileSaveRequestDto.about( userProfile.getAbout() );
        userProfileSaveRequestDto.phone( userProfile.getPhone() );
        userProfileSaveRequestDto.age( userProfile.getAge() );
        userProfileSaveRequestDto.website( userProfile.getWebsite() );

        return userProfileSaveRequestDto.build();
    }
}
