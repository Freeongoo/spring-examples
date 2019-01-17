package com.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class BaseController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome() {
        return "welcome public";
    }

    @RequestMapping(value = "/secret", method = RequestMethod.GET)
    public String secret() {
        return "secret info";
    }
}
