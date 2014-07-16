package com.smiletv.service;

import com.smiletv.bean.Device;

/**
 * Created by hejian on 2014/7/2.
 */
public interface DeviceService {
    /**
     * 将上传的包含设备信息的xml文件存储到数据库去
     * @param xmlFilePath
     * @return  如果抛出异常则会回滚所有添加的数据
     */
    public void saveDeviceInfo(String xmlFilePath);

    /**
     * 激活快乐小盒
     * @param dev
     * 激活成功返回true否则返回false
     */
    public boolean activeSmileBox(Device dev);

    /**
     * 盒子认证，认证成功返回true，否则返回false
     * @param dev
     * @return
     */
    public boolean smileBoxAuthentication(Device dev);


}
