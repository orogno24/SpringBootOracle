package kopo.poly.controller;

import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/redirect")
    public String redirectPage(HttpServletRequest request, ModelMap modelMap) throws Exception {
        log.info(this.getClass().getName() + ".redirect 페이지 보여주는 함수 실행");
        String msg = CmmUtil.nvl(request.getParameter("msg"), "로그인해주세요.");
        modelMap.addAttribute("msg", msg);
        modelMap.addAttribute("url", "/user/login");
        return "/redirect";
    }

    @GetMapping(value = "login2")
    public String login2() {
        log.info(this.getClass().getName() + ".user/login2 Start!");
        log.info(this.getClass().getName() + ".user/login2 End!");
        return "/user2/login2";
    }

}
