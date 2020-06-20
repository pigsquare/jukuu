package zfz.ds.jukuu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/")
    public String index(){
        return "welcome";
    }
    @RequestMapping("result")
    public String index2(){
        return "result";
    }
    @RequestMapping("success")
    public String index3(){
        return "success";
    }
}
