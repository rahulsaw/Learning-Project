package com.learn.learningproject.testpackage.entity;

import lombok.Data;

import javax.persistence.*;

/*
created by Rahul sawaria on 17/05/21
*/
@Data
@Entity
@Table(name = "test_lp")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private String pincode;

}
