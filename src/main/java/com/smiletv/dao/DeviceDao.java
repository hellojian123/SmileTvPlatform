package com.smiletv.dao;

import com.smiletv.bean.Device;
import com.smiletv.bean.User;

/**
 * Created by hejian on 2014/7/2.
 */
public interface DeviceDao {

    /**
     * 将数一个DevInfo数据插入到数据库中
     * @param device
     */
    public void insertDevInfo2DB(Device device);

    /**
     * 清理释放session的一级缓存
     */
    public void releaseSessionCache();

    /**
     * 激活设备，成功返回true，否则返回false。
     * @param device
     * @return
     */
    public boolean activeDevice(Device device);

    /**
     * 查找一个box
     * @return
     */
    public Device findDevice(Device device);

    /**
     * 认证成功，将用户和盒子绑定
     * @param device
     * @return
     */
    public void bandUserAndBox(Device device,User user);

}
