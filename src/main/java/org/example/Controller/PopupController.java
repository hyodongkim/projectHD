package org.example.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.Entity.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class PopupController {

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) throws IOException {

        return "thymeleaf/popup/logout";
    }
}
