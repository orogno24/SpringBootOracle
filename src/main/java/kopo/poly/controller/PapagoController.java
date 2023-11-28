package kopo.poly.controller;

import kopo.poly.dto.OcrDTO;
import kopo.poly.dto.PapagoDTO;
import kopo.poly.service.IOcrService;
import kopo.poly.service.IPapagoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "papago")
@RequiredArgsConstructor
@RestController
public class PapagoController {

    private final IPapagoService papagoService;


    @PostMapping(value = "detectLangs")
    public PapagoDTO detectLangs(HttpServletRequest request)
            throws Exception {

        log.info(this.getClass().getName() + ".detectLangs Start!");

        String text = CmmUtil.nvl(request.getParameter("text"));

        log.info("text : " + text);

        PapagoDTO pDTO = new PapagoDTO();
        pDTO.setText(text);

        PapagoDTO rDTO = Optional.ofNullable(papagoService.detectLangs(pDTO)).orElseGet(PapagoDTO::new);

        log.info(this.getClass().getName() + ".detectLangs End!");

        return rDTO;

    }

    @PostMapping(value = "translate")
    public PapagoDTO translate(HttpServletRequest request)
            throws Exception {

        log.info(this.getClass().getName() + ".translate Start!");

        String text = CmmUtil.nvl(request.getParameter("text"));

        log.info("text : " + text);

        PapagoDTO pDTO = new PapagoDTO();
        pDTO.setText(text);

        PapagoDTO rDTO = Optional.ofNullable(papagoService.translate(pDTO)).orElseGet(PapagoDTO::new);

        log.info(this.getClass().getName() + ".translate End!");

        return rDTO;
    }

}