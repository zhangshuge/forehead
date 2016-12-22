package com.zc;

import com.zc.common.cache.LocalCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangchi9 on 2016/12/20.
 */
@Controller
public class DemoController {
    @Autowired
    @Qualifier(value = "serialRuleServiceImpl")
    private LocalCache<String,String> cache;
    @RequestMapping("/demotest.action")
    public ModelAndView demotest(){
        String value = null;
        value = cache.get("test");
        String c = cache.get("key1");
        String d = cache.get("key2");
        ModelAndView view = new ModelAndView("/demo");
        view.addObject("value",value);
        return view;
    }
}
