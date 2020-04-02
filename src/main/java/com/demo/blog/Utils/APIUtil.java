package com.demo.blog.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import com.demo.blog.vo.Weather;

import net.sf.json.JSONObject;

public class APIUtil {
	public static String separator = System.getProperty("file.separator");
	//獲取bin下Setting.ini配置文件的目錄
	public static String basepath = System.getProperty("user.dir")
//	+separator+"main"+separator+"resources"+separator+"templates"+separator+"weatherFile"+
			+separator+"WeatherSetting.md";
	
	//加載Setting.ini配置檔內容
	public static IniEditor inieditor = getSettingConfig();

	//主方法main用于接口测试
	public static void main(String[] args) throws Exception {
//		Weather weather = returnWeather("223.104.212.171");
//		System.out.println(weather.toString());
		
		System.out.println(basepath);
//		Map<String,Object> map = getWeatherByCity(getCityByIP("42.227.160.255"));
//		System.out.println(map);
//		Map<String,Object> map0 = (Map<String, Object>) map.get("result");
//		System.out.println(map0);
		
//		String city = getCityByIP("42.227.160.255");
//		System.out.println(city);
		
//		String strUrl="http://apis.juhe.cn/ip/ipNew";
//        String strUrlValue="ip=223.104.213.119&key=022287affefd4e498740d2d107efbe4c";
//        String json = "";
//        String url = strUrl+"?"+strUrlValue;
//        //json=HttpClientUtil.doGet(url);
//        json=HttpGetPost.sendGet(strUrl, strUrlValue);
//        System.out.println(json);
        
	}
	
	
	//API接受IP地址，返回该IP地址对应城市的天气Weather类
	public static Weather returnWeather(String IP) {
		String headUrl = "https://cdn.heweather.com/cond_icon/";
		String suffix = ".png";
		Weather weather = new Weather();
		String cityname = getCityByIP(IP);
		if(cityname.equals("获取城市名称失败")) {
			weather = setNullWeather();
			weather.setCity("获取失败");
			System.out.println(weather.toString());
			return weather;
		}
		Map<String,Object> map = getWeatherByCity(cityname);
		System.out.println(map.toString());
		if(map.get("error_code").toString().contains("失败")) {
			weather = setNullWeather();
			System.out.println(weather);
			return weather;
		}
		if(map.isEmpty()) {//如果沒有查詢到
			System.out.println(weather);
			weather = setNullWeather();
			return weather;
		}else if(map.get("reason").equals("查询成功!")) {
			Map<String,Object> mapresult = (Map<String, Object>) map.get("result");
			if(!mapresult.get("city").equals("")&&mapresult.get("city")!=null) {
				Map<String,Object> maprealtime = (Map<String, Object>) mapresult.get("realtime");
				ArrayList listfuture = (ArrayList) mapresult.get("future");
				System.out.println(listfuture);
				Map<String,Object> maptoday = (Map<String, Object>) listfuture.get(0);
				
				weather.setCity(mapresult.get("city").toString());
				weather.setTemperature(maprealtime.get("temperature").toString());
				weather.setHumidity(maprealtime.get("humidity").toString());
				weather.setInfo(maprealtime.get("info").toString());
				weather.setFutureweather(maptoday.get("weather").toString());
				weather.setDirect(maprealtime.get("direct").toString());
				weather.setPower(maprealtime.get("power").toString());
				weather.setAqi(maprealtime.get("aqi").toString());
				String imgUrlNumber = inieditor.get("SETTING", maprealtime.get("info").toString());
				if(imgUrlNumber.equals("")||imgUrlNumber==null) {
					imgUrlNumber = "100";
				}
				String imgUrl = headUrl+imgUrlNumber+suffix;
				weather.setWeatherImgUrl(imgUrl);
			}else {
				weather = setNullWeather();
				System.out.println(weather);
				return weather;
			}
		}else {
			return weather;
		}
		return weather;
	}
	
	
	
	
	//根据IP获取所在城市的名称
	private static String getCityByIP(String IP) {
		String city ="";
		String jsonStr = "";
		String strUrl = "http://apis.juhe.cn/ip/ipNew";
		String srtVlaue = "ip="+IP+"&key=022287affefd4e498740d2d107efbe4c";
		jsonStr = HttpGetPost.sendGet(strUrl,srtVlaue);
		System.out.println(jsonStr);
		if(jsonStr.equals("请求超时")) {
			return "获取城市名称失败";
		}
		JSONObject json = JSONObject.fromObject(jsonStr);
		String resultcode = (String) json.get("resultcode");
		if(resultcode.equals("200")) {//接口請求成功
			JSONObject result = (JSONObject) json.get("result");
			city = (String) result.get("City");
		}else {
			city = "所在城市获取失败";
		}
		if(city.contains("市")) {
			city = city.substring(0, 2);//只截取前两位汉字
		}
		System.out.println("根据IP:"+IP+"获取城市名称为:"+city);
		return city;
	}
	
	//根据城市名称获取该城市的天气状况
	private static Map<String,Object> getWeatherByCity(String City) {
		Map<String,Object> hashmap = new HashMap<String, Object>();
		String jsonStr = "";
		String strUrl = "http://apis.juhe.cn/simpleWeather/query";
		String srtVlaue = "city="+City+"&key=93e7573d94a0b85b2ac38ded5aef41e0";
		jsonStr = HttpGetPost.sendGet(strUrl,srtVlaue);
		System.out.println(jsonStr);
		JSONObject json = JSONObject.fromObject(jsonStr);
		String reason = (String) json.get("reason");
		if(reason.equals("查询成功!")) {
			hashmap = JsonUtil.parseJSONstr2Map(jsonStr);
			
		}else {
			hashmap.put("error_code", "获取"+City+"天气失败");
		}
		
		return hashmap;
	}

	//設置一個各個屬性都為""的weather
	public static Weather setNullWeather() {
		Weather weather = new Weather();
		weather.setCity("未知");
		weather.setAqi("未知");
		weather.setDirect("未知");
		weather.setFutureweather("未知");
		weather.setHumidity("未知");
		weather.setInfo("未知");
		weather.setPower("未知");
		weather.setTemperature("未知");
		weather.setWeatherImgUrl("");
		return weather;
	}
	
	//加載Setting.ini配置檔內容
    public static IniEditor getSettingConfig(){
    	IniEditor inieditor = new IniEditor();
		try {
			inieditor.load(basepath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inieditor;
    }
	
}
