package com.kaist.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kaist.entity.Board;
import com.kaist.entity.BoardComment;
import com.kaist.entity.Role;
import com.kaist.entity.User;
import com.kaist.mapper.KaistMapper;
import com.kaist.repository.BoardCommentRepository;
import com.kaist.repository.BoardRepository;
import com.kaist.repository.UserRepository;

@Service
public class BaordCommentService {
	
	@Autowired
	BoardCommentRepository boardCommentRepo;
	
	@Autowired
	UserRepository userRepo;
	
	
	@Autowired
	KaistMapper mapper;
	
	
	
	public List<HashMap> save(BoardComment param, Authentication auth) {

		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String userid = userDetails.getUsername();
		User user = userRepo.findByUserId(userid);
		param.setUserId(user.getId());
		HashMap hParam = new HashMap();
		if(param.getId()!=null){
			Optional<BoardComment> bco = boardCommentRepo.findById(param.getId());

			if(bco.isPresent()){
				BoardComment bc = bco.get();
				if(bc.getUserId().equals(user.getId())){
					if(param.getContent() != null )bc.setContent(param.getContent());
					BoardComment saveBoard = boardCommentRepo.save(bc);
					hParam.put("boardId", saveBoard.getBoardId());
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			BoardComment saveBoard = boardCommentRepo.save(param);
			hParam.put("boardId", saveBoard.getBoardId());
		}
		List<HashMap> result = mapper.findByBoardComment(hParam);
		return result;
	}
	
	public List<HashMap> delete(BoardComment param, Authentication auth) {

		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String userid = userDetails.getUsername();
		User user = userRepo.findByUserId(userid);
		param.setUserId(user.getId());
		
		BoardComment board = boardCommentRepo.findById(param.getId()).get();
		HashMap hParam = new HashMap();
		hParam.put("boardId", board.getBoardId());
		if(user.getRole() == Role.ROLE_ADMIN || user.getRole() == Role.ROLE_USER && board.getUserId() == user.getId()){
			boardCommentRepo.delete(param);
			return mapper.findByBoardComment(hParam); 
		}
		
		return null;
	}
	
	

}
