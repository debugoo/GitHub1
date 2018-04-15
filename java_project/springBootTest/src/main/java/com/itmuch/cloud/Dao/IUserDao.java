package com.itmuch.cloud.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itmuch.cloud.entity.User;

@Repository
public interface IUserDao extends JpaRepository<User, Long> {
	
}
