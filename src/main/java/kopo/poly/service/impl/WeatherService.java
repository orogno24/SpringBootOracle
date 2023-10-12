package kopo.poly.service.impl;

import kopo.poly.dto.WeatherDTO;
import kopo.poly.service.IWeatherService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class WeatherService implements IWeatherService {

    @Override
    public List<WeatherDTO> toDayWeather() throws Exception {

        log.info(this.getClass().getName() + ".toDayWeather 시작!");

        int res = 0;

        String url = "https://weather.naver.com";

        Document doc = null;

        doc = Jsoup.connect(url).get();


        Elements element = doc.select("div.weather_now");
        Elements locationElement = doc.select("strong.location_name");

        Iterator<Element> weatherDescription = element.select("span.weather").iterator();
        Iterator<Element> currentTemperature = element.select("strong.current").iterator();
        Iterator<Element> currentLocation = locationElement.iterator();

        List<WeatherDTO> pList = new ArrayList<>();

        WeatherDTO pDTO = new WeatherDTO();

        String locationText = weatherDescription.next().text();
        String status1Text = currentTemperature.next().text();
        String status2Text = currentLocation.next().text();

        log.info("location : " + locationText);
        log.info("status1 : " + status1Text);
        log.info("status2 : " + status2Text);

        pDTO.setLocation(locationText);
        pDTO.setStatus1(status1Text.substring(5));
        pDTO.setStatus2(status2Text);

        pList.add(pDTO);

        log.info(this.getClass().getName() + ".toDayWeather End!");

        return pList;
    }
}
