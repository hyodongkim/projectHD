package org.example.Controller;

import org.example.Entity.Article;
import org.example.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class NoticeController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/notice")
    public String noticeList(@PageableDefault(page = 0, size = 10, sort = "articleId", direction = Sort.Direction.ASC) Pageable pageable,
                             @CookieValue(value = "memberId", required = false) Long id,
                             @RequestParam(required = false, defaultValue = "") String search,
                             Model model) {

        Integer count = articleService.countMembersId(id);
        List<Article> article = articleService.findByAdminArticle();

        model.addAttribute("article",article);
        model.addAttribute("count",count);

        return "thymeleaf/notice/noticeList";
    }
}
