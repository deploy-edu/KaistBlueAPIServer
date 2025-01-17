package com.kaist.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name="User")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
	
	@Column(length = 30, nullable = false)
	private String userId;
	
	@Column(length = 15, nullable = false)
	private String userName;
	
	@Column(length = 100, nullable = true)
	private String uPassword;
	
	@Column(length = 10, nullable = true)
	private String status;
	
	@Column(length = 30, nullable = true)
	private String email;
	
	@Column(length = 100, nullable = true)
	private String token;
	
	@CreationTimestamp // 시간 자동입력
	private Timestamp crateDate;
	
	
	@Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String userId, String uPassword, String userName, String email, Role role) {
        this.userId = userId;
        this.uPassword = uPassword;
        this.userName = userName;
        this.email = email;
        this.role = role;
    }
}
