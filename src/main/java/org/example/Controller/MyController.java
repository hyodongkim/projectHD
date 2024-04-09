package org.example.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.Dto.LoginForm;
import org.example.Dto.StoreDto;
import org.example.Entity.Article;
import org.example.Entity.Member;
import org.example.Entity.Store;
import org.example.Service.ArticleService;
import org.example.Service.ArticleStoreService;
import org.example.Service.MemberService;
import org.example.Service.StoreService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
public class MyController {

    @Autowired
    private MyServiceImpl Service;

    @Autowired
    private MemberService memberService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleStoreService articleStoreService;

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
        List<Article> article = articleService.findMembersId(id);
//        Optional<Article> article = articleService.findByMember(id);
        model.addAttribute("article",article);

        String path1 = path + id;
        File dir = new File(path1);
        System.out.println("view:"+path1);
        String[] files = dir.list(); // 디렉토리에 저장된 파일들 이름을 배열에 담아줌.
        model.addAttribute("imgs", files);

        List<Member> member = memberService.findProfileId(id);
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
    @GetMapping("/delete")
    public String delete(HttpServletRequest request, HttpServletResponse response,
                        @SessionAttribute(name = "Id", required = false) Long id, Model model) throws IOException{

        articleStoreService.deleteArticleStoreMember(id);

        articleService.deleteArticleMember(id);

        System.out.println("회원PK:"+id);
        storeService.deleteMemberImage(id);

        memberService.deleteMember(id);
        System.out.println("삭제");


        return "redirect:/deleteMemberPage";
    }
    @GetMapping("/deletePhoto/{storeFilename}")
    public String deletePhoto(@PathVariable String storeFilename,@SessionAttribute(name = "Id", required = false) Long id,@ModelAttribute Member member,@ModelAttribute Store store,
                              RedirectAttributes redirectAttributes,Model model) throws IOException {
        member.setId(id);
        store.getStoreFilename(storeFilename);

        String path1 = path + id;
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

        storeService.delMember(storeFilename);
        Optional<Member> mem = memberService.findMember(id);

        model.addAttribute("mem",mem);
        redirectAttributes.addAttribute("id",id);

        System.out.println("삭제됨Id:"+id);
        System.out.println("삭제됨storeFilename:"+storeFilename);

        return "redirect:/updateMember";
    }

    @GetMapping("/updateMember")
    public String updateMember(@SessionAttribute(name = "Id", required = false) Long id,@ModelAttribute Store store,@RequestParam(required=false) String storeFilename,
                               @ModelAttribute Member member, Model model) {

        member.setId(id);
        store.getStoreFilename(storeFilename);

        Optional<Member> member1 = memberService.findMember(id);
        model.addAttribute("member", member1.get());





//        List<Store> member2 = storeService.findStore(num);
//        model.addAttribute("mem",member2);



        String path1 = path + id;
        File dir = new File(path1);
        System.out.println("view:"+path1);
        String[] files = dir.list(); // 디렉토리에 저장된 파일들 이름을 배열에 담아줌.
        model.addAttribute("imgs", files);

//            model.addAttribute("imgs", files);

        return "thymeleaf/myPage/myUpdateForm";
    }
    @PostMapping("/updateMember")
    public String updates(@SessionAttribute(name = "Id", required = false) Long id,
                          @ModelAttribute Store store, @ModelAttribute Member member , StoreDto dto,
                          RedirectAttributes redirectAttributes, Model model) throws IOException {

        UUID uuid = UUID.randomUUID();
        MultipartFile f = dto.getFile();
        String fname1 = f.getOriginalFilename(); // 원본 파일명
        String fname = "/"+ uuid +"_"+ fname1;
        File f2 = new File(path+member.getId()); // 업로드된 파일을 저장할 새 파일 생성
        f2.mkdirs();
        File f3 = new File(f2+fname);


        try {
            System.out.println("updatePost:"+f3.getAbsolutePath());
            f.transferTo(f3);



        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        store.getOriginFilename(fname1);
        store.getStoreFilename(f3.getAbsolutePath());
        store.setMember(member);



        memberService.save(member);
        storeService.save(store);


        if(store.getOriginFilename().isEmpty()){

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

        storeService.deleteEmptyName();

        redirectAttributes.addAttribute("id",id);

        return "redirect:/myPage";

    }
    @GetMapping("/deleteMemberPage")
    public String deletePageMember(HttpServletRequest request, HttpServletResponse response,Model model) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie myCookie = new Cookie("id", null);
        myCookie.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
        myCookie.setPath("/"); // 모든 경로에서 삭제 됬음을 알린다.
        response.addCookie(myCookie);

        Cookie myCookie1 = new Cookie("memberId", null);
        myCookie1.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
        myCookie1.setPath("/"); // 모든 경로에서 삭제 됬음을 알린다.
        response.addCookie(myCookie1);


        return "thymeleaf/popup/deleteMemberPage";
    }

}