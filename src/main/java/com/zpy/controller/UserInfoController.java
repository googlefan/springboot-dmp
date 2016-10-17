package com.zpy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2016/10/17.
 */
@RestController
public class UserInfoController {
    @RequestMapping("/")
    @ResponseBody
    ModelAndView home() {
        return new ModelAndView("index.html");
    }

    @RequestMapping("/show")
    public String show() {
        StringBuffer sb = new StringBuffer();
        sb.append("age,population\n");
        sb.append("<5,5\n");
        sb.append("5-13,8\n");
        sb.append("14-17,3\n");
        sb.append("18-24,6\n");
        sb.append("25-44,19\n");
        sb.append("45-64,19\n");
        sb.append("≥65,35");
        return sb.toString();
    }
}
