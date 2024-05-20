package com.bamba.secureapp.mapper;

import com.bamba.secureapp.dto.UserDTO;
import com.bamba.secureapp.entities.UserEntity;

public class UserMapper {
	
	public static UserEntity userDtoToUserEntity(UserDTO userDTO) {
		
		UserEntity userEntity = new UserEntity();
		userEntity.setId(userDTO.getId());
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setPassword(userDTO.getPassword());
		
		return userEntity;
	}
	
	public static UserDTO userEntityToUserDto(UserEntity userEntity) {
		
		UserDTO userDto = new UserDTO();
		userDto.setId(userEntity.getId());
		userDto.setEmail(userEntity.getEmail());
		userDto.setPassword(userEntity.getPassword());
		
		return userDto;
	}

}