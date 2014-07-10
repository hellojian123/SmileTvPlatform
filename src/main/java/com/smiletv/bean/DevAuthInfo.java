package com.smiletv.bean;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hejian on 2014/7/4.
 * 设备(终端)认证信息实体
 */
@Entity
@Table(name = "t_dev_auth")
public class DevAuthInfo implements Serializable {

    private Integer id;
    private boolean actiStatus;   //激活状态，true表示激活，false表示未激活
    private Date actiTime;        //激活时间
    private String actiArea;      //激活位置信息
    private Device device;        //所激活的设备

    @Id
    @GeneratedValue(generator = "f_k")
    @GenericGenerator(name = "f_k", strategy = "foreign",
            parameters = {@Parameter(name = "property", value = "device")})
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isActiStatus() {
        return actiStatus;
    }

    public void setActiStatus(boolean actiStatus) {
        this.actiStatus = actiStatus;
    }

    public Date getActiTime() {
        return actiTime;
    }

    public void setActiTime(Date actiTime) {
        this.actiTime = actiTime;
    }

    public String getActiArea() {
        return actiArea;
    }

    public void setActiArea(String actiArea) {
        this.actiArea = actiArea;
    }

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
