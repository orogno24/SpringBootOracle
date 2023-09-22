package kopo.poly.controller;

import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Controller
public class UserInfoController {

    private final IUserInfoService userInfoService;

    @GetMapping(value = "userRegForm")
    public String userRegForm() {
        log.info(this.getClass().getName() + ".user/userRegForm");

        return "/user/userRegForm";
    }

    @ResponseBody
    @PostMapping(value = "getUserIdExists")
    public UserInfoDTO getUserExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getUserIdExists 시작!");

        String userId = CmmUtil.nvl(request.getParameter("userId"));

        log.info("userId : " + userId);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserId(userId);

        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserIdExists(pDTO)).orElseGet(UserInfoDTO::new);

        log.info(this.getClass().getName() + ".getUserIdExists 끝!");

        return rDTO;
    }

    @ResponseBody
    @PostMapping(value = "getEmailExists")
    public UserInfoDTO getEmailExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getEmailExists 시작!");

        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("email : " + email);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setEmail(EncryptUtil.encAES128CBC(email));

        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getEmailExists(pDTO)).orElseGet(UserInfoDTO::new);

        log.info(this.getClass().getName() + ".getEmailExists 끝!");

        return rDTO;
    }

    @ResponseBody
    @PostMapping(value = "insertUserInfo")
    public MsgDTO insertUserInfo(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".insertUserInfo 시작!");

        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        UserInfoDTO pDTO = null;

        try {
            String userId = CmmUtil.nvl(request.getParameter("userId"));
            String userSeq = CmmUtil.nvl(request.getParameter("userSeq"));
            String userName = CmmUtil.nvl(request.getParameter("userName"));
            String password = CmmUtil.nvl(request.getParameter("password"));
            String email = CmmUtil.nvl(request.getParameter("email"));
            String addr1 = CmmUtil.nvl(request.getParameter("addr1"));
            String addr2 = CmmUtil.nvl(request.getParameter("addr2"));

            log.info("userId : " + userId);
            log.info("userSeq : " + userSeq);
            log.info("userName : " + userName);
            log.info("password : " + password);
            log.info("email : " + email);
            log.info("addr1 : " + addr1);
            log.info("addr2 : " + addr2);

            pDTO = new UserInfoDTO();

            pDTO.setUserId(userId);
            pDTO.setUserSeq(userSeq);
            pDTO.setUserName(userName);

            pDTO.setPassword(EncryptUtil.encHashSHA256(password));

            pDTO.setEmail(EncryptUtil.encAES128CBC(email));
            pDTO.setAddr1(addr1);
            pDTO.setAddr2(addr2);

            res = userInfoService.insertUserInfo(pDTO);

            log.info("회원가입 결과(res) : " + res);

            if (res == 1) {
                msg = "회원가입되었습니다.";
            } else if (res == 2) {
                msg = "이미 가입된 아이디입니다.";
            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";
            }
        } catch (Exception e) {

            msg = "실패하였습니다. : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);

            log.info(this.getClass().getName() + ".insertUserInfo 끝!");
        }

        return dto;
    }

    @GetMapping(value = "userList")
    public String userList(ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".userList 시작!");

        List<UserInfoDTO> rList = Optional.ofNullable(userInfoService.getUserList())
                .orElseGet(ArrayList::new);

        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".userList End!");

        return "/user/userList";
    }

    @GetMapping(value = "userInfo")
    public String userInfo(HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".userInfo Start!");
        String userId = CmmUtil.nvl(request.getParameter("userId"));
        log.info("userId : " + userId);
        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserId(userId);
        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserInfo(pDTO, true))
                .orElseGet(UserInfoDTO::new);
        model.addAttribute("rDTO", rDTO);
        log.info(this.getClass().getName() + ".userInfo End!");
        return "/user/userInfo";
    }

    @GetMapping(value = "loginInfo")
    public String loginInfo(ModelMap model, HttpSession session) throws Exception {
        MsgDTO dto = null;
        String msg = "";

        log.info(this.getClass().getName() + ".loginInfo Start!");
        String SS_USER_ID = (String) session.getAttribute("SS_USER_ID");
        log.info("SS_USER_ID : " + SS_USER_ID);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserId(SS_USER_ID);

        if (CmmUtil.nvl(pDTO.getUserId()).length() == 0) {
            msg = "아이디와 비밀번호가 올바르지 않습니다.";
            dto.setMsg(msg);
            return "user/login";
        }

        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserInfo(pDTO, true))
                .orElseGet(UserInfoDTO::new);
        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".loginInfo End!");

        return "user/loginInfo";
    }

    @GetMapping(value = "login")
    public String login() {
        log.info(this.getClass().getName() + ".user/login Start!");

        log.info(this.getClass().getName() + ".user/login End!");

        return "/user/login";
    }

    @ResponseBody
    @PostMapping(value = "loginProc")
    public MsgDTO loginProc(HttpServletRequest request, HttpSession session) {
        log.info(this.getClass().getName() + ".loginProc Start!");

        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        UserInfoDTO pDTO = null;

        try {
            String userId = CmmUtil.nvl(request.getParameter("userId"));
            String password = CmmUtil.nvl(request.getParameter("password"));

            log.info("userId : " + userId);
            log.info("password : " + password);

            pDTO = new UserInfoDTO();

            pDTO.setUserId(userId);

            pDTO.setPassword(EncryptUtil.encHashSHA256(password));

            UserInfoDTO rDTO = userInfoService.getLogin(pDTO);

            if (CmmUtil.nvl(rDTO.getUserId()).length() > 0) {

                res = 1;

                msg = "로그인이 성공했습니다.";

                session.setAttribute("SS_USER_ID", userId);
                session.setAttribute("SS_USER_NAME", CmmUtil.nvl(rDTO.getUserName()));
            } else {
                msg = "아이디와 비밀번호가 올바르지 않습니다.";
            }
        } catch (Exception e) {
            msg = "시스템 문제로 로그인이 실패했습니다.";
            res = 2;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);

            log.info(this.getClass().getName() + ".loginProc End!");
        }

        return dto;
    }

    @GetMapping(value = "loginResult")
    public String loginResult() {
        log.info(this.getClass().getName() + ".user/loginResult Start!");

        log.info(this.getClass().getName() + ".user/loginResult End!");

        return "/user/loginResult";
    }

    @ResponseBody
    @PostMapping(value = "userProc")
    public MsgDTO userProc(HttpServletRequest request, HttpSession session) {
        log.info(this.getClass().getName() + ".userProc Start!");

        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        UserInfoDTO pDTO = null;

        try {
            String SS_USER_ID = (String) session.getAttribute("SS_USER_ID");

            log.info("SS_USER_ID : " + SS_USER_ID);

            if (SS_USER_ID != null) {

                res = 1;

                msg = "현재 로그인 상태입니다.";

            } else {
                msg = "로그인 해주세요.";
            }
        } catch (Exception e) {
            msg = "시스템 문제로 로그인이 실패했습니다.";
            res = 2;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);

            log.info(this.getClass().getName() + ".userProc End!");
        }

        return dto;
    }

}
