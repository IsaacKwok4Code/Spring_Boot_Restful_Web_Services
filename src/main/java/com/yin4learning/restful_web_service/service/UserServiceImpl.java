package com.yin4learning.restful_web_service.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yin4learning.restful_web_service.db.entity.PasswordResetTokenEntity;
import com.yin4learning.restful_web_service.db.entity.UserEntity;
import com.yin4learning.restful_web_service.exceptions.UserServiceException;
import com.yin4learning.restful_web_service.model.response.ErrorMessages;
import com.yin4learning.restful_web_service.repository.UserRepository;
import com.yin4learning.restful_web_service.share.dto.AddressDTO;
import com.yin4learning.restful_web_service.share.dto.UserDTO;
import com.yin4learning.restful_web_service.share.util.Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// find user by email - email and userID are unique. but for security and data issue, should have method for finding email.
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//create user entity object and 
		UserEntity userObject = userRepository.findByEmail(email);
		// if system can't find the email in database
		if (userObject == null) throw new UsernameNotFoundException(email);
		
		//return new User(userObject.getEmail(), userObject.getEncryptedPassword(), new ArrayList<>());
		return new User(userObject.getEmail()
				,userObject.getEncryptedPassword()
				,userObject.getEmailVerificationStatus()
				,true, true,
				true, new ArrayList<>());
	}
	
	@Override
	public UserDTO createUser(UserDTO userRsDTO) {
		//First checking existing record by email
		UserEntity existingUserDetails = userRepository.findByEmail(userRsDTO.getEmail());
		if(existingUserDetails !=null) {
			throw new RuntimeException("Record is existing");
		}
		//add the address object assicate with the user object
		for(int i=0;i<userRsDTO.getAddresses().size();i++) {
			AddressDTO address = userRsDTO.getAddresses().get(i);
			address.setUserDetails(userRsDTO);
			address.setAddressId(utils.generateAddressId(30));
			userRsDTO.getAddresses().set(i, address);
		}
		
		UserEntity userEntityObject = new UserEntity();
		UserDTO returnDtoObject = new UserDTO();
		
		// Copy DTO to entity for database
		//BeanUtils.copyProperties(userRsDTO, userEntityObject);
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = modelMapper.map(userRsDTO, UserEntity.class);
		
		//setup userId
		String publicUserId = utils.generateUserId(30);
		userEntityObject.setUserId(publicUserId);
		//setup encrypted PW with BCryPassword encode
		userEntityObject.setEncryptedPassword(bCryptPasswordEncoder.encode(userRsDTO.getPassword()));
		//setup token
		userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(publicUserId));

		//save the user data to DB
		UserEntity storedUserDetails = userRepository.save(userEntityObject);
		// copy entity data to DTO
		//BeanUtils.copyProperties(storedUserDetails, returnDtoObject);
		returnDtoObject  = modelMapper.map(storedUserDetails, UserDTO.class);
		
		// Send an email message to user to verify their email address
		// amazonSES.verifyEmail(returnValue);
		
		return returnDtoObject;
	}

	@Override
	public UserDTO getUser(String email) {
		// check DB with the email
		UserEntity userEntity = userRepository.findByEmail(email);
		// if the data is not exist throw exception
		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		// create return DTO object
		UserDTO returnValue = new UserDTO();
		// copy the data from entity to return object if the data is exist
		BeanUtils.copyProperties(userEntity, returnValue);
		// return object
		return returnValue;
	}

	@Override
	public UserDTO getUserByUserId(String id) {
		// create return DTO object
		UserDTO returnValue = new UserDTO();
		// check DB with the id
		UserEntity userEntity = userRepository.findByUserId(id);
		// if the data is not exist throw exception
		if(userEntity == null) {
			throw new UsernameNotFoundException("User with ID: " + id + " not found");
		}
		// copy the data from entity to return object if the data is exist
		BeanUtils.copyProperties(userEntity, returnValue);
		// return object
		return returnValue;
	}
	
	@Override
	public UserDTO updateUser(String userId, UserDTO user) {
		// create return DTO object
		UserDTO returnValue = new UserDTO();
		// check DB with the id
		UserEntity userEntity = userRepository.findByUserId(userId);
		// if the data is not exist throw custom exception
		if(userEntity == null) {
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}
		// update first name
		userEntity.setFirstName(user.getFirstName());
		// update last name
		userEntity.setLastName(user.getLastName());
		// save to DB
		UserEntity updatedUserDetails = userRepository.save(userEntity);
		// copy the data from entity to DTO
		returnValue = new ModelMapper().map(updatedUserDetails, UserDTO.class);
		// return object
		return returnValue;
	}
	
	@Transactional
	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		userRepository.delete(userEntity);
	}
	
	@Override
	public List<UserDTO> getUsers(int page, int limit) {
		List<UserDTO> returnValue = new ArrayList<>();
		if(page>0) page = page-1;
		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = usersPage.getContent();
		
		for (UserEntity userEntity : users) {
			UserDTO userDto = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
		}
		return returnValue;
	}
	
	@Override
	public boolean verifyEmailToken(String token) {
		boolean returnValue = false;
		UserEntity userEntity = userRepository.findUserByEmailVerificationToken(token);
		if (userEntity != null) {
            boolean hastokenExpired = Utils.hasTokenExpired(token);
            if (!hastokenExpired) {
                userEntity.setEmailVerificationToken(null);
                userEntity.setEmailVerificationStatus(Boolean.TRUE);
                userRepository.save(userEntity);
                returnValue = true;
            }
        }
		return returnValue;
	}
	
	@Override
	public boolean requestPasswordReset(String email) {
		boolean returnValue = false;
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if (userEntity == null) {
            return returnValue;
        }
		
		String token = new Utils().generatePasswordResetToken(userEntity.getUserId());
		PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUserDetails(userEntity);
        passwordResetTokenRepository.save(passwordResetTokenEntity);
        
        returnValue = new AmazonSES().sendPasswordResetRequest(
                userEntity.getFirstName(), 
                userEntity.getEmail(),
                token);
        
		return returnValue;
	}

	@Override
	public boolean resetPassword(String token, String password) {
		// TODO Auto-generated method stub
		return false;
	}
	}
	
}
