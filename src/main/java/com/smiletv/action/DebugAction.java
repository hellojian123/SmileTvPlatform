package com.smiletv.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hejian on 2014/7/14.
 *
 */
@Controller
@RequestMapping("/debug")
public class DebugAction {

    /**
     * 转发到debug页面
     * @return
     */
    @RequestMapping("/debug.do")
    public String toDebugPage(){
        return "debug/debug";
    }

}
