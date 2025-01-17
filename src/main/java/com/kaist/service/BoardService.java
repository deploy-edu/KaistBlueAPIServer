package com.kaist.service;

import java.util.HashMap;
import java.util.List;

import com.kaist.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kaist.mapper.KaistMapper;
import com.kaist.repository.BoardCommentRepository;
import com.kaist.repository.BoardRepository;
import com.kaist.repository.UserRepository;

@Service
public class BoardService {

	@Autowired
	BoardRepository boardRepo;

	@Autowired
	BoardCommentRepository boardCommentRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	KaistMapper mapper;

	public Board save(Board board, Authentication auth) {

		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String userid = userDetails.getUsername();
		User user = userRepo.findByUserId(userid);

		board.setUserId(user.getId());

		// 수정
		if (null != board.getId()) {
			Board updateBoard = boardRepo.findById(board.getId()).get();
			updateBoard.setTitle(board.getTitle());
			updateBoard.setContent(board.getContent());
			return boardRepo.save(board);

		}
		// 저장
		return boardRepo.save(board);
	}

	public List<HashMap> list(Community community) {

		HashMap param = new HashMap();
		param.put("communityId", community.getId());

		return mapper.findByCommunityBoard(param);
	}

	public List<HashMap> list(BoardComment comment) {

		HashMap param = new HashMap();
		param.put("boardId", comment.getBoardId());

		return mapper.findByBoardComment(param);
	}

	public Board delete(Board board, Authentication auth) {

		// 자신의글 or 관리자일때

		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String userid = userDetails.getUsername();
		User user = userRepo.findByUserId(userid);
		board.setUserId(user.getId());
		Board deleteBoard = boardRepo.findById(board.getId()).get();

		if (user.getRole() == Role.ROLE_ADMIN || user.getId() == deleteBoard.getUserId()) {
			boardRepo.delete(board);
			return null;
		} else {
			return board;
		}

	}

}
