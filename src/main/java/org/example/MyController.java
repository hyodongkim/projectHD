package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@Slf4j
public class MyController {

    @Autowired
    private ServiceImpl Service;

    @GetMapping("/")
    public String realmain(Model model) {
//        List<entity> list = Service.findAll();
//        model.addAttribute("list", list);
        return "views/main";
//        return "redirect:/admin/views/admin_notice";
    }
    @GetMapping("/{id}")
    public String main(@PathVariable("id") String id, Model model) {
//        List<entity> list = Service.findAll();
//        model.addAttribute("list", list);
        return "views/main";
//        return "redirect:/admin/views/admin_notice";
    }

    @GetMapping("/test")
    public String test_main(Model model) {
//        List<entity> list = Service.findAll();
//        model.addAttribute("list", list);
        return "views/test_main";
//        return "redirect:/admin/views/admin_notice";
    }

    @GetMapping("/leaf")
    public String ddd(Model model) {
        List<entity> list = Service.findAll();
        model.addAttribute("list", list);
        return "thymeleaf/ddd";
    }
//    testHHHdsaf
}