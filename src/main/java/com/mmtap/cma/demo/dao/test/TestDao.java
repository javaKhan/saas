package com.mmtap.cma.demo.dao.test;

import com.mmtap.cma.demo.entry.test.MyTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDao extends JpaRepository<MyTest,Integer>{

    public List findByIdAfter(int id);
}
