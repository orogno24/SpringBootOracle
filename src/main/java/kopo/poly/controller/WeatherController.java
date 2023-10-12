package kopo.poly.controller;

import kopo.poly.dto.FoodDTO;
import kopo.poly.dto.WeatherDTO;
import kopo.poly.service.IFoodService;
import kopo.poly.service.IWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/weather")
@RequiredArgsConstructor
@Controller
public class WeatherController {
    private final IWeatherService weatherService;

    @GetMapping(value = "toDayWeather")
    public String collectWeather(ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".collectWeather Start!");

        List<WeatherDTO> rList = Optional.ofNullable(weatherService.toDayWeather()).orElseGet(ArrayList::new);

        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".collectWeather End!");

        return "/weather/todayWeather";
    }

}
