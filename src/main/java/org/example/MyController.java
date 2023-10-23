package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class MyController {

    @Autowired
    private ServiceImpl Service;
    @GetMapping("/")
    public String main(Model model) {
//        List<entity> list = Service.findAll();
//        model.addAttribute("list", list);
        return "views/main";
    }
    @GetMapping("/leaf")
    public String ddd(Model model) {
        List<entity> list = Service.findAll();
        model.addAttribute("list", list);
        return "thymeleaf/ddd";
    }
//    testHHHdsaf
}