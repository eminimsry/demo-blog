package com.demo.blog.vo;

public class Weather {
	private String city;	//城市
	private String temperature;		//温度
	private String humidity;	//湿度
	private String info;		//天气
	private String futureweather;     //未来天气
	private String direct;		//风向
	private String power;		//风力强度
	private String aqi;		//空气清新指数
	private String weatherImgUrl;  //调用天气图片的路径
	
	
	public Weather() {
		super();
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getFutureweather() {
		return futureweather;
	}
	public void setFutureweather(String futureweather) {
		this.futureweather = futureweather;
	}
	public String getDirect() {
		return direct;
	}
	public void setDirect(String direct) {
		this.direct = direct;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getAqi() {
		return aqi;
	}
	public void setAqi(String aqi) {
		this.aqi = aqi;
	}
	
	public String getWeatherImgUrl() {
		return weatherImgUrl;
	}
	public void setWeatherImgUrl(String weatherImgUrl) {
		this.weatherImgUrl = weatherImgUrl;
	}
	@Override
	public String toString() {
		return "Weather [city=" + city + ", temperature=" + temperature + ", humidity=" + humidity + ", info=" + info
				+ ", futureweather=" + futureweather + ", direct=" + direct + ", power=" + power + ", aqi=" + aqi
				+ ", weatherImgUrl=" + weatherImgUrl + "]";
	}
	
	
	
	
	
	
}
