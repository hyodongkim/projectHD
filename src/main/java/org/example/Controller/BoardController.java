package org.example.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.Dto.*;
import org.example.Entity.*;
import org.example.Service.ArticleService;
import org.example.Service.ArticleStoreService;
import org.example.Service.CommentService;
import org.example.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    @Autowired
    private ArticleStoreService articleStoreService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public String registerArticle(@PageableDefault(page = 0, size = 10, sort = "articleId", direction = Sort.Direction.ASC) Pageable pageable,
                                  @RequestParam(required = false, defaultValue = "") String search,
                                  Model model) {

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

        return "thymeleaf/board/articleForm";
    }

    @GetMapping("/{articleId}")
    public String viewArticle(@PathVariable Long articleId, @ModelAttribute Article article, @ModelAttribute Store store,
                              @ModelAttribute ArticleStore articleStore,@ModelAttribute Member member,
                              @ModelAttribute Comment comment,
                              @PageableDefault(page = 0, size = 10, sort = "commentId", direction = Sort.Direction.ASC) Pageable pageable,
                              Model model) {

        articleService.plusClickCount(articleId);

        Optional<Article> article1 = articleService.findArticle(articleId);
        model.addAttribute("article", article1.get());


//        String path1 = path_article + articleId;
        String path1 = path_article + "ARTICLE"+"/";
        File dir = new File(path1);
        String path2 ="/"+article.getArticleId();
        File dir1 = new File(dir+path2);

        System.out.println("view:"+dir1);
        String[] files = dir1.list(); // 디렉토리에 저장된 파일들 이름을 배열에 담아줌.
        model.addAttribute("imgs", files);
//            model.addAttribute("imgs", files);







        Page<Comment> page = commentService.findByArticleId(articleId,pageable);

        long totalElements = page.getTotalElements();
        List<Comment> list = page.getContent();
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


        return "thymeleaf/board/articleView";
    }
    @GetMapping("/writeComment/{articleId}")
    public String registerComment(@ModelAttribute Comment comment, @PathVariable("articleId") Long articleId,
                                  RedirectAttributes redirectAttributes,
                                  Model model){

        commentService.insertArticle(comment);

        model.addAttribute("comment",comment);

        redirectAttributes.addAttribute("articleId",articleId);

        return "redirect:/Boards/{articleId}";
    }


    @PostMapping("/{articleId}")
    public String viewArticlePost(@PathVariable Long articleId, @ModelAttribute ArticleStore articleStore, @ModelAttribute Article article,
                                  ArticleStoreDto dto, Store store, @ModelAttribute Member member,
                                  Model model) {

        Optional<Article> article1 = articleService.findArticle(articleId);
        model.addAttribute("article", article1.get());

        UUID uuid = UUID.randomUUID();
        MultipartFile f = dto.getFile();
        String fname1 = f.getOriginalFilename(); // 원본 파일명
        String fname = "/"+ uuid +"_"+ fname1;
        File f2 = new File(path_article+article.getArticleId()); // 업로드된 파일을 저장할 새 파일 생성
        f2.mkdirs();
        File f3 = new File(f2+fname);


        try {
            System.out.println("ViewPost:"+f3.getAbsolutePath());
            f.transferTo(f3);



        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        articleStore.getOriginFilename(fname1);
        articleStore.getStoreFilename(f3.getAbsolutePath());
        articleStore.setArticle(article);
        article.setMember(member.getId());

        articleService.insertArticle(article);
        articleStoreService.insertArticleStore(articleStore);

        if(articleStore.getOriginFilename().isEmpty()){

            if(f3.delete()){
                System.out.println("삭제성공");
            }
            else{
                System.out.println("삭제실패");
            }
        }
        else{
            System.out.println("이상무");
        }
        articleStoreService.deleteEmptyName();

        return "thymeleaf/board/articleForm";
    }

    @GetMapping("/delete/{articleId}")
    public String delete(@PathVariable Long articleId,
                         @ModelAttribute Article article, Model model) {

        articleService.deleteAllImagesByArticleId(articleId);

        articleService.deleteArticle(articleId);

        System.out.println("삭제");
        return "redirect:/Boards";
    }



    @GetMapping("/writeArticle")
    public String writeArticle(@ModelAttribute("article") Article article, @ModelAttribute("articleStore") ArticleStore articleStore,
                               Model model) {

        model.addAttribute("article",article);
        model.addAttribute("articleStore",articleStore);

        return "thymeleaf/board/writeArticle";
    }

    @PostMapping("/writeArticle")
    public String register(ArticleStoreDto dto,
                           @ModelAttribute ArticleStore articleStore, @ModelAttribute("article") Article article,
                           @ModelAttribute Member member,
                           Model model) throws IOException {



        article.setMember(member.getId());

        articleService.save(article);

        System.out.println("1:"+member.getId());

        UUID uuid = UUID.randomUUID();
        MultipartFile f = dto.getFile();
        String fname1 = f.getOriginalFilename(); // 원본 파일명
        String fname2 = uuid +"_"+fname1;

        System.out.println("2:"+member.getId());

        String fname="/"+fname2;
        File f2 = new File(path_article+"ARTICLE"); // 업로드된 파일을 저장할 새 파일 생성
        f2.mkdirs();
        String fname3=f2+"/"+article.getArticleId();

        System.out.println("3:"+member.getId());

        File f3 = new File(fname3+fname);
        f3.mkdirs();

        System.out.print("2관문 통과");



        System.out.print("3관문 통과");

        try {
            f.transferTo(f3); // 파일 복사
            System.out.println("registerPost:"+f3.getAbsolutePath());
//            articleStoreService.save(articleStore);

            System.out.println("5:"+member.getId());
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            System.out.println("6:"+member.getId());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            System.out.println("7:"+member.getId());
        }

        System.out.print("4관문 통과");

        articleStore.getOriginFilename(fname1);
        articleStore.getStoreFilename(fname2);
        articleStore.setArticle(article);


        System.out.println("4:"+member.getId());


        articleStoreService.save(articleStore);

        if(articleStore.getOriginFilename().isEmpty()){

            if(f3.delete()){
                System.out.println("삭제성공");
            }
            else{
                System.out.println("삭제실패");
            }
        }
        else{
            System.out.println("이상무");
        }
        articleStoreService.deleteEmptyName();

        return "redirect:/Boards";
    }

    @GetMapping("/read_img/{articleId}/{img}")
    // 이미지는 바이너리값 -> byte[]
    public ResponseEntity<byte[]> read_img(@PathVariable String articleId, @PathVariable String img, StoreDto dto, Model model)throws IOException{

        File f = new File(path_article+"ARTICLE");
        String fname = f+"/"+articleId +"/";
        File f1 = new File(fname + img);
        System.out.println("read_img:"+f1);
        HttpHeaders header = new HttpHeaders(); // HttpHeaders : 여러 설정을 담음.
        ResponseEntity<byte[]> result = null; // ResponseEntity 응답 객체 선언.
        try { // 여러 설정값 중 Content-Type라는 값이 있음.
            header.add("Content-Type", Files.probeContentType(f1.toPath())); // 응답 데이터의 종류를 설정
            // 응답 객체 생성
            result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f1),
                    header, HttpStatus.OK);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/deletePhoto/{articleId}/{storeFilename}")
    public String deletePhoto(@PathVariable String storeFilename,@PathVariable Long articleId,@ModelAttribute Member member,@ModelAttribute Store store,
                              @ModelAttribute Article article,RedirectAttributes redirectAttributes,
                              @ModelAttribute("loginForm") LoginForm loginForm,
                              @CookieValue(value = "rememberId", required = false) String rememberId,Model model) throws IOException {
        article.setArticleId(articleId);
        store.getStoreFilename(storeFilename);

        String path1 = path_article +"ARTICLE"+"/"+articleId;
        File dir = new File(path1, storeFilename);
        System.out.println("view:"+dir);
        String[] files = dir.list(); // 디렉토리에 저장된 파일들 이름을 배열에 담아줌.

        if(dir.delete()){
            System.out.println("파일 삭제 성공!");
        }
        else{
            System.out.println("파일 삭제 실패.");
        }
        model.addAttribute("imgs", files);

        articleStoreService.delArticle(storeFilename);
        Optional<Member> mem = memberService.findMember(articleId);

        model.addAttribute("mem",mem);
        redirectAttributes.addAttribute("articleId",articleId);

        System.out.println("삭제됨articleId:"+articleId);
        System.out.println("삭제됨storeFilename:"+storeFilename);

        return "redirect:/Boards/updateArticle/{articleId}";
    }



    @GetMapping("/updateArticle/{articleId}")
    public String updateArticle(@PathVariable Long articleId,@ModelAttribute ArticleStore articleStore,@RequestParam(required=false) String storeFilename,
                               @ModelAttribute Article article, Model model) {

        article.setArticleId(articleId);
        articleStore.getStoreFilename(storeFilename);

        Optional<Article> article1 = articleService.findArticle(articleId);
        model.addAttribute("article", article1.get());





//        List<Store> member2 = storeService.findStore(num);
//        model.addAttribute("mem",member2);



        String path1 = path_article + "ARTICLE/"+articleId;
        File dir = new File(path1);
        dir.mkdirs();

        System.out.println("view:"+path1);
        String[] files = dir.list(); // 디렉토리에 저장된 파일들 이름을 배열에 담아줌.
        model.addAttribute("imgs", files);

//            model.addAttribute("imgs", files);

        return "thymeleaf/board/updateArticle";
    }

    @PostMapping("/updateArticle/{articleId}")
    public String updates(@ModelAttribute ArticleStore articleStore, @ModelAttribute Article article , ArticleStoreDto dto, @PathVariable Long articleId,
                          RedirectAttributes redirectAttributes,
                          @ModelAttribute("loginForm") LoginForm loginForm,
                          @CookieValue(value = "rememberId", required = false) String remember,
                          @ModelAttribute Member member,Model model) throws IOException {

        UUID uuid = UUID.randomUUID();
        MultipartFile f = dto.getFile();
        String fname1 = f.getOriginalFilename(); // 원본 파일명
        String fname = "/"+ uuid +"_"+ fname1;
        File f2 = new File(path_article+"ARTICLE/"+article.getArticleId()); // 업로드된 파일을 저장할 새 파일 생성
        f2.mkdirs();
        File f3 = new File(f2+"/"+fname);


        System.out.println("updatePost:"+f3.getAbsolutePath());
        f.transferTo(f3);

//        try {
//
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

        articleStore.getOriginFilename(fname1);
        articleStore.getStoreFilename(f3.getAbsolutePath());
        articleStore.setArticle(article);
        article.setMember(member.getId());

        System.out.println("값을 보여줘 : "+member.getId());

        articleService.save(article);
        articleStoreService.save(articleStore);

        if(articleStore.getOriginFilename().isEmpty()){

            if(f3.delete()){
                System.out.println("삭제성공");
            }
            else{
                System.out.println("삭제실패");
            }
        }
        else{
            System.out.println("이상무");
        }
        articleStoreService.deleteEmptyName();

        redirectAttributes.addAttribute("articleId",articleId);


        return "thymeleaf/board/articleView";

    }


}
