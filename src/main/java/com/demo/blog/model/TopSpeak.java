package com.demo.blog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_topspeak")
public class TopSpeak {
	@Id
    @GeneratedValue
    private Long id; //自增id
	private Long user_id; 
	private Long speak_id;
	
	public TopSpeak() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getSpeak_id() {
		return speak_id;
	}

	public void setSpeak_id(Long speak_id) {
		this.speak_id = speak_id;
	}
	
	
}
