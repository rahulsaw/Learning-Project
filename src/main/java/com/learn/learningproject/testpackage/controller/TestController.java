package com.learn.learningproject.testpackage.controller;


import com.learn.learningproject.testpackage.entity.Test;
import com.learn.learningproject.testpackage.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
Rahul sawaria created on 11/05/21
*/

@RestController
@RequestMapping("test/")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @Value("${test.prop:abc}")
    private String testPropertyValue;

    @GetMapping("hello")
    public Collection<String> sayHello() {
        log.info("inside the test controller say hello method....");
        log.info("test.prop property value is : {}", testPropertyValue);
        log.error("checking log level by printing error log");
        return IntStream.range(0, 10)
                .mapToObj(i -> "Hello number  " + i).collect(Collectors.toList());
    }

    @PostMapping("save")
    public String saveData(@RequestBody Test test) {
        log.info("inside the test controller save method....");
        log.info(test.toString());
        testService.save(test);
        return "successfully save";
    }

    @GetMapping("get")
    public List<Test> getData() {
        log.info("inside the test controller get method....");
        return testService.get();
    }

    @PostMapping("email")
    public String sendEmail(@RequestParam String recipient) {
        log.info("inside the test controller sendEmail method....");
        testService.testEmailService(recipient);
        return "Email send successfully to "+recipient;
    }

}
