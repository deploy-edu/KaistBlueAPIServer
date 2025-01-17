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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaist.dto.Message;
import com.kaist.dto.StatusEnum;
import com.kaist.entity.Board;
import com.kaist.entity.BoardComment;
import com.kaist.entity.Community;
import com.kaist.service.BaordCommentService;
import com.kaist.service.BoardService;

@RestController
@RequestMapping("/board") // localhost:8080/board
public class BoardController {

	@Autowired
	BoardService boardService;

	@Autowired
	BaordCommentService boardCommentService;

	@PostMapping("/save")
	public ResponseEntity<Message> save(@RequestBody Board board, Authentication auth) {

		Board result = boardService.save(board, auth);

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		message.setStatus(HttpStatus.OK);

		message.setMessage(null == result ? "error" : "ok");
		message.setData(null == result ? new ArrayList() : result);

		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}

	@PostMapping("/list")
	public ResponseEntity<Message> list(@RequestBody Community community) {

		List<HashMap> result = boardService.list(community);

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		message.setStatus(HttpStatus.OK);

		message.setMessage(null == result ? "error" : "ok");
		message.setData(null == result ? new ArrayList() : result);

		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}

	@PostMapping("/delete")
	public ResponseEntity<Message> delete(@RequestBody Board param, Authentication auth) {

		Board result = boardService.delete(param, auth);

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		message.setStatus(HttpStatus.OK);

		message.setMessage(null == result ? "ok" : "error");
		message.setData(null == result ? new ArrayList() : result);

		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}

	@PostMapping("/comment/list")
	public ResponseEntity<Message> commentList(@RequestBody BoardComment param, Authentication auth) {

		List<HashMap> result = boardService.list(param);

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		message.setStatus(HttpStatus.OK);

		message.setMessage(null == result ? "error" : "ok");
		message.setData(null == result ? new ArrayList() : result);

		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}

	@PostMapping("/comment/save")
	public ResponseEntity<Message> saveComment(@RequestBody BoardComment param, Authentication auth) {

		List<HashMap> result = boardCommentService.save(param, auth);

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		message.setStatus(HttpStatus.OK);

		message.setMessage(null == result ? "error" : "ok");
		message.setData(null == result ? new ArrayList() : result);

		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}

	@PostMapping("/comment/delete")
	public ResponseEntity<Message> deleteComment(@RequestBody BoardComment param, Authentication auth) {

		List<HashMap> result = boardCommentService.delete(param, auth);

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		message.setStatus(HttpStatus.OK);

		message.setMessage(null == result ? "error" : "ok");
		message.setData(null == result ? new ArrayList() : result);

		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}

}
