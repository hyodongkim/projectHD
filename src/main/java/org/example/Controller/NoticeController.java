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
                                  @RequestParam(required = false, defaultValue = "") String search,
                                  Model model) {

        List<Article> article = articleService.findByAdminArticle();
        model.addAttribute("article",article);


        Page<Article> page = articleService.findArticles(search, pageable);

        long totalElements = page.getTotalElements();
        List<Article> list = page.getContent();
        int requestPage = page.getPageable().getPageNumber() + 1;
        int totalPage = page.getTotalPages();
        int startPage = Math.max(1, requestPage - 4);
        int endPage   = Math.min(page.getTotalPages(), requestPage + 4);
        boolean hasPrevious = page.hasPrevious();
        boolean hasNext = page.hasNext();

        model.addAttribute("totalElements", totalElements);
        model.addAttribute("list", list);
        model.addAttribute("requestPage", requestPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("hasPrevious", hasPrevious);
        model.addAttribute("hasNext", hasNext);

        return "thymeleaf/notice/noticeList";
    }
}
