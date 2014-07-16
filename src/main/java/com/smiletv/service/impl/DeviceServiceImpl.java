package com.smiletv.service.impl;

import com.smiletv.bean.DevAuthInfo;
import com.smiletv.bean.Device;
import com.smiletv.bean.User;
import com.smiletv.dao.DeviceDao;
import com.smiletv.service.DeviceService;
import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by hejian on 2014/7/2.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED ,isolation = Isolation.DEFAULT)
public class DeviceServiceImpl implements DeviceService {

    @Resource(name = "deviceDao")
    private DeviceDao deviceDao ;

    /**
     * 将设备信息xml文件保存到数据库去，由于数据量之大这个业务方法需要加上spring的事物，
     * 以避免存储失败能够回滚。
     * @param xmlFilePath  xml文件的绝对路径
     * @return  没抛出异常则成功，否则抛出异常并会会滚掉所有数据
     */
    @Override
    public void saveDeviceInfo(String xmlFilePath){
        try{
            SAXReader reader = new SAXReader();
            reader.setDefaultHandler(new ElementHandler(){  //实现elmentHandler接口以解析大xml文件防止内存溢出。
                public void onEnd(ElementPath ep) {
                    Device dev = new Device();
                    Element e = ep.getCurrent(); //获得当前节点
                    if(e.getName().equals("device")){
                        List<Attribute> attres = e.attributes();
                        for(Attribute attr:attres){  //将节点元素的属性装配的bean中
                            String attrName = attr.getName();
                            String attrValue = attr.getText();
                            try {
                                BeanUtils.setProperty(dev,attrName,attrValue);
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        deviceDao.insertDevInfo2DB(dev);   //将device存储到数据库去
                    }
                    e.detach(); //将elment从内存中移去
                }
                public void onStart(ElementPath ep) {
                    Element e = ep.getCurrent();
                    e.detach(); //将element从内存中移去
                }
            });
            reader.read(new BufferedInputStream(new FileInputStream(new File(xmlFilePath))));
            deviceDao.releaseSessionCache();  //在spring提交事务之前清理session，因为commit方法并不会清理session的一级缓存
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    /**
     * 第一次开机请求过来激活快乐小盒
     * @param dev
     */
    @Override
    public boolean activeSmileBox(Device dev) {
        if(dev==null){
            return false;
        }

        return deviceDao.activeDevice(dev);
    }

    /**
     * 认证盒子的合法性，合法返回true，非法返回false
     * @param dev
     * @return
     */
    @Override
    public boolean smileBoxAuthentication(Device dev) {
        if(dev==null){
            return false;
        }

        //从数据库查找该盒子
        Device device =  deviceDao.findDevice(dev);
        if(device==null){ //如果从数据库找不到这个盒子，说明非法。通过deiviceId，mcid，有线mac，无线mac去数据库匹配寻找。
            return false;
        }

        DevAuthInfo authInfo = device.getDevAuthInfo();
        if(!authInfo.isActiStatus()){ //如果激活信息为没有激活，则该盒子非法。
            return false;
        }

        if(!dev.getUniqueID().equals(device.getUniqueID())){ //如果uuid不匹配，则该盒子非法
            return false;
        }

        //绑定用户和盒子
        User user = dev.getUser();
        deviceDao.bandUserAndBox(device,user);

        return true;
    }


}
