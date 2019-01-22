package com.mmtap.cma.demo.saas.dao;

import com.mmtap.cma.demo.saas.entiy.Synctargeturi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SynctargeturiResopory extends JpaRepository<Synctargeturi,String>{

    @Query(value = "select * from t_synctargeturi t where t.SyncInstanceCode= :key ",nativeQuery = true)
    Synctargeturi findByDomainKey(String key);

}
