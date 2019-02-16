package com.bootone.boot_one.Controller;

import com.bootone.boot_one.domain.City;


import com.bootone.boot_one.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class CityRestController {
    @Autowired
    private CityService cityService;
    @Autowired
    private CacheManager cacheManager;


@RequestMapping(value = "/api/city/{id}", method = RequestMethod.GET)
public City findOneCity(@PathVariable("id") Long id) {
    System.out.println("wangke:" + id);
    System.out.println(cacheManager.getCacheNames());
    System.out.println(cacheManager.getCache("people").getNativeCache().toString());
    City city = cityService.findOne(id);
    System.out.println(city.getCityName());

    return city;
}

    @RequestMapping(value = "/city/{id}", method = RequestMethod.GET)
    public City findOneCity2(@PathVariable("id") Long id) {
        System.out.println("wangke2:" + id);
        City city = cityService.findOne2(id);
        return city;
    }

/*public ModelAndView findOneCity(@PathVariable("id") Long id) {
    System.out.prin tln("wangke:"+id);
    City city = cityService.findCityById(id);
    System.out.println(city.getCityName());
    // return "redirect";
    System.out.println("++++++++++++++");
    ModelAndView view= new ModelAndView();
    System.out.println("+++++++++++"+city.getCityName());
    view.addObject("studentList", city.getCityName());
    //设置要跳转的页面，与返回值时String时返回success类似，以下跳转到/student/studentList.jsp
    view.setViewName("redirect");

    return view;
}*/
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createUserForm(Map<String, Object> model) {
        model.put("time", "2018-5-26");
        model.put("message", "这是测试的内容。。。");
        model.put("toUserName", "张三");
        model.put("fromUserName", "老许");

        return "userForm";
    }
}
