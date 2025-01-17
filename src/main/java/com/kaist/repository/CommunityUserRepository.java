package com.kaist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaist.entity.CommunityUser;

public interface CommunityUserRepository extends JpaRepository<CommunityUser, Long>{

	boolean existsByCommunityIdAndUserId(Long cid, Long uid);
	boolean existsByNickNameAndCommunityId(String nickName, Long cid);

	CommunityUser findByUserIdAndCommunityId(Long id, Long cid);
}
