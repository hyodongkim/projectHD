package org.example.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.ServiceImpl.MyServiceImpl;
import org.example.Entity.MyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
public class MyController {

    @Autowired
    private MyServiceImpl Service;

    private String VIEW_PATH = "/errors/";

    @GetMapping("/")
    public String realmain(Model model) {
//        List<entity> list = Service.findAll();
//        model.addAttribute("list", list);
        return "thymeleaf/index";
//        return "redirect:/admin/views/admin_notice";
    }

//    @GetMapping("/{id}")
//    public String main(@PathVariable("id") String id, Model model) {
////        List<entity> list = Service.findAll();
////        model.addAttribute("list", list);
//        return "views/main";
////        return "redirect:/admin/views/admin_notice";
//    }

    @GetMapping("/test")
    public String test_main(Model model) {
//        List<entity> list = Service.findAll();
//        model.addAttribute("list", list);
        return "views/test_main";
//        return "redirect:/admin/views/admin_notice";
    }
}