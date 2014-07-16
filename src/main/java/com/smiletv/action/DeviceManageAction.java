package com.smiletv.action;

import com.smiletv.bean.*;
import com.smiletv.bean.RequestParam;
import com.smiletv.service.DeviceService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by hejian on 2014/7/14.
 * 设备管理action
 */

@Controller
@RequestMapping("/device/management")
public class DeviceManageAction {

    @Resource(name = "deviceService")
    DeviceService deviceService;

    /**
     * 终端认证，当终端第一次开机时需要激活，激活后以后再开机就是终端的认证。
     *
     * 激活：激活信息没有用户信息，只有盒子信息。将带过来的信息直接存入数据库，并标记为已激活。
     * 认证：判断盒子是否合法，如果合法将用户和盒子绑定。
     *
     * 合法性判断：
     *      1，盒子是否是激活状态。
     *      2，deiviceId是否匹配。
     *      3，有线mac是否匹配。
     *      4，无线mac是否匹配。
     *      5，mcid是否匹配。
     *      6，uuId是否匹配。
     */
    @RequestMapping(value = "/terminal/authentication",method = RequestMethod.POST)
    public @ResponseBody ResponseResult terminalAuthentication(@RequestBody RequestParam requestParam){

        ResponseResult result = new ResponseResult();
        result.setRequestType("TerminalAuthentication");
        String reqType = requestParam.getRequestType();

        //判断是否是认证请求
        if(reqType==null|reqType.trim().equals("")){
            result.setResult(-1);
            return result;
        }

        if(!reqType.trim().equalsIgnoreCase("TerminalAuthentication")){
            result.setResult(-1);
            return result;
        }

        User user = new User();
        Device device = new Device();
        DevAuthInfo authInfo = new DevAuthInfo();
        //将请求的参数解析出来封装到各个bean。
        try {
            BeanUtils.copyProperties(user,requestParam);
            BeanUtils.copyProperties(device,requestParam);
            device.setWiredMAC(requestParam.getParams()[0]);
            device.setWirelessMAC(requestParam.getParams()[1]);
            device.setMachineCode(requestParam.getParams()[2]);

            authInfo.setActiArea(requestParam.getParams()[3]);
        } catch (Exception e) {
            //e.printStackTrace();
            result.setResult(-1);
            return result;
        }

        //判断请求参数的盒子信息是否完整
        if(device.empty()){
            result.setResult(-1);
            return result;
        }

        //判断是否是第一次开机，第一次开机是没有用户信息的。
        if(user.empty()){   //如果为空就表示是开机第一次请求
            authInfo.setActiTime(new Date());
            authInfo.setActiStatus(true);

            device.setDevAuthInfo(authInfo);
            //激活盒子
            try {
                if(!deviceService.activeSmileBox(device)){
                    result.setResult(-1);
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.setResult(-1);
                return result;
            }

        }

        //盒子认证
        device.setUser(user);  //关联用户
        try {
            if(!deviceService.smileBoxAuthentication(device)){  //如果认证非法
                result.setResult(-1);
                return result;
            }
        } catch (Exception e) {
            result.setResult(-1);
            return result;
        }

        //激活或者认证成功
        result.setResult(0);
        return result;

    }


}
