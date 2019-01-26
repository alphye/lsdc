package com.lishengzn.lsdc.kernel.as.controller;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.lsdc.kernel.as.websocket.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequestMapping("login")
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("toLogin")
    public String toLogin(){
        return "/login.jsp";
    }

    @RequestMapping("login")
    public String login(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        String accountID = "actid_"+ UUID.randomUUID();
        session.setAttribute(Constants.SKEY_ACCOUNT_ID, accountID);
        return "/main.jsp";
    }
}
