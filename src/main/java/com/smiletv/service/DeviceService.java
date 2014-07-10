package com.smiletv.service;

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


}
