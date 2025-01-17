package com.kaist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaist.entity.User;
import com.kaist.repository.UserRepository;
import com.kaist.service.kaistDemoApiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping(value = "/kaist")
@RestController
public class KaistMainController {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	kaistDemoApiService service;
	
	
//	@PostConstruct
//	public void init() {
//		User kUser1 = new User();
//		kUser1.setUserId("kaist1");
//		kUser1.setUserName("김카이스트1");
//		kUser1.setEmail("kaist1@kaist.ac.kr");
//		
//		User kUser2 = new User();
//		kUser2.setUserId("kaist2");
//		kUser2.setUserName("김카이스트2");
//		kUser2.setEmail("kaist2@kaist.ac.kr");
//		
//		User kUser3 = new User();
//		kUser3.setUserId("kaist3");
//		kUser3.setUserName("김카이스트3");
//		kUser3.setEmail("kaist3@kaist.ac.kr");
//		
//		User kUser4 = new User();
//		kUser4.setUserId("kaist4");
//		kUser4.setUserName("김카이스트4");
//		kUser4.setEmail("kaist4@kaist.ac.kr");
//		
//		User kUser5 = new User();
//		kUser5.setUserId("kaist5");
//		kUser5.setUserName("김카이스트5");
//		kUser5.setEmail("kaist5@kaist.ac.kr");
//		
//		User kUser6 = new User();
//		kUser6.setUserId("kaist6");
//		kUser6.setUserName("김카이스트6");
//		kUser6.setEmail("kaist6@kaist.ac.kr");
//		
//		userRepo.save(kUser1);
//		userRepo.save(kUser2);
//		userRepo.save(kUser3);
//		userRepo.save(kUser4);
//		userRepo.save(kUser5);
//		userRepo.save(kUser6);
//	}
	
	//get 방식의 API 
	@Operation(summary ="HTTP GET 방식 요청 데모", description = "String 형식 반환")
	@GetMapping(value = "/hello")
	public String hello() {
		return "hello spring boot!";
	}
	
	//get 방식의 API 
	@Operation(summary ="HTTP GET 방식 요청 데모", description = "String 형식 반환")
	@GetMapping(value = "/users")
	public List<User> getUser() {
		return service.getUser();
	}
	
	
	
	//get 방식의 API 
	@Operation(summary ="HTTP GET 방식 요청 데모", description = "String 형식 반환")
	@GetMapping(value = "/user/{name}")
	public List<User> getUser(@PathVariable("name") String userName) {
		return service.findByUserName(userName);
	}
	
	@GetMapping(value = "/item")
	public HashMap getItem() {
		HashMap returnVal = new HashMap();
		returnVal.put("kaist", "hello wold");
		return returnVal;
	}
	
	
	@Tag(name = "demo1", description = "스웨거 Annotaion 설정에 대한 내용")
	@GetMapping(value = "/items")
	public List getItems() {
		List itemList = new ArrayList();
		HashMap returnVal = new HashMap();
		returnVal.put("kaist", "hello wold");
		
		itemList.add(returnVal);
		itemList.add(returnVal);
		itemList.add(returnVal);
		itemList.add(returnVal);
		
		return itemList;
	}
	@Tag(name = "demo1", description = "스웨거 Annotaion 설정에 대한 내용")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "OK"),
	    @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
	    @ApiResponse(responseCode = "404", description = "NOT FOUND"),
	    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
	})
	@PostMapping(value = "/item/add")
	public HashMap itemAdd(@RequestBody HashMap HParam) {
		System.out.println("## HParam : "+ HParam);
		return HParam;
	}
	
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "OK"),
	    @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
	    @ApiResponse(responseCode = "404", description = "NOT FOUND"),
	    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
	})
	@PostMapping(value = "/user/search")
	public List<HashMap> findByUser(@RequestBody HashMap HParam) {
		
		return service.findByUser(HParam);
	}
	
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "OK"),
	    @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
	    @ApiResponse(responseCode = "404", description = "NOT FOUND"),
	    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
	})
	@PostMapping(value = "/user/page")
	public Page<User> findByUserPage(@RequestBody HashMap HParam) {
		return service.findByEmail(HParam);
	}
	
	
}