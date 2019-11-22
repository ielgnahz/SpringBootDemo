package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.bean.Greeting;
import com.example.bean.HelloMessage;
import com.example.demo.User;
import com.example.service.UserService;
import com.example.util.AccessTokenDTO;
import com.example.util.HttpUtil;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class GreetingController {

    @Resource
    UserService userService;

//    @Value("${security.oauth2.client.clientId}")
//    private String clientId;
//
//    @Value("${security.oauth2.client.clientSecret}")
//    private String clientSecret;
//
//    @Value("${security.oauth2.client.redirectUri}")
//    private String redirectUri;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public GreetingController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


    @RequestMapping("/change")
    @ResponseBody
    public String chanageStatus(){
        User user = userService.findUserById(1);
        simpMessagingTemplate.convertAndSend("/topic/greetings", user);
        return "OK";
    }


//    @RequestMapping("/callback")
//    public String callback(@RequestParam String code, String state, HttpServletRequest request){
//        // 2019/11/20 redirectUri与login.html中不匹配的话accessToken会返回redirect_uri_mismatch
//        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(clientId, clientSecret, code, redirectUri, state);
//        String accessToken = HttpUtil.postHttpRequest("https://github.com/login/oauth/access_token", JSON.toJSONString(accessTokenDTO)).split("&")[0].split("=")[1];
//        System.out.println(accessToken);
//        String user = HttpUtil.getHttpRequest("https://api.github.com/user", new NameValuePair[]{new NameValuePair("access_token", accessToken)});
//        System.out.println(user);
//        request.getSession().setAttribute("user", user);
//        return "redirect:/";
//    }

}