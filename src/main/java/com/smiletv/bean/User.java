package com.smiletv.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hejian on 2014/7/4.
 *
 * 用户实体
 */
@Entity
@Table(name = "t_user")
public class User implements Serializable{

    private Integer id;
    private String userName;          //用户名
    private String nickName;          //昵称
    private String password;          //密码
    private String randomKey;         //随机key,用于加密。暂时保留
    private String userPhone;         //用户电话号码
    private Set<Device> devices = new HashSet<Device>(0);     //用户拥有的终端设备
    private Set<CheckIn> checkIns = new HashSet<CheckIn>(0);   //用户签到信息

    @Id
    @GeneratedValue(generator = "native_generator")
    @GenericGenerator(name = "native_generator",strategy = "native")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    public Set<CheckIn> getCheckIns() {
        return checkIns;
    }

    public void setCheckIns(Set<CheckIn> checkIns) {
        this.checkIns = checkIns;
    }

    /**
     * 判断这些user属性是否有可用(不为null，值不为null，值不为"")值.
     * @return
     */
    public boolean empty(){
        //注意，用户是否为空只关心用户信息不关心randkey和password，所以不加randkey和password。
        String cmpStr = this.userName+this.nickName+this.userPhone;
        if(cmpStr == null){
            return true;        //返回true表示user没有值
        }
        if(cmpStr.trim().matches("^(null)++$")){
            return true;
        }
        if(cmpStr.trim().equals("")){
            return true;
        }
        return false;           //返回false表示user有值
    }

}
