package com.cloud.weather.service.impl;

import com.cloud.weather.service.WeatherDataService;
import com.cloud.weather.vo.Weather;
import com.cloud.weather.vo.WeatherResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    private final static String WEATHER_URI = "https://www.tianqiapi.com/api/?version=v1&appid=36277646&appsecret=Im66r7zN&";
    private final static Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate; //只有Spring web环境才好用
    @Autowired
    private RedisUtilsServiceImpl redisUtilsService;

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

        String key = uri;
        // 先查缓存，如果缓存有直接取缓存中数据
        //如果缓存没有，则去调用第三方接口
        if(redisUtilsService.get(key) != null){
            logger.info("缓存命中");
            return (WeatherResponse)redisUtilsService.get(key);
        }
        logger.info("缓存没有命中");
        WeatherResponse weatherResponse = null;
//      WeatherResponse weatherResponse1 = restTemplate.getForEntity(uri,WeatherResponse.class).getBody();
        ResponseEntity<String> respStr =  restTemplate.getForEntity(uri,String.class);
        // json字符串转Object
        ObjectMapper objectMapper = new ObjectMapper();
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
                redisUtilsService.set(key,weatherResponse,1L,TimeUnit.HOURS);
            }else{
                weatherResponse.setStatus(500);
                weatherResponse.setDesc("失败");
            }
//            weatherResponse = objectMapper.readValue(strBody,WeatherResponse.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return weatherResponse;

    }

}
