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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Table(name="Community")
@Getter
@Setter
@Entity
public class Community {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique=true, nullable = false)
	private Long id;
	
	
	@Column(length=30, nullable = false)
	private String title;
	
	@Column(length=100, nullable = false)
	private String summary;
	
	@Column(length=2, nullable = true)
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
