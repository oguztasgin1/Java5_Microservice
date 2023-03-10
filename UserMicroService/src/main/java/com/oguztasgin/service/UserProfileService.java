package com.oguztasgin.service;

import com.oguztasgin.dto.request.UserSaveResquestDto;
import com.oguztasgin.exception.EErrorType;
import com.oguztasgin.exception.UserException;
import com.oguztasgin.manager.IElasticServiceManager;
import com.oguztasgin.mapper.IUserProfileMapper;
import com.oguztasgin.rabbitmq.model.CreateUser;
import com.oguztasgin.repository.IUserProfileRepository;
import com.oguztasgin.repository.entity.UserProfile;
import com.oguztasgin.utility.JwtTokenManager;
import com.oguztasgin.utility.ServiceManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {
    private final IUserProfileRepository repository;

    private final IElasticServiceManager elasticServiceManager;
    private final JwtTokenManager jwtTokenManager;
    public UserProfileService(IUserProfileRepository repository,
                              IElasticServiceManager elasticServiceManager, JwtTokenManager jwtTokenManager){
        super(repository);
        this.repository=repository;
        this.elasticServiceManager = elasticServiceManager;
        this.jwtTokenManager = jwtTokenManager;
    }
    public Boolean saveDto(UserSaveResquestDto dto){
        UserProfile userProfile= IUserProfileMapper.INSTANCE.toUserProfile(dto);
        save(userProfile);
        elasticServiceManager.save(IUserProfileMapper.INSTANCE.toUserProfileSaveRequestDto(userProfile));
        return true;
    }

    public void save(CreateUser createUser){
        UserProfile userProfile = IUserProfileMapper.INSTANCE.toUserProfile(createUser);
        save(userProfile);
        elasticServiceManager.save(IUserProfileMapper.INSTANCE.toUserProfileSaveRequestDto(userProfile));
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

    @Cacheable(value = "getUpperName")
    public String getUpperName(String name){
        try{
            Thread.sleep(3000);
        }catch (Exception e){

        }
        return name.toUpperCase();
    }
    @CacheEvict(value = "getUpperName", allEntries = true)
    public void clearCache(){
        System.out.println("Tum cache temizlendi");
    }

    /**
     * Tüm Kullanıcı Listesini belli kriterlere göre sayfalı ve sıralı şekilde döner
     * @param direction -> (ASC, DESC)  -> a..Z, Z..a, 0..9, 9..0
     * @param currentPage -> sayfalama yapılmış ise getirmek istenilen sayfayı niteler.
     *                    10.000/50 [1-200 sayfa] 5. sayfa 251.-300 arasını verir.
     * @param pageSize -> her istek te getirilecek data sayısını belirtir. 10.000 kaydın oldeuğu bir listede
     *                 50 yazarsanız, kayıtları size 50 şer olarak getirir.
     * @param sortingParameter -> Hangi alanda sıralama yapmak istiyorsunuz?
     * @return
     */
    public Page<UserProfile> findAll(String direction, Integer currentPage, int pageSize, String sortingParameter){
        Sort sort = null;
        Pageable pageable = null;
        if(!sortingParameter.isEmpty()){
            direction = direction == null ? "ASC" : direction;
            sort = Sort.by(Sort.Direction.fromString(direction),sortingParameter);
        }
        /**
         * 1- sıralama yap, sayfayı getir.
         * 2- sıralama yap, sayfa belirtmemiş
         * 3- sıralama belirtmemiz, sayfa getir.
         * Sayfalama yapmak zorundayız.
         */
        if(sort!=null && currentPage!=null){
            pageable = PageRequest.of(currentPage,pageSize == 0 ? 10 : pageSize,sort);
        } else if(sort==null && currentPage!=null){
            pageable = PageRequest.of(currentPage,pageSize == 0 ? 10 : pageSize);
        }else{
            pageable = PageRequest.of(0,pageSize == 0 ? 10 : pageSize);
        }
        return repository.findAll(pageable);
    }

    public Optional<UserProfile> findByAuthId(Long authid){
        return repository.findOptionalByAuthid(authid);
    }

}
