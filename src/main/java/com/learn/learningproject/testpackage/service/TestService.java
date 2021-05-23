package com.learn.learningproject.testpackage.service;

import com.learn.learningproject.testpackage.entity.Test;

import java.util.List;

/*
created by Rahul sawaria on 17/05/21
*/
public interface TestService {

    void save(Test test);

    List<Test> get();

    void testEmailService(String recipient);
}
