package com.chicmic.sbthene.repo;


import com.chicmic.sbthene.models.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepo extends JpaRepository<UserToken,String> {
    UserToken findByUuid(String uuid);
    
    void deleteByUuid(String authHeader);

//    void deleteByUserUuid(String uuid);
}
