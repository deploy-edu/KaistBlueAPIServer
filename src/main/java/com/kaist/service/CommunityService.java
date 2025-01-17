package com.kaist.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kaist.entity.Community;
import com.kaist.entity.Role;
import com.kaist.entity.User;
import com.kaist.mapper.KaistMapper;
import com.kaist.repository.CommunityRepository;
import com.kaist.repository.CommunityUserRepository;
import com.kaist.repository.UserRepository;

@Service
public class CommunityService {

	
	@Autowired
	CommunityRepository communityRepo;
	
	@Autowired
	CommunityUserRepository communityUserRepo;
	
	@Autowired
	KaistMapper mapper;
	
	@Autowired
	UserRepository userRepo;
	
	public Community create(Community community) {
		if(community.getImageStr() != null) {
			return communityImageSave(community);
		}
		
		return communityRepo.save(community); 
	}
	
	public List<Community> list() {
		return communityRepo.findAll(); 
	}
	
	public List<HashMap> listUser(Authentication auth) {
		//인증값에서 사용자 추출하여 처리
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String userId = userDetail.getUsername(); // kaist 1, admin 2
		User user = userRepo.findByUserId(userId);
		HashMap map = new HashMap();
		map.put("userId", user.getId());
		
		List<HashMap> result = mapper.findByMyCommunity(map);
		
		return result; 
	}
	
	
	public Community communityImageSave(Community community) {
		//DATA URL 을 바이트로 변환
		  byte imageArray [] = null;
		  
		  //이미지가 있는 경우 저장
		  String BASE_64_PREFIX = "data:image/png;base64,";
		  String base64Url = community.getImageStr();
		  String[] base64Array = base64Url.split(",");
		  if(BASE_64_PREFIX != base64Array[0]) {
			  BASE_64_PREFIX = base64Array[0]+",";
		  }
		  
		  if(base64Url!=null&&base64Url!="") {
			  try {
				  
				  if(base64Url.startsWith(BASE_64_PREFIX)) {
			            if (base64Url.startsWith(BASE_64_PREFIX)){
			                imageArray =  Base64.getDecoder().decode(base64Url.substring(BASE_64_PREFIX.length()));
			            }
//			            System.out.println(imageArray);
			            community.setImage(imageArray);
			            community.setType(BASE_64_PREFIX);
				  }
			  }catch(Exception e) {
				  e.printStackTrace();
			  }
			
		  }
		  Community result = communityRepo.save(community);
		  return result;
	  }
	
	
}
