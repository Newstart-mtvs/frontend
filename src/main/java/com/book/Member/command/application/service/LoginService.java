package com.book.Member.command.application.service;

import com.book.Member.command.application.dto.KakaoProfileDTO;
import com.book.Member.command.application.dto.LogoutDTO;
import com.book.Member.command.application.dto.OauthTokenDTO;
import com.book.Member.command.domain.repository.LoginRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LoginService(LoginRepository loginRepository, ModelMapper modelMapper) {
        this.loginRepository = loginRepository;
        this.modelMapper = modelMapper;
    }

    public static OauthTokenDTO getAccessToken(String code) {

        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "9d672256fe1b571e038f23b57e69b78e");
        params.add("redirect_uri", "http://localhost:8888/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OauthTokenDTO oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthTokenDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return oauthToken;
    }

    public static KakaoProfileDTO findKakaoProfile(String accessToken) {

        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());


        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=urf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        KakaoProfileDTO kakaoProfileDTO = new KakaoProfileDTO();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            kakaoProfileDTO = objectMapper.readValue(kakaoProfileResponse.getBody(),
                    KakaoProfileDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return kakaoProfileDTO;
    }


    public static LogoutDTO Logout(String accessToken) {

        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=urf-8");
        headers.add("authorization", "Bearer " + accessToken);



        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);
            ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                    "https://kapi.kakao.com/v1/user/logout",
                    HttpMethod.POST,
                    kakaoProfileRequest,
                    String.class
            );


            LogoutDTO logoutDTO = new LogoutDTO();
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                logoutDTO = objectMapper.readValue(kakaoProfileResponse.getBody(),
                        LogoutDTO.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        return logoutDTO;
    }
    public static LogoutDTO logout(String accessToken) {

        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpHeaders headers = new HttpHeaders();
        headers.add("client_id", "9d672256fe1b571e038f23b57e69b78e");
        headers.add("logout_redirect_uri", "");



        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v1/user/unlink",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );


        LogoutDTO logoutDTO = new LogoutDTO();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            logoutDTO = objectMapper.readValue(kakaoProfileResponse.getBody(),
                    LogoutDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return logoutDTO;
    }
    public static LogoutDTO unlink(String accessToken) {

        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=urf-8");
        headers.add("authorization", "Bearer " + accessToken);



        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v1/user/unlink",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );


        LogoutDTO logoutDTO = new LogoutDTO();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            logoutDTO = objectMapper.readValue(kakaoProfileResponse.getBody(),
                    LogoutDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return logoutDTO;
    }

}
