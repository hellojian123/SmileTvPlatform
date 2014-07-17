package com.smiletv.service.impl;

import com.smiletv.bean.CheckIn;
import com.smiletv.bean.Device;
import com.smiletv.bean.User;
import com.smiletv.dao.UserDao;
import com.smiletv.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hejian on 2014/7/17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
public class UserServiceImpl implements UserService {

    @Resource(name = "userDao")
    UserDao userDao;
    /**
     * 完善个人信息。
     * @param device
     * @return
     */
    @Override
    public boolean updatePersonInfo(Device device) {
        if(device==null){
            return false;
        }

        //通过盒子的deviceID和uuid查出该盒子
        Device dev = this.userDao.findDeviceByUUID(device);

        if(dev==null){   //如果没有这个设备，返回false
            return false;
        }

        //判断关联的用户是否为空
        if(dev.getUser()==null){ //如果没有用户信息  就新增用户信息 并关联设备 （此操作会完善用户信息）
            userDao.saveUserInfo(dev,device.getUser());
        }

        /*
            有用户信息，就更新用户信息，更新的作用有两个(1,如果用户信息不完善,更新后就完善了。
            2,如果用户信息是完善的但是需要修改其中的信息，更新将会同步最新用户信息到数据库)。
            如果查询的user和要更改的user一模一样，由于一级缓存的存在，不会产生update语句。
        */
        if(!userDao.updateUserInfo(dev.getUser(),device.getUser())){
            return false;
        }

        return true;
    }

    /**
     * 用户登录.
     * 通过设备找到关联的用户。
     * 然后再比较请求体的用户是否和此设备关联的用户匹配
     * @param user
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public boolean userLogin(Device device,User user) {
        if(device==null){
            return false;
        }
        if(user==null){
            return false;
        }

        //通过请求体的设备信息在数据库中查找，通过盒子的deviceID和uuid查出该盒子
        Device dev = this.userDao.findDeviceByUUID(device);
        if(dev==null){   //如果没有这个设备，说明用户盒子非法没必要登录，返回false登录失败
            return false;
        }

        //登录验证
        User u = dev.getUser();
        if(u==null){
            return false;
        }
        String phoneNum = u.getUserPhone();
        String password = u.getPassword();
        //当且仅当手机号和密码各相同才登录成功。
        if(!user.getUserPhone().equals(phoneNum)){
            return false;
        }
        if(!user.getPassword().equals(password)){
            return false;
        }
        return true;
    }

    /**
     * 用户签到。
     * 先查找有无这个盒子，判断盒子的合法性。
     * 再从盒子找到关联的用户，判断用户的合法性。
     * 再根据用户关联签到信息。
     * 一天只能签到一次。
     * @param device
     * @param user
     * @param checkIn
     * @return
     */
    @Override
    public boolean userCheckIn(Device device, User user, CheckIn checkIn) {

        //查找盒子判断盒子的合法性， 通过uuid和deviceId查找
        Device dev = this.userDao.findDeviceByUUID(device);
        if(dev==null){   //如果没有这个设备，说明用户盒子非法没必要登录，返回false登录失败
            return false;
        }

        User u = dev.getUser();
        if(u==null){
            return false;
        }
        String phoneNum = u.getUserPhone();
        String password = u.getPassword();
        //当且仅当手机号和密码各相同签到。
        if(!user.getUserPhone().equals(phoneNum)){
            return false;
        }
        if(!user.getPassword().equals(password)){
            return false;
        }

        //查出最近签到时间（根据用户id查）
        Date nearTime = userDao.getMaxCheckInTime(u);
        if(nearTime==null){//如果最近时间为空则表示该用户还没有签到，可以直接插入签到信息
            userDao.saveCheckInInfo(u,checkIn);
            return true;
        }

        Date currentSystemDate = new Date();
        long timeDifference = nearTime.getTime()-currentSystemDate.getTime(); //获得时间差
        if(timeDifference>0){//最近一次签到时间大于了系统时间返回false
            return false;
        }
        //判断当前签到时间与最近签到时间是否是同一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nTime = format.format(nearTime);                  //最近一次签到时间
        String cTime = format.format(checkIn.getCheckinTime());  //当前需要签到时间
        if(nTime.equals(cTime)){//时间是在同一天返回false。
            return false;
        }
        if(checkIn.getCheckinTime().getTime()-nearTime.getTime()<0){ //如果签到时间比最近一次签到时间小  返回false
            return false;
        }

        //插入签到信息。
        userDao.saveCheckInInfo(u,checkIn);

        return true;
    }


}
