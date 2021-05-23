package com.learn.learningproject.testpackage.service;


import com.learn.learningproject.commonservices.EmailService;
import com.learn.learningproject.testpackage.dao.TestDao;
import com.learn.learningproject.testpackage.entity.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
created by Rahul sawaria on 17/05/21
*/
@Service
public class TestServiceImpl implements TestService {

    private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    private TestDao testDao;
    @Autowired
    private EmailService emailService;

    @Override
    public void save(Test test) {
        log.info("inside save method in test service class..");
        testDao.save(test);
    }

    @Override
    public List<Test> get() {
        log.info("inside get method in test service class..");
        return testDao.getData();

    }

    @Override
    public void testEmailService(String recipient) {
        log.info("inside test email method in test service class..");
        String recipientsArray[]=recipient.split(",");
        emailService.sendEmail(recipientsArray, "Test Email Received Successfully!", "You are receiving this mail that means your email service is working fine :)", "text/html",
                null, null);

    }
}
