package com.smiletv.dao.impl;

import com.smiletv.bean.Device;
import com.smiletv.dao.DeviceDao;
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


}
