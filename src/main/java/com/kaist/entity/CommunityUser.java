package com.kaist.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Builder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;


@Table(name="CommunityUser")
@Entity
@Getter
@Setter
public class CommunityUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique=true, nullable = false)
	private Long id;
	
	private Long communityId;
	
	private Long userId;
	
	private Long sortNo;
	
	@Column(length=100, nullable = true)
	private String nickName;
	
	@Column(length = 2, nullable = true)
	@ColumnDefault("'C'")
	private String status;
	
	@CreationTimestamp
	private Timestamp createdAt;
	
	@Column( name = "image")
	@Lob
	public byte[] image;
	
	@Transient
	public String imageStr;
	
	@Column(name = "type")
	private String type;
}
