package com.smiletv.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hejian on 2014/7/2.
 * 设备信息类
 * 1，对于设备认证成功后会将设备id和用户id绑定，即是将一台设备和一位用户绑定。
 * 2，第一次设备激活的时候用户为空。
 * 3，在第一次认证的时候会将用户和用户所要认证的设备带过来。如果如果这台设备的
 *    设备信息中激活状态是true(已激活状态)。那么就认证成功。将用户和设备绑定存
 *    入数据库。在第二次及以后的认证过程中，直接将客户端带来的用户和设备去和数
 *    据库匹配，匹配成功即为认证成功。
 */
@Entity
@Table(name = "t_device")
public class Device  implements Serializable{

    private Integer id;
    private String  deviceID;         //设备ID
    private String  deviceType;       //设备类型（型号）
    private String  serialNumber;     //设备序列号
    private String  uniqueID;         //标识设备的唯一id
    private String  wiredMAC;         //有线MAC地址
    private String  wirelessMAC;      //无线MAC地址
    private String  machineCode;      //机器码
    private User user;                //设备认证后所属用户
    private DevAuthInfo devAuthInfo;  //设备的激活信息

    @Id
    @GeneratedValue(generator = "native_generator")
    @GenericGenerator(name = "native_generator",strategy = "native")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getWiredMAC() {
        return wiredMAC;
    }

    public void setWiredMAC(String wiredMAC) {
        this.wiredMAC = wiredMAC;
    }

    public String getWirelessMAC() {
        return wirelessMAC;
    }

    public void setWirelessMAC(String wirelessMAC) {
        this.wirelessMAC = wirelessMAC;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne
    @PrimaryKeyJoinColumn
    public DevAuthInfo getDevAuthInfo() {
        return devAuthInfo;
    }

    public void setDevAuthInfo(DevAuthInfo devAuthInfo) {
        this.devAuthInfo = devAuthInfo;
    }

    /**
     * 判断这些device属性是否有可用(不为null，值不为null，值不为"")值.
     * @return
     */
    public boolean empty(){
        String cmpStr = this.deviceID+this.machineCode+this.wiredMAC+this.wirelessMAC;
        if(cmpStr == null){
            return true;        //返回true表示user没有值
        }
        if(cmpStr.trim().equals("null")){
            return true;
        }
        if(cmpStr.trim().equals("")){
            return true;
        }
        return false;           //返回false表示user有值
    }
}
