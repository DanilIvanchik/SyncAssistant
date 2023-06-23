package com.example.sync.controller;

import com.example.sync.dto.GPTRequest;
import com.example.sync.dto.GPTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bot")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt){
        GPTRequest request = new GPTRequest(model, prompt);
        GPTResponse response = restTemplate.postForObject(apiUrl,request,GPTResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }
}
