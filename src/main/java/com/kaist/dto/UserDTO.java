package com.kaist.dto;

import lombok.Data;

@Data
public class UserDTO {
	
	private Long id;
	private String userId;
	private String email;
	private String userName;
	private String Role;
}
