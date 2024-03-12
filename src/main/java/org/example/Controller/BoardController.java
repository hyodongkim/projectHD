package org.example.Controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Dto.StoreDto;
import org.example.Entity.Article;
import org.example.Entity.Board;
import org.example.Entity.Member;
import org.example.Entity.Store;
import org.example.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/Boards")
@Slf4j
public class BoardController {

    @Value("${spring.servlet.multipart.location}")
    private String path_article;
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public String registerArticle(@PageableDefault(page = 0, size = 10, sort = "articleId", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(required = false, defaultValue = "") String search, Model model) {

        Page<Article> page = articleService.findArticles(search, pageable);

        long totalElements = page.getTotalElements();
        List<Article> list = page.getContent();
        int requestPage = page.getPageable().getPageNumber() + 1;
        int totalPage = page.getTotalPages();
        int startPage = Math.max(1, requestPage - 4);
        int endPage = Math.min(page.getTotalPages(), requestPage + 4);
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

        return "thymeleaf/board/articleForm";
    }

    @GetMapping("/{articleId}")
    public String viewArticle(@PathVariable Long articleId, @ModelAttribute StoreDto Dto, @ModelAttribute Store store, Model model) {


        Optional<Article> article1 = articleService.findArticle(articleId);
        model.addAttribute("member", article1.get());

        String path1 = path_article + articleId;
        File dir = new File(path1);
        System.out.println("view:"+path1);
        String[] files = dir.list(); // 디렉토리에 저장된 파일들 이름을 배열에 담아줌.
        model.addAttribute("imgs", files);
//            model.addAttribute("imgs", files);

        return "thymeleaf/board/view";
    }

//    @PostMapping("/{articleId}")
//    public String viewArticlePost(@PathVariable Long articleId,@ModelAttribute StoreDto dto,@ModelAttribute Article article,Model model,
//                           Store store) {
//
//
//        Optional<Article> article1 = articleService.findArticle(articleId);
//        model.addAttribute("article", article1.get());
//
//        UUID uuid = UUID.randomUUID();
//        MultipartFile f = dto.getFile();
//        String fname1 = f.getOriginalFilename(); // 원본 파일명
//        String fname = "/"+ uuid +"_"+ fname1;
//        File f2 = new File(path_article+article.getArticleId()); // 업로드된 파일을 저장할 새 파일 생성
//        f2.mkdirs();
//        File f3 = new File(f2+fname);
//
//
//        try {
//            System.out.println("ViewPost:"+f3.getAbsolutePath());
//            f.transferTo(f3);
//
//
//
//        } catch (IllegalStateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        store.getOriginFilename(fname1);
//        store.getStoreFilename(f3.getAbsolutePath());
//        store.setMember(member);
//
//
//
//        articleService.registerArticle(article);
//        storeService.save(store);
//
//
//        if(store.getOriginFilename().isEmpty()){
//            storeService.deleteEmptyName();
//            if(f3.delete()){
//                System.out.println("인식함");
//            }
//            else{
//                System.out.println("인식못함");
//            }
//        }
//        else{
//            System.out.println("이상무");
//        }
//
//        return "thymeleaf/board/view";
//    }

    @GetMapping("/delete/{articleId}")
    public String delete(@PathVariable Long articleId, Model model) {
        articleService.deleteArticle(articleId);
        System.out.println("삭제");
        return "redirect:/Boards";
    }


}
