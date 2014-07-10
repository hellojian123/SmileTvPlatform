package com.smiletv.service.impl;

import com.smiletv.bean.Device;
import com.smiletv.dao.DeviceDao;
import com.smiletv.dao.impl.DeviceDaoImpl;
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
import java.lang.reflect.InvocationTargetException;
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





}
