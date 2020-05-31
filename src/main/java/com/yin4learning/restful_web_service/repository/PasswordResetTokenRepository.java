package com.yin4learning.restful_web_service.repository;

import org.springframework.data.repository.CrudRepository;

import com.yin4learning.restful_web_service.db.entity.PasswordResetTokenEntity;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long> {
	PasswordResetTokenEntity findByToken(String token);
}
