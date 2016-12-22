package com.zc;

import com.zc.common.cache.LocalCache;
import com.zc.service.cache.IdcCacheService;
import com.zc.service.cache.SerialRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangchi9 on 2016/12/20.
 */
@Controller
public class CacheController {
    @Autowired
    @Qualifier(value = "SerialRuleServiceImpl")
    private SerialRuleService<String, Object> localCache;

    @Autowired
    private IdcCacheService idcCacheService;

    @RequestMapping(value = "/queryCaches.action")
    public ModelAndView queryCaches(Model model) {
        String value = null;
        value = localCache.get("test");
        String value1 = localCache.get("key1");
        String value2 = localCache.get("key2");
        ModelAndView view = new ModelAndView("/cache");
        view.addObject("value", value);
        view.addObject("value1", value1);
        view.addObject("value2", value2);


        idcCacheService.get("000010");

        localCache.getCacheStats("SerialRuleServiceImpl");
        return view;
    }
}
