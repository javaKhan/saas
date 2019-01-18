package com.mmtap.cma.demo.saas.entiy;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_synctargeturi")
@Data
public class Synctargeturi {
    @Id
    @Column(name = "SyncID")
    private String SyncID;
    @Column(name = "SyncRule")
    private int SyncRule;
    @Column(name = "TargetUri")
    private String TargetUri;
    @Column(name = "SyncInstanceCode")
    private String SyncInstanceCode;
    @Column(name = "SyncInstanceName")
    private String SyncInstanceName;
    @Column(name = "MappingID")
    private String MappingID;
    @Column(name = "MappingName")
    private String MappingName;
}
