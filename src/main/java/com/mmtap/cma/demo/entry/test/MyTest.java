package com.mmtap.cma.demo.entry.test;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_test")
public class MyTest {
    @Id
    @Column(name = "mid")
    private int id;
    @Column(name = "text")
    private String text;
}
