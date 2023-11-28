package kopo.poly.controller;

import kopo.poly.dto.FoodDTO;
import kopo.poly.dto.WeatherDTO;
import kopo.poly.service.IFoodService;
import kopo.poly.service.IWeatherService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/weather")
@RequiredArgsConstructor
@RestController
public class WeatherController {
    private final IWeatherService weatherService;

    @GetMapping(value = "getWeather")
    public WeatherDTO getWeather(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getWeather Start!");

        String lat = CmmUtil.nvl(request.getParameter("lat"));
        String lon = CmmUtil.nvl(request.getParameter("lon"));

        WeatherDTO pDTO = new WeatherDTO();
        pDTO.setLat(lat);
        pDTO.setLon(lon);

        WeatherDTO rDTO = weatherService.getWeather(pDTO);

        if (rDTO == null) {
            rDTO = new WeatherDTO();

        }

        log.info(this.getClass().getName() + ".getWeather End!");

        return rDTO;
    }

}
