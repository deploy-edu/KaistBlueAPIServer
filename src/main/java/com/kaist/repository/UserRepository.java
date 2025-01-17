package com.kaist.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kaist.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByUserId(String userId);
	public User findByEmail(String Email);
	public List<User> findByUserName(String userName);
	public Page<User> findByEmail(String email, PageRequest page);
}
