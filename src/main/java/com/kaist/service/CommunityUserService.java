package com.kaist.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import com.kaist.dto.CommunityUserDTO;
import com.kaist.dto.Message;
import com.kaist.dto.UserDTO;
import com.kaist.mapper.KaistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kaist.entity.CommunityUser;
import com.kaist.entity.User;
import com.kaist.repository.CommunityUserRepository;
import com.kaist.repository.UserRepository;

@Service
public class CommunityUserService {
	
	
	@Autowired
	CommunityUserRepository communityUserRepo;
	
	@Autowired
	UserRepository  userRepo;

	@Autowired
	KaistMapper mapper;
	
	public Message userAdd(CommunityUser cu, Authentication auth) {

		//인증된 사용자 정보를 찾기
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String userName = userDetails.getUsername();
		User user = userRepo.findByUserId(userName);
		cu.setUserId(user.getId());

		Message rMsg = new Message();
		rMsg.setStatus(HttpStatus.OK);
		//커뮤니티 닉네임 검색
		CommunityUser findUser = communityUserRepo.findByUserIdAndCommunityId(user.getId(), cu.getCommunityId());

		if(null!=findUser){
			//중복가입
			rMsg.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			rMsg.setMessage("중복가입 불가");
			return rMsg;
		}

		if(communityUserRepo.existsByNickNameAndCommunityId(cu.getNickName(), cu.getCommunityId())){
			//닉네임 중복
			rMsg.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			rMsg.setMessage("사용중인 닉네임");
			return rMsg;
		}else{
			rMsg.setStatus(HttpStatus.OK);
			CommunityUser result = communityUserImageSave(cu);
			rMsg.setMessage(result != null ? "정상처리 되었습니다." : "서버오류");
			if(null!=result){
				rMsg.setData(result);
			}
			return rMsg;
		}
	}

	public Message userEdit(CommunityUser cu, Authentication auth) {

		//인증된 사용자 정보를 찾기
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String userName = userDetails.getUsername();
		User user = userRepo.findByUserId(userName);
		cu.setUserId(user.getId());

		Message rMsg = new Message();
		rMsg.setStatus(HttpStatus.OK);
		CommunityUser findUser = communityUserRepo.findByUserIdAndCommunityId(user.getId(), cu.getCommunityId());
		if(null == findUser){
			//닉네임 중복
			rMsg.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			rMsg.setMessage("사용자를 찾을 수 없음");
			return rMsg;
		}else{
			rMsg.setStatus(HttpStatus.OK);
			//수정은 이미지, 또는 닉네임만
			if(cu.getNickName() != null) findUser.setNickName(cu.getNickName());
			if(cu.getImageStr() != null) findUser.setImageStr(cu.getImageStr());
			CommunityUser result = communityUserImageSave(findUser);
			rMsg.setMessage(result != null ? "정상처리 되었습니다." : "서버오류");
			if(null!=result){
				CommunityUserDTO cuDTO = new CommunityUserDTO();
				cuDTO.setCommunityId(result.getCommunityId());
				cuDTO.setImage(result.getImageStr());
				cuDTO.setNickName(result.getNickName());
				cuDTO.setUserId(result.getUserId());
				cuDTO.setUserName(user.getUserName());
				cuDTO.setEmail(user.getEmail());
				rMsg.setData(cuDTO);
			}
			return rMsg;
		}
	}

	public List<HashMap> userDelete(CommunityUser cu, Authentication auth) {

		//인증된 사용자 정보를 찾기

		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String userName = userDetails.getUsername();
		User user = userRepo.findByUserId(userName);
		cu.setUserId(user.getId());
		communityUserRepo.delete(cu);
		HashMap<String, Long> param = new HashMap<>();
		param.put("userId", user.getId());
		return mapper.findByMyCommunity(param);
	}

	public CommunityUser communityUserImageSave(CommunityUser communityUser) {
		//DATA URL 을 바이트로 변환

		if(null != communityUser.getImageStr() && !communityUser.getImageStr().isEmpty()){
			  byte imageArray [] = null;
			  //이미지가 있는 경우 저장
			  String BASE_64_PREFIX = "data:image/png;base64,";
			  String base64Url = communityUser.getImageStr();
			  String[] base64Array = base64Url.split(",");
			  if(!BASE_64_PREFIX.equals(base64Array[0]) ) {
				  BASE_64_PREFIX = base64Array[0]+",";
			  }

			  if(!base64Url.isEmpty()) {
				  try {
					  if(base64Url.startsWith(BASE_64_PREFIX)) {
							if (base64Url.startsWith(BASE_64_PREFIX)){
								imageArray =  Base64.getDecoder().decode(base64Url.substring(BASE_64_PREFIX.length()));
							}
	//			            System.out.println(imageArray);
							communityUser.setImage(imageArray);
							communityUser.setType(BASE_64_PREFIX);
					  }
				  }catch(Exception e) {
					  e.printStackTrace();
				  }
			  }
		}
		  CommunityUser result = communityUserRepo.save(communityUser);
		  return result;
	  }
	
	
	
	
	

}
