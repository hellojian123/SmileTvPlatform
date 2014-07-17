package com.smiletv.dao.impl;

import com.smiletv.bean.CheckIn;
import com.smiletv.bean.Device;
import com.smiletv.bean.User;
import com.smiletv.dao.UserDao;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by hejian on 2014/7/17.
 */
public class UserDaoImpl implements UserDao {

    @Resource(name = "sessonFactory")
    private SessionFactory sf;

    /**
     * 通过设备的uuid和设备id查找一个盒子返回
     * @param device
     * @return
     */
    @Override
    public Device findDeviceByUUID(Device device) {
        Session session = sf.getCurrentSession();
        if(device==null){
            return null;
        }
        String hql = "from Device d where d.deviceID=? and d.uniqueID=?";
        Query query = session.createQuery(hql);
        query.setString(0,device.getDeviceID());
        query.setString(1,device.getUniqueID());
        query.setMaxResults(1);
        return (Device) query.uniqueResult();
    }

    /**
     * 保存用户信息
     * @param device
     * @param user
     */
    @Override
    public void saveUserInfo(Device device, User user) {
        Session session = sf.getCurrentSession();
        //设置关联
        device.setUser(user);
        user.getDevices().add(device);
        session.save(user);
    }

    /**
     * 更新用户信息
     * destUser:目标User，需要更改到数据库的user
     * oriUser:源user,给目标user赋值的user。
     * 这里更改user到数据库用到了session一级缓存和快照的原理。
     */
    @Override
    public boolean updateUserInfo(User destUser , User oriUser) {
        Session session = sf.getCurrentSession();
        oriUser.setId(destUser.getId());
        try {
            BeanUtils.copyProperties(destUser,oriUser);
            session.flush();  //将一级缓存同步到数据库
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Date getMaxCheckInTime(User user) {
        Session session = sf.getCurrentSession();
        /*查出最大时间的sql语句：
            select * from t_checkin where
                checkinTime in
                    (select max(checkinTime) from t_checkin group by user_id)
                and user_id=5
        */
        String hql = "from CheckIn c where c.checkinTime in (select max (_c.checkinTime) from CheckIn _c group by _c.user ) and c.user = ?";
        Query query = session.createQuery(hql);
        query.setInteger(0,user.getId());
        CheckIn checkIn = (CheckIn)query.uniqueResult();
        if(checkIn==null){
            return null;
        }
        return checkIn.getCheckinTime();
    }

    @Override
    public void saveCheckInInfo(User user,CheckIn checkIn) {
        Session session = sf.getCurrentSession();

        //设置关联
        user.getCheckIns().add(checkIn);
        checkIn.setUser(user);

        session.save(checkIn);
    }
}
