package com.mmtap.cma.demo.controller;

import com.mmtap.cma.demo.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/do")
    public Object dotest(){
        return testService.testDb();
    }
}
