package com.learn.learningproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
Rahul sawaria created on 11/05/21
*/
@RestController
public class TestController {

    public final static Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/hello")
    public Collection<String> sayHello() {
        log.info("inside the test controller say hello method..");
        return IntStream.range(0, 10)
                .mapToObj(i -> "Hello number  " + i).collect(Collectors.toList());


    }
}
