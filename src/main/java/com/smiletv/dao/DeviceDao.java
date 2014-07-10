package com.smiletv.dao;

import com.smiletv.bean.Device;

/**
 * Created by hejian on 2014/7/2.
 */
public interface DeviceDao {

    public void insertDevInfo2DB(Device device);  //将数一个DevInfo数据插入到数据库中

    public void releaseSessionCache();            //清理释放session的一级缓存

}
