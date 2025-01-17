package com.kaist.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kaist.entity.User;
import com.kaist.mapper.KaistMapper;
import com.kaist.repository.UserRepository;

@Service
public class kaistDemoApiService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	KaistMapper mapper; 

	public List<User> getUser() {
		return userRepo.findAll();
	}
	
	// 서비스에서 Repository 로 연결
	public List<User> findByUserName(String userName) {
		return userRepo.findByUserName(userName);
	}
	
	// 서비스에서 Repository 로 연결
	public Page<User> findByEmail(HashMap HParam) {
		int page = Integer.parseInt(HParam.get("page").toString());
		int size = Integer.parseInt(HParam.get("size").toString());
		String email = HParam.get("email").toString();
		
		PageRequest pageRequest = PageRequest.of(page, size);
		
		return userRepo.findByEmail(email, pageRequest);
	}
	
	public List<HashMap> findByUser(HashMap HParam) {
		return mapper.findByUserList(HParam);
	}
	
	
	
}
