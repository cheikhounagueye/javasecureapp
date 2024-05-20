package com.bamba.secureapp.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bamba.secureapp.dao.LoginDao;
import com.bamba.secureapp.dao.UserImpl;
import com.bamba.secureapp.dto.UserDTO;
import com.bamba.secureapp.entities.UserEntity;
import com.bamba.secureapp.mapper.UserMapper;
import com.bamba.secureapp.dao.IUser;
import com.bamba.secureapp.exception.*;

public class LoginService {

	private static Logger logger = LoggerFactory.getLogger(LoginService.class);

	private LoginDao loginDao = new LoginDao();
	private IUser userDao = new UserImpl();

	public Optional<UserDTO> login(String email, String password) {

		if (email.equals("contact@samanecorporation.com") && password.equals("passer123@")) {
			UserDTO userDto = new UserDTO();
			userDto.setEmail(email);
			userDto.setPassword(password);

			return Optional.of(userDto);
		} else {
			return Optional.ofNullable(null);
		}

	}

	public Optional<UserDTO> loginMockito(String email, String password) {

		logger.info("Tentattive email : {} pwd : {}", email, password);

		Optional<UserEntity> userEntityOption = loginDao.loginUser(email, password);
		if (userEntityOption.isPresent()) {
			UserEntity userEntity = userEntityOption.get();
			UserDTO userDto = UserMapper.userEntityToUserDto(userEntity);

			return Optional.of(userDto);
		} else {
			return Optional.ofNullable(null);
		}

	}

	public Optional<UserDTO> log(String email, String password) {

		logger.info("Tentattive email : {} pwd : {}", email, password);

		Optional<UserEntity> userEntityOption = loginDao.log(email, password);
		if (userEntityOption.isPresent()) {
			UserEntity userEntity = userEntityOption.get();
			UserDTO userDto = UserMapper.userEntityToUserDto(userEntity);

			return Optional.of(userDto);
		} else {
			return Optional.ofNullable(null);
		}

	}

	public Optional<UserDTO> logException(String email, String password) {

		logger.info("Tentative email : {} pwd : {}", email, password);

		return loginDao.logException(email, password).map(user -> {
			UserDTO userDto = UserMapper.userEntityToUserDto(user);
			logger.info("infos correct !");
			return Optional.of(userDto);
		}).orElseThrow(() -> new EntityNotFoundException("infos incorrect."));
	}

	public boolean save(UserDTO userDTO) {

		return userDao.add(UserMapper.userDtoToUserEntity(userDTO));
	}

	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

}