package com.kaist.dto;

import lombok.Data;

@Data
public class CommunityUserDTO {
	
	private Long userId;
	private Long communityId;
	private String nickName;
	private String userName;
	private String email;
	public String image;
}
