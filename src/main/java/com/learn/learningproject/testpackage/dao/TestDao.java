package com.learn.learningproject.testpackage.dao;

import com.learn.learningproject.testpackage.entity.Test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TestDao {

    Logger log = LoggerFactory.getLogger(TestDao.class);

    @Autowired
    private SessionFactory factory;

    private Session getSession() {
        return factory.getCurrentSession();
    }

    @Transactional
    public void save(Test test) {
        log.info("inside dao class save method");
        getSession().save(test);
    }

    @Transactional
    public List<Test> getData() {
        log.info("inside dao class get data method");
        String hql = "FROM Test";
        Query query = getSession().createQuery(hql);
        List<Test> data = query.getResultList();
        if(!CollectionUtils.isEmpty(data))
            return data;

        return new ArrayList<>();
    }


}
