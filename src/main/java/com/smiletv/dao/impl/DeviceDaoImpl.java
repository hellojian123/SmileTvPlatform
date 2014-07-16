package com.smiletv.dao.impl;

import com.smiletv.bean.DevAuthInfo;
import com.smiletv.bean.Device;
import com.smiletv.bean.User;
import com.smiletv.dao.DeviceDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.annotation.Resource;

/**
 * Created by hejian on 2014/7/2.
 */
public class DeviceDaoImpl implements DeviceDao {

    @Resource(name = "sessonFactory")
    private SessionFactory sf;

    /**
     * 将device保存到数据库
     * @param device
     * @return
     */
    @Override
    public void insertDevInfo2DB(Device device) {
        Session session = sf.getCurrentSession();
        session.save(device);
        int count = device.getId()+1;  //每10000条数据清理释放一次一级缓存防止内存溢出
        if(count%10000==0){
            releaseSessionCache();
        }
    }

    /**
     * 在进行大批量的操作数据时候清理session一级缓存，提高效率。
     * @param
     */
    @Override
    public void releaseSessionCache(){
        Session session = sf.getCurrentSession();
        if(session!=null){
            session.flush();
            session.clear();
        }
    }

    /**
     * 设备第一次开机激活设备
     * @param device
     * @return
     */
    @Override
    public boolean activeDevice(Device device) {
        Session session = sf.getCurrentSession();
        if(device==null){
            return false;
        }
        DevAuthInfo authInfo = device.getDevAuthInfo();
        if(authInfo==null){
            return false;
        }
        String hql = "from Device d where d.deviceID=? and d.machineCode=? and d.wiredMAC=? and d.wirelessMAC=?";
        Query query = session.createQuery(hql);
        query.setString(0,device.getDeviceID());
        query.setString(1,device.getMachineCode());
        query.setString(2,device.getWiredMAC());
        query.setString(3,device.getWirelessMAC());
        query.setMaxResults(1);
        Device dev = (Device) query.uniqueResult();
        if(dev==null){
            return false;
        }
        dev.setUniqueID(device.getUniqueID());
        //建立关联
        dev.setDevAuthInfo(authInfo);
        authInfo.setDevice(dev);
        //保存激活信息，激活设备
        session.save(authInfo);
        return true;
    }

    /**
     * 查找一个设备。
     * @param device
     * @return
     */
    @Override
    public Device findDevice(Device device) {
        Session session = sf.getCurrentSession();
        if(device==null){
            return null;
        }
        String hql = "from Device d where d.deviceID=? and d.machineCode=? and d.wiredMAC=? and d.wirelessMAC=?";
        Query query = session.createQuery(hql);
        query.setString(0,device.getDeviceID());
        query.setString(1,device.getMachineCode());
        query.setString(2,device.getWiredMAC());
        query.setString(3,device.getWirelessMAC());
        query.setMaxResults(1);
        return (Device) query.uniqueResult();
    }

    @Override
    public void bandUserAndBox(Device device,User user) {
        Session session = sf.getCurrentSession();

        //设置关联
        device.setUser(user);
        user.getDevices().add(device);

        session.save(user);
    }


}
