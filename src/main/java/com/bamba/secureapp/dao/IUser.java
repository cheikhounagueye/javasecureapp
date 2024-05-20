package com.bamba.secureapp.dao;

import java.util.List;

import com.bamba.secureapp.entities.UserEntity;

public interface IUser {
	public List<UserEntity> liste();
	public boolean delete(long mat);
	public boolean update(UserEntity e);
	public UserEntity get(long mat);
	public boolean add(UserEntity u);

}
