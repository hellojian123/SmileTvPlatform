package com.smiletv.dao;

import com.smiletv.bean.CheckIn;
import com.smiletv.bean.Device;
import com.smiletv.bean.User;

import java.util.Date;

/**
 * Created by hejian on 2014/7/17.
 */
public interface UserDao {

    /**
     * 通过uuid和设备id查找一个盒子
     * @param device
     * @return
     */
    public Device findDeviceByUUID(Device device);

    /**
     * 保存用户信息
     * @param device
     * @param user
     */
    public void saveUserInfo(Device device,User user);

    /**
     * 更新用户信息
     * destUser:目标User，需要更改到数据库的user
     * oriUser:源user,给目标user赋值的user。
     */
    public boolean updateUserInfo(User destUser , User oriUser);

    /**
     *获得最近的一次签到时间
     */
    public Date getMaxCheckInTime(User user);

    /**
     * 保存签到信息
     */
    public void saveCheckInInfo(User user,CheckIn checkIn);


}
