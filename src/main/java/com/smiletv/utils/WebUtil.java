package com.smiletv.utils;

import com.smiletv.bean.RequestParam;
import org.apache.commons.codec.binary.Base64;
import java.security.MessageDigest;

/**
 * Created by hejian on 2014/7/17.
 */
public class WebUtil {

    /**
     * 请求体的合法性检测
     */
    public static boolean reqBodyValidate(String requestType,RequestParam requestParam){
        String reqType = requestParam.getRequestType();
        if(reqType==null|reqType.trim().equals("")){
           return false;
        }
        if(!reqType.trim().equalsIgnoreCase(requestType)){
            return false;
        }
        return true;
    }

    /**
     * 将明文密码经过md5 和 base64加密
     * @param message 原始的明文密码
     * @return 返回加密后的密码
     */
    public static String MD5(String message){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5 = md.digest(message.getBytes());

            return Base64.encodeBase64String(md5);
        } catch (Exception e) {
            e.printStackTrace();
            return message;
        }
    }

}
