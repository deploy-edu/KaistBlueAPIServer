package com.kaist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaist.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	
	List<Board> findByCommunityId(Long id);

}
