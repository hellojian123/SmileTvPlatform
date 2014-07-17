package com.smiletv.service;

import com.smiletv.bean.CheckIn;
import com.smiletv.bean.Device;
import com.smiletv.bean.User;

/**
 * Created by hejian on 2014/7/17.
 */
public interface UserService {
    /**
     * 通过设备关联的用户来更新用户信息
     * @param device
     * @return
     */
    public boolean updatePersonInfo(Device device);

    /**
     * 用户登录。
     * @return
     */
    public boolean userLogin(Device device,User user);

    /**
     * 用户签到
     * @param device
     * @param user
     * @param checkIn
     * @return
     */
    public boolean userCheckIn(Device device,User user,CheckIn checkIn);

}
