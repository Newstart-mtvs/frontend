package com.book.API.command.application.controller;

import com.book.API.command.application.dto.APIDTO;
import com.book.API.command.infrastructure.service.RESTTEMPLATE;
import com.nimbusds.jose.shaded.json.JSONArray;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class APIController {
    private final String BASE_URL = "https://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbhorry71729001&output=js&Query=aladin";

    private final RestTemplate restTemplate;

    @Autowired
    public APIController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/listBook")
    public Map<String, Object> callApi(@RequestParam(value = "pbNum", required = false) String query) {

        final HttpHeaders headers = new HttpHeaders();
        final HttpEntity<?> entity = new HttpEntity<>(headers);

        URI builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("Query", query)
                .queryParam("output", "js")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<Map<String, Object>>() {
        };

        ResponseEntity<Map<String, Object>> result = restTemplate.exchange(builder, HttpMethod.GET, entity, responseType);
        System.out.println(result);
        return result.getBody();
    }

    @GetMapping("/listBook2") //왜 되는건지 알아보기 알라딘 api
    public ResponseEntity<?> fetch() throws UnsupportedEncodingException {

        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        restTemplate.getInterceptors().add((request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request,body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Map> resultMap = restTemplate.exchange(BASE_URL, HttpMethod.GET, entity, Map.class);
        System.out.println(resultMap.getBody());
        System.out.println(resultMap.getStatusCode());
        System.out.println(resultMap);
        return resultMap;

    }


    @GetMapping("/listBook3")
    public String hello() {

        // uri 주소 생성
        URI uri = UriComponentsBuilder
                .fromUriString(BASE_URL) //http://localhost에 호출
                .queryParam("Query", "steve")
                .queryParam("Content-Type","application/json; charset=UTF-8.")// query parameter가 필요한 경우 이와 같이 사용
                .encode()
                .build()
                .toUri();

        System.out.println(uri.toString());

        RestTemplate restTemplete = new RestTemplate();
        restTemplete.getInterceptors().add((request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request,body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });

        ResponseEntity<Map> result = restTemplete.getForEntity(uri, Map.class);
        // entity로 데이터를 가져오겠다(Get)~~
        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());

        return null;
    }

}