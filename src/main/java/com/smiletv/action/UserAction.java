package com.smiletv.action;

import com.smiletv.bean.*;
import com.smiletv.service.UserService;
import com.smiletv.utils.WebUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;

/**
 * Created by hejian on 2014/7/17.
 */
@Controller
@RequestMapping("/user")
public class UserAction {

    @Resource(name = "userService")
    UserService userService;

    /**
     * 完善个人(用户)信息（设置密码昵称 用户可选）
     * @param requestParam
     * @return
     */
    @RequestMapping(value = "/info/completion",method = RequestMethod.POST)
    public @ResponseBody
    ResponseResult CompleteUserInfo(@RequestBody RequestParam requestParam){
        ResponseResult result = new ResponseResult();
        result.setRequestType("AddPersonalInfo");

        //判断是否是完善(用户)信息请求
        if(!WebUtil.reqBodyValidate("AddPersonalInfo", requestParam)){
            result.setResult(-1);
            return result;
        }

        //解析请求体封装到相应实体中
        User user = new User();
        Device device = new Device();
        try {
            user.setUserName(requestParam.getParams()[0]);  //有可能数组越界异常。
            user.setNickName(requestParam.getParams()[1]);
            user.setUserPhone(requestParam.getParams()[2]);
            user.setPassword(WebUtil.MD5(requestParam.getParams()[3]));
            user.setRandomKey(requestParam.getRandomKey());
            device.setDeviceID(requestParam.getDeviceID());
            device.setUniqueID(requestParam.getUniqueID());
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(-1);
            return result;
        }

        //判断盒子和用户是否为空
        if(device.empty()|user.empty()){
            result.setResult(-1);
            return result;
        }

        //完善用户信息(必须通过盒子来找到该用户再更新完善用户信息)
        device.setUser(user);
        try {
            if(!userService.updatePersonInfo(device)){
                result.setResult(-1);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(-1);
            return result;
        }

        result.setResult(0);
        return result;
    }

    /**
     * 用户登录
     * @param requestParam
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public @ResponseBody
    ResponseResult userLogin(@RequestBody RequestParam requestParam){
        ResponseResult result = new ResponseResult();
        result.setRequestType("Login");

        //判断是否是用户登录请求
        if(!WebUtil.reqBodyValidate("Login", requestParam)){
            result.setResult(-1);
            return result;
        }

        //解析请求体封装到相应实体中
        User user = new User();
        Device device = new Device();
        user.setUserName(requestParam.getUserName());
        user.setUserPhone(requestParam.getUserPhone());
        user.setPassword(WebUtil.MD5(requestParam.getPassword()));
        user.setRandomKey(requestParam.getRandomKey());
        device.setDeviceID(requestParam.getDeviceID());
        device.setUniqueID(requestParam.getUniqueID());

        //判断盒子和用户是否为空
        if(device.empty()|user.empty()){
            result.setResult(-1);
            return result;
        }

        //用户登录
        try {
            if(!userService.userLogin(device,user)){
                result.setResult(-1);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(-1);
            return result;
        }

        result.setResult(0);
        return result;
    }


    /**
     * 用户签到
     * @return
     */
    @RequestMapping(value = "/checkin",method = RequestMethod.POST)
    public @ResponseBody
    ResponseResult userCheckIn(@RequestBody RequestParam requestParam){

        ResponseResult result = new ResponseResult();
        result.setRequestType("Checkin");

        //判断是否是用户签到请求
        if(!WebUtil.reqBodyValidate("Checkin", requestParam)){
            result.setResult(-1);
            return result;
        }

        //解析请求体封装到相应实体中
        User user = new User();
        Device device = new Device();
        CheckIn checkIn = new CheckIn();
        try {
            BeanUtils.copyProperties(user,requestParam);
            user.setPassword(WebUtil.MD5(requestParam.getPassword()));
            BeanUtils.copyProperties(device,requestParam);

            String checkinTime = requestParam.getParams()[0];
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            checkIn.setCheckinTime(format.parse(checkinTime));  //如果时间能够成功转换，不抛异常，checIn一定会有时间,且不为空。
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(-1);
            return result;
        }

        //判断盒子和用户是否为空
        if(device.empty()|user.empty()){
            result.setResult(-1);
            return result;
        }

        //用户签到
        try {
            if(!userService.userCheckIn(device,user,checkIn)){
                result.setResult(-1);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(-1);
            return result;
        }

        result.setResult(0);
        return result;
    }


}
