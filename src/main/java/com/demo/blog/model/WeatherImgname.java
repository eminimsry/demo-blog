package com.demo.blog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_weather")
public class WeatherImgname {
	@Id
    @GeneratedValue
	private Long id;
	private String weathername;
	private String imgurl;
	
	public WeatherImgname() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWeathername() {
		return weathername;
	}

	public void setWeathername(String weathername) {
		this.weathername = weathername;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	@Override
	public String toString() {
		return "WeatherImgname [id=" + id + ", weathername=" + weathername + ", imgurl=" + imgurl + "]";
	}

	
}
