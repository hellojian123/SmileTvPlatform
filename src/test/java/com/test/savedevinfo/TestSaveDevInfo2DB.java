package com.test.savedevinfo;

import com.smiletv.service.DeviceService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hejian on 2014/7/3.
 */
public class TestSaveDevInfo2DB {

    /**
     * 测试将设备信息保存到数据库
     */
    @Test
    public void testinsertDev(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("springMVC.xml");

        DeviceService d = (DeviceService) ac.getBean("deviceService");
        try {
            d.saveDeviceInfo("F:\\IdeaProjects\\SmileTvPlatform\\src\\test\\resources\\devices.xml");
        } catch (Exception e) {
            System.out.println("错误处理(数据已经回滚)");
            e.printStackTrace();
        }

    }




}
