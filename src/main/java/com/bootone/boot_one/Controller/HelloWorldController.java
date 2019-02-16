package com.bootone.boot_one.Controller;


import com.bootone.boot_one.domain.City;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HelloWorldController {

    City city = new City();
    @RequestMapping("/redirect")

    public String hello(City city, Model model){
        model.addAttribute("city", city);
        model.addAttribute("message", ",welcome");
        return "redirect";
    }
    @RequestMapping("/")
    public String page(){
        System.out.println("fsdd");
        return "system/index";
    }

    @RequestMapping(value = "/home")
    public String home(Model model) {
        model.addAttribute("city",city);
        return "redirect";
    }

    @RequestMapping(value = "/aa",method = RequestMethod.POST)
    public String bb(City city,Model model) {
        model.addAttribute("city", city);
        model.addAttribute("message", ",welcome");
        System.out.println("wangkedas");
        return "aa";
    }
}
