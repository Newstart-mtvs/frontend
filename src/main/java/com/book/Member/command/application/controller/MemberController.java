package com.book.Member.command.application.controller;
import com.book.Member.command.application.dto.KakaoProfileDTO;
import com.book.Member.command.application.dto.MemberDTO;
import com.book.Member.command.application.service.LoginService;
import com.book.Member.command.application.dto.OauthTokenDTO;
import com.book.Member.command.application.service.RealLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController {
    private final RealLoginService realLoginService;

    @Autowired
    public MemberController(RealLoginService realLoginService) {
        this.realLoginService = realLoginService;
    }

    @GetMapping("/kakao/callbacka")
   // https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=9d672256fe1b571e038f23b57e69b7&redirect_uri=http://localhost:8888/kakao/callback
    public void getKakaoCode(@RequestParam("code") String code, HttpServletResponse response) {

        /* 인가 코드로 액세스 토큰 발급 */
        OauthTokenDTO oauthToken = LoginService.getAccessToken(code);

        System.out.println(oauthToken);
    }

    @Description("회원이 소셜 로그인을 마치면 자동으로 실행되는 API입니다. 인가 코드를 이용해 토큰을 받고, 해당 토큰으로 사용자 정보를 조회합니다." +
            "사용자 정보를 이용하여 서비스에 회원가입합니다.")
    @GetMapping("/kakao/callback")
    @ResponseBody
    public String getAuthorization( @RequestParam("code") String code, HttpSession session) {
        /* 인가 코드로 액세스 토큰 발급 */
        OauthTokenDTO oauthToken = LoginService.getAccessToken(code);
        KakaoProfileDTO jisu = LoginService.findKakaoProfile(oauthToken.getAccess_token());
        System.out.println(oauthToken);
        System.out.println(jisu.getProperties().getNickname());
        System.out.println(jisu.getKakao_account().getEmail());
        session.setAttribute("access_token", oauthToken.getAccess_token());
        session.setAttribute("id",jisu.getKakao_account().getEmail());
        session.setAttribute("nickname",jisu.getProperties().getNickname());

        realLoginService.duplicate(jisu.getKakao_account().getEmail());
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(jisu.getId());
        memberDTO.setMemberNickname(jisu.getProperties().getNickname());
        memberDTO.setMemberEmail(jisu.getKakao_account().getEmail());
        memberDTO.setIsDeleted("0");
        realLoginService.saveRegister(memberDTO);

        return "templates/index.html";

    }

    @GetMapping("/logininfo")
    public String getAuthorization( Model model, HttpSession session) {
        /* 인가 코드로 액세스 토큰 발급 */
        String email = (String)session.getAttribute("id");
        String nickname = (String)session.getAttribute("nickname");

        model.addAttribute("ida",nickname);
        model.addAttribute("nicknamea",email);

        return "logininfo";

    }

    @Description("회원이 소셜 로그인을 마치면 자동으로 실행되는 API입니다. 인가 코드를 이용해 토큰을 받고, 해당 토큰으로 사용자 정보를 조회합니다." +
            "사용자 정보를 이용하여 서비스에 회원가입합니다.")
    @GetMapping("/kakao/logout")
    @ResponseBody
    public String Logout(HttpSession session, String code) {
        /* 인가 코드로 액세스 토큰 발급 */
        String access_token = (String)session.getAttribute("access_token");
        if(access_token != null && !"".equals(access_token)){
            session.removeAttribute("access_Token");
            session.removeAttribute("userId");
            session.invalidate();
            return "https://kauth.kakao.com/oauth/logout?client_id=9d672256fe1b571e038f23b57e69b78e&logout_redirect_uri=http://localhost:8888";

        }else{
            //return "redirect:/";
            System.out.println("access_Token is null");
            return null;
        }




    }











}
