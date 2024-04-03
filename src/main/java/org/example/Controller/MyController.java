package org.example.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.Dto.LoginForm;
import org.example.Dto.StoreDto;
import org.example.Entity.Article;
import org.example.Entity.Member;
import org.example.Service.ArticleService;
import org.example.Service.MemberService;
import org.example.ServiceImpl.MyServiceImpl;
import org.example.Entity.MyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class MyController {

    @Autowired
    private MyServiceImpl Service;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ArticleService articleService;

    @Value("${spring.servlet.multipart.location}")
    private String path;

    @GetMapping("/")
    public String realmain(Model model) {
//        List<entity> list = Service.findAll();
//        model.addAttribute("list", list);
        return "thymeleaf/index";
//        return "redirect:/admin/views/admin_notice";
    }
    @GetMapping("/myPage")
    public String myPage(@SessionAttribute(name = "name", required = false) String name,
                         @CookieValue(value = "memberId", required = false) Long id,
                         Model model) {
        List<Article> article = articleService.findMembersName(name);
        model.addAttribute("article",article);

        String path1 = path + id;
        File dir = new File(path1);
        System.out.println("view:"+path1);
        String[] files = dir.list(); // 디렉토리에 저장된 파일들 이름을 배열에 담아줌.
        model.addAttribute("imgs", files);

        List<Member> member = memberService.findProfileName(name);
        model.addAttribute("member",member);

        return "thymeleaf/myPage/myPage";
    }

    @GetMapping("/read_img/{img}")
    // 이미지는 바이너리값 -> byte[]
    public ResponseEntity<byte[]> read_img(@CookieValue(value = "memberId", required = false) Long id, @PathVariable String img, StoreDto dto, Model model)throws IOException {

        File f = new File(path+id);
        String fname = f+"/";
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


    @GetMapping("/test")
    public String test_main(Model model) {
//        List<entity> list = Service.findAll();
//        model.addAttribute("list", list);
        return "views/main";
//        return "redirect:/admin/views/admin_notice";
    }
}