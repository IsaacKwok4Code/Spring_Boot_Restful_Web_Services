package com.yin4learning.restful_web_service.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.yin4learning.restful_web_service.entity.UserEntity;

@Repository
public interface UserRepository extends  CrudRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);
	//UserEntity findByUserId(String userId);
	//UserEntity findUserByEmailVerificationToken(String token);
}
