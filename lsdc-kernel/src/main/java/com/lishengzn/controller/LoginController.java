package com.lishengzn.controller;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.dto.UserDto;
import com.lishengzn.websocket.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequestMapping("main")
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("toLogin")
    public String toLogin(){
        return "/login.jsp";
    }

    @RequestMapping("view")
    public String login(UserDto userDto, HttpServletRequest request){
//        LOG.info("用户登录：", JSONObject.toJSONString(userDto));
        HttpSession session = request.getSession(true);
        String accountID = "actid_"+ UUID.randomUUID();
        userDto.setAccountID(accountID);
        session.setAttribute(Constants.SKEY_ACCOUNT_ID, accountID);
        session.setAttribute("user",userDto);

        return "/main.jsp";
    }
}
