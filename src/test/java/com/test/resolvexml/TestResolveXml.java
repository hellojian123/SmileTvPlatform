package com.test.resolvexml;

import com.smiletv.service.impl.DeviceServiceImpl;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by hejian on 2014/7/2.
 */
public class TestResolveXml {

    @Test
    public void testResoveXmlFile(){

        try {
            SAXReader reader = new SAXReader();
            final int[] count = new int[1];
            reader.setDefaultHandler(new ElementHandler(){
                public void onEnd(ElementPath ep) {
                    Element e = ep.getCurrent(); //获得当前节点

                    if(e.getName().equals("device")){
                        System.out.println(e.attribute("deviceID").getText());
                        System.out.println(e.attribute("macAddress").getText());
                        System.out.println(e.attribute("deviceType").getText());
                        System.out.println(e.attribute("serialNumber").getText());
                        System.out.println("------------------------------------"+(count[0]++));
                    }
                    e.detach(); //从内存中移去
                }
                public void onStart(ElementPath ep) {
                    Element e = ep.getCurrent();
                    e.detach(); //从内存中移去
                }
            });

            reader.read(new BufferedInputStream(new FileInputStream(
                    new File("F:\\IdeaProjects\\SmileTvPlatform\\src\\test\\resources\\devices.xml"))));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        DeviceServiceImpl d = new DeviceServiceImpl();
        try {
            d.saveDeviceInfo("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
