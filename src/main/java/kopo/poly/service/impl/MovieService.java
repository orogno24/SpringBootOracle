package kopo.poly.service.impl;

import kopo.poly.dto.MovieDTO;
import kopo.poly.persistance.mapper.IMovieMapper;
import kopo.poly.service.IMovieService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MovieService implements IMovieService {

    private final IMovieMapper movieMapper;

    @Transactional //update, delete 등에 사용
    @Override
    public int collectMovieRank() throws Exception {
        log.info(this.getClass().getName() + ".collectMovieRank Start!");

        String collectTime = DateUtil.getDateTime("yyyyMMdd");

        MovieDTO pDTO = new MovieDTO();
        pDTO.setCollectTime(collectTime);

        movieMapper.deleteMovieInfo(pDTO);

        pDTO = null;

        int res = 0;

        String url = "http://www.cgv.co.kr/movies/";

        Document doc = null;

        doc = Jsoup.connect(url).get();

        Elements element = doc.select("div.sect-movie-chart");

        Iterator<Element> movie_rank = element.select("strong.rank").iterator();
        Iterator<Element> movie_name = element.select("strong.title").iterator();
        Iterator<Element> movie_reserve = element.select("strong.percent span").iterator();
        Iterator<Element> score = element.select("span.percent").iterator();
        Iterator<Element> open_day = element.select("span.txt-info").iterator();

        while (movie_rank.hasNext()) {

            pDTO = new MovieDTO();

            pDTO.setCollectTime(collectTime);

            String rank = CmmUtil.nvl(movie_rank.next().text()).trim();
            pDTO.setMovieRank(rank.substring(3, rank.length()));

            pDTO.setMovieNm(CmmUtil.nvl(movie_name.next().text()).trim());

            pDTO.setMovieReserve(CmmUtil.nvl(movie_reserve.next().text()).trim());
            pDTO.setScore(CmmUtil.nvl(score.next().text()).trim());

            pDTO.setOpenDay(CmmUtil.nvl(open_day.next().text()).trim().substring(0, 10));

            pDTO.setRegId("admin");

            res += movieMapper.insertMovieInfo(pDTO);

        }

        log.info(this.getClass().getName() + ".collectMovieRank End!");

        return res;
    }

    @Override
    public List<MovieDTO> getMovieInfo() throws Exception {

        log.info(this.getClass().getName() + ".getMovieInfo End!");

        String collectTime = DateUtil.getDateTime("yyyyMMdd");

        MovieDTO pDTO = new MovieDTO();
        pDTO.setCollectTime(collectTime);

        List<MovieDTO> rList = movieMapper.getMovieInfo(pDTO);

        log.info(this.getClass().getName() + ".getMovieInfo End!");

        return rList;
    }
}