package com.kaist.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaist.dto.Message;
import com.kaist.entity.Community;
import com.kaist.entity.CommunityUser;
import com.kaist.mapper.KaistMapper;
import com.kaist.service.CommunityService;
import com.kaist.service.CommunityUserService;


//localhost:8080/community

@RestController
@RequestMapping("/community")
public class CommunityController {
	
	@Autowired
	CommunityService communityService;
	
	@Autowired
	CommunityUserService communityUserService;
	
	@Autowired
	KaistMapper mapper;
	
	@PostMapping("/create") //localhost:8080/community/create
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<Message> create(@RequestBody Community community) {
		
		Community result = communityService.create(community);
		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		message.setStatus(HttpStatus.OK);
		message.setMessage("정상적으로 처리되었습니다.");
		message.setData(result);
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
	
	
	@GetMapping("/list")
	public ResponseEntity<Message> list(){
		
		List<Community> result = communityService.list();
		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		message.setStatus(HttpStatus.OK);
		message.setMessage("정상적으로 처리되었습니다.");
		message.setData(result);
		
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
	
	@PostMapping("/user/add")
	public ResponseEntity<Message> userAdd(@RequestBody CommunityUser cu, Authentication auth){
		Message message = communityUserService.userAdd(cu, auth);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(message, headers, message.getStatus());
	}

	@PostMapping("/user/edit")
	public ResponseEntity<Message> userEdit(@RequestBody CommunityUser cu, Authentication auth){
		Message message = communityUserService.userEdit(cu, auth);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(message, headers, message.getStatus());
	}

	@PostMapping("/user/delete")
	public ResponseEntity<Message> userDelete(@RequestBody CommunityUser cu, Authentication auth){

		List<HashMap> result = communityUserService.userDelete(cu, auth);
		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		message.setStatus(HttpStatus.OK);
		message.setMessage(null == result ? "오류가 발생하였습1니다." : "정상적으로 처리되었습니다.");
		message.setData(null == result ? new ArrayList():result);

		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
	
	
	@PostMapping("/list/user")
	public ResponseEntity<Message> listUser(Authentication auth){
		
		List<HashMap> result = communityService.listUser(auth);
		
		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		message.setStatus(HttpStatus.OK);
		message.setMessage(null == result ? "error" : "ok");
		message.setData(null == result ? new ArrayList():result);
		
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
	
	
	@PostMapping("/image/save")
	public ResponseEntity<Message> imageSave(Community param, Authentication auth){
		
		Community result = communityService.communityImageSave(param);
		
		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		message.setStatus(HttpStatus.OK);
		message.setMessage(null == result ? "error" : "ok");
		message.setData(null == result ? new ArrayList():result);
		
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
	
	
	
	
	
	

}
