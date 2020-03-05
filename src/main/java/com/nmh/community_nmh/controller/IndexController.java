package com.nmh.community_nmh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author niminui
 * @date 2020/3/4 11:59
 */
@Controller
public class HelloController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

}