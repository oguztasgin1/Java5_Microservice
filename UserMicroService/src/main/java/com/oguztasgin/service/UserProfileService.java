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
            throw new UserException(EErrorType.INVALID_TOKEN, "Token i??in g??nderilen kullan??c?? sistemde kay??tl?? de??ildir.");
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
     * T??m Kullan??c?? Listesini belli kriterlere g??re sayfal?? ve s??ral?? ??ekilde d??ner
     * @param direction -> (ASC, DESC)  -> a..Z, Z..a, 0..9, 9..0
     * @param currentPage -> sayfalama yap??lm???? ise getirmek istenilen sayfay?? niteler.
     *                    10.000/50 [1-200 sayfa] 5. sayfa 251.-300 aras??n?? verir.
     * @param pageSize -> her istek te getirilecek data say??s??n?? belirtir. 10.000 kayd??n oldeu??u bir listede
     *                 50 yazarsan??z, kay??tlar?? size 50 ??er olarak getirir.
     * @param sortingParameter -> Hangi alanda s??ralama yapmak istiyorsunuz?
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
         * 1- s??ralama yap, sayfay?? getir.
         * 2- s??ralama yap, sayfa belirtmemi??
         * 3- s??ralama belirtmemiz, sayfa getir.
         * Sayfalama yapmak zorunday??z.
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
