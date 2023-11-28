package kopo.poly.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.WeatherDTO;
import kopo.poly.dto.WeatherDailyDTO;
import kopo.poly.service.IWeatherService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.NetworkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class WeatherService implements IWeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Override
    public WeatherDTO getWeather(WeatherDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getWeather Start!");

        String lat = CmmUtil.nvl(pDTO.getLat());
        String lon = CmmUtil.nvl(pDTO.getLon());

        String apiParam = "?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric";
        log.info("apiParam " + apiParam);

        String json = NetworkUtil.get(IWeatherService.apiURL + apiParam);
        log.info("json " + json);

        Map<String, Object> rMap = new ObjectMapper().readValue(json, LinkedHashMap.class);

        Map<String, Double> current = (Map<String, Double>) rMap.get("current");

        double currentTemp = current.get("temp");
        log.info("현재 기온 : " + currentTemp);

        List<Map<String, Object>> daliyList = (List<Map<String, Object>>) rMap.get("daily");

        List<WeatherDailyDTO> pList = new LinkedList<>();

        for (Map<String, Object> dailyMap : daliyList) {
            String day = DateUtil.getLongDateTime(dailyMap.get("dt"), "yyyy-MM-dd");
            String sunrise = DateUtil.getLongDateTime(dailyMap.get("sunrise"));
            String sunset = DateUtil.getLongDateTime(dailyMap.get("sunset"));
            String moonrise = DateUtil.getLongDateTime(dailyMap.get("moonrise"));
            String moonset = DateUtil.getLongDateTime(dailyMap.get("moonset"));

            log.info("-------------------------------------------------");
            log.info("today : " + day);
            log.info("해뜨는 시간 : " + sunrise);
            log.info("해지는 시간 : " + sunset);
            log.info("달뜨는 시간 : " + moonrise);
            log.info("달지는 시간 : " + moonset);

            Map<String, Double> dailyTemp = (Map<String, Double>) dailyMap.get("temp");

            String dayTemp = String.valueOf(dailyTemp.get("day"));
            String dayTempMax = String.valueOf(dailyTemp.get("max"));
            String dayTempMin = String.valueOf(dailyTemp.get("min"));

            log.info("평균 기온 : " + dayTemp);
            log.info("최고 기온 : " + dayTempMax);
            log.info("최저 기온 : " + dayTempMin);

            WeatherDailyDTO wdDTO = new WeatherDailyDTO();

            wdDTO.setDay(day);
            wdDTO.setSunrise(sunrise);
            wdDTO.setSunset(sunset);
            wdDTO.setMoonrise(moonrise);
            wdDTO.setMoonset(moonset);
            wdDTO.setDayTemp(dayTemp);
            wdDTO.setDayTempMax(dayTempMax);
            wdDTO.setDayTempMin(dayTempMin);

            pList.add(wdDTO);

            wdDTO = null;
        }

        WeatherDTO rDTO = new WeatherDTO();

        rDTO.setLat(lat);
        rDTO.setLon(lon);
        rDTO.setCurrentTemp(currentTemp);
        rDTO.setDailyList(pList);

        log.info(this.getClass().getName() + ".getWeather End!");

        return rDTO;
    }
}
