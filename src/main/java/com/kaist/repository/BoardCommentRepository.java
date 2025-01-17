package com.kaist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaist.entity.BoardComment;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long>{

}
