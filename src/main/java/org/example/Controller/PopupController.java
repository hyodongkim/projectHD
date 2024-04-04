package org.example.Controller;

import org.example.Entity.Article;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/Popups")
public class PopupController {

    @GetMapping("/logout")
    public String logout() {

        return "thymeleaf/popup/logout";
    }
}
