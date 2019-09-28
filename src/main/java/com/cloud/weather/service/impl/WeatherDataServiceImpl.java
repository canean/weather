package com.cloud.weather.service.impl;

import com.cloud.weather.service.WeatherDataService;
import com.cloud.weather.vo.Weather;
import com.cloud.weather.vo.WeatherResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    private final static String WEATHER_URI = "https://www.tianqiapi.com/api/?version=v1&appid=36277646&appsecret=Im66r7zN&";

    @Autowired
    private RestTemplate restTemplate; //只有Spring web环境才好用
    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        String uri = WEATHER_URI + "cityid=" + cityId;
        return this.doGetWeather(uri);
    }

    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        String uri = WEATHER_URI + "city=" + cityName ;
        return this.doGetWeather(uri);
    }

    private WeatherResponse  doGetWeather(String uri){
        WeatherResponse weatherResponse = null;
//      WeatherResponse weatherResponse1 = restTemplate.getForEntity(uri,WeatherResponse.class).getBody();
        ResponseEntity<String> respStr =  restTemplate.getForEntity(uri,String.class);
        // json字符串转Object
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        String strBody = null;
        if(respStr.getStatusCodeValue() == 200){
            strBody =  respStr.getBody();
        }
        try {
            Weather weather = objectMapper.readValue(strBody,Weather.class);
            weatherResponse = new WeatherResponse();
            if(weather != null){
                weatherResponse.setData(weather);
                weatherResponse.setStatus(200);
                weatherResponse.setDesc("成功");
            }else{
                weatherResponse.setStatus(500);
                weatherResponse.setDesc("失败");
            }
//            weatherResponse = objectMapper.readValue(strBody,WeatherResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return weatherResponse;

    }

}
