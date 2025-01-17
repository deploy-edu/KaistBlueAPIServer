package com.kaist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaist.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, Long>{

}
