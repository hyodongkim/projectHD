package org.example.Controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Dto.ArticleStoreDto;
import org.example.Dto.MemberForm;
import org.example.Dto.StoreDto;
import org.example.Entity.*;
import org.example.Service.ArticleService;
import org.example.Service.ArticleStoreService;
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
import org.springframework.validation.annotation.Validated;
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

    @PostMapping("/{articleId}")
    public String viewArticlePost(@PathVariable Long articleId, @ModelAttribute ArticleStore articleStore, @ModelAttribute Article article, Model model,
                                  ArticleStoreDto dto, Store store) {


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



        articleService.registerArticle(article);
        articleStoreService.save(articleStore);


        if(store.getOriginFilename().isEmpty()){
            articleStoreService.deleteEmptyName();
            if(f3.delete()){
                System.out.println("인식함");
            }
            else{
                System.out.println("인식못함");
            }
        }
        else{
            System.out.println("이상무");
        }

        return "thymeleaf/board/view";
    }

    @GetMapping("/delete/{articleId}")
    public String delete(@PathVariable Long articleId, Model model) {
        articleService.deleteArticle(articleId);
        System.out.println("삭제");
        return "redirect:/Boards";
    }



//    @GetMapping("/register")
//    public String registerForm(@ModelAttribute Member member,@ModelAttribute Store store, Model model) {
//
//
//        memberService.register(member);
//
//        model.addAttribute("member", member);
//
//        return "thymeleaf/member/registerForm";
//    }
//
//    @PostMapping("/register")
//    public String register(@Validated @ModelAttribute MemberForm form, @RequestParam("id") Long id, BindingResult bindingResult, StoreDto dto, @ModelAttribute Member member,
//                           RedirectAttributes redirectAttributes, @ModelAttribute Store store, Model model) throws IOException {
//        // 검증 실패 시 다시 입력폼으로 포워드
//        if (bindingResult.hasErrors()) {
//            log.info("bindingResults : {}", bindingResult);
//            // BindingResult는 모델에 자동 저장된다.
//            return "thymeleaf/member/registerForm";
//        }
//
//
//        UUID uuid = UUID.randomUUID();
//        MultipartFile f = dto.getFile();
//        String fname1 = f.getOriginalFilename(); // 원본 파일명
//        String fname2 = uuid +"_"+ fname1;
//        String fname="/"+fname2;
//        File f2 = new File(path+id); // 업로드된 파일을 저장할 새 파일 생성
//        f2.mkdirs();
//        File f3 = new File(f2+fname);
//
//        try {
//            f.transferTo(f3); // 파일 복사
//            System.out.println("registerPost:"+f3.getAbsolutePath());
//            storeService.save(store);
//        } catch (IllegalStateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        store.getOriginFilename(fname1);
//        store.getStoreFilename(fname2);
//        store.setMember(member);
//
//        memberService.register(member);
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
////        return "thymeleaf/member/view";
//        return "redirect:/Members";
//    }
//
//    @GetMapping("/read_img/{id}/{img}")
//    // 이미지는 바이너리값 -> byte[]
//    public ResponseEntity<byte[]> read_img(@PathVariable String id, @PathVariable String img, StoreDto dto, Model model)throws IOException{
//
//        File f = new File(path+id);
//        String fname = f+"/";
//        File f1 = new File(fname + img);
//        System.out.println("read_img:"+f1);
//        HttpHeaders header = new HttpHeaders(); // HttpHeaders : 여러 설정을 담음.
//        ResponseEntity<byte[]> result = null; // ResponseEntity 응답 객체 선언.
//        try { // 여러 설정값 중 Content-Type라는 값이 있음.
//            header.add("Content-Type", Files.probeContentType(f1.toPath())); // 응답 데이터의 종류를 설정
//            // 응답 객체 생성
//            result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f1),
//                    header, HttpStatus.OK);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    @GetMapping("/deletePhoto/{id}/{storeFilename}")
//    public String deletePhoto(@PathVariable String storeFilename,@PathVariable Long id,@ModelAttribute Member member,@ModelAttribute Store store,
//                              RedirectAttributes redirectAttributes,Model model) throws IOException {
//        member.setId(id);
//        store.getStoreFilename(storeFilename);
//
//        String path1 = path + id;
//        File dir = new File(path1, storeFilename);
//        System.out.println("view:"+dir);
//        String[] files = dir.list(); // 디렉토리에 저장된 파일들 이름을 배열에 담아줌.
//
//        if(dir.delete()){
//            System.out.println("파일 삭제 성공!");
//        }
//        else{
//            System.out.println("파일 삭제 실패.");
//        }
//        model.addAttribute("imgs", files);
//
//        storeService.delMember(storeFilename);
//        Optional<Member> mem = memberService.findMember(id);
//
//        model.addAttribute("mem",mem);
//        redirectAttributes.addAttribute("id",id);
//
//        System.out.println("삭제됨Id:"+id);
//        System.out.println("삭제됨storeFilename:"+storeFilename);
//
//        return "redirect:/Members/updateMember/{id}";
//    }



}
