package org.example.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.Dto.*;

import org.example.Entity.Member;
import org.example.Entity.Store;
import org.example.Repository.MemberRepository;

import org.example.Service.MemberService;
import org.example.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
import org.springframework.web.util.UriUtils;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

import static org.apache.commons.io.FilenameUtils.getFullPath;

@Controller
@RequestMapping("/Members")
@Slf4j
public class MemberController {

    @Value("${spring.servlet.multipart.location}")
    private String path;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreService storeService;

    private final String rootPath = System.getProperty("user.dir");
    // 프로젝트 루트 경로에 있는 files 디렉토리
    private final String fileDir = rootPath + "/src/main/resource/static/PROFILE/";


    @GetMapping("/register")
    public String registerForm(Model model) {


        model.addAttribute("member", new Member());

        return "thymeleaf/member/registerForm";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute MemberForm form, BindingResult bindingResult, StoreDto dto, @ModelAttribute Member member,
                           RedirectAttributes redirectAttributes, Model model) throws IOException {
        // 검증 실패 시 다시 입력폼으로 포워드
        if (bindingResult.hasErrors()) {
            log.info("bindingResults : {}", bindingResult);
            // BindingResult는 모델에 자동 저장된다.
            return "thymeleaf/member/registerForm";
        }


        MultipartFile f = dto.getFile();
        String fname = f.getOriginalFilename(); // 원본 파일명
        File f2 = new File(path + fname); // 업로드된 파일을 저장할 새 파일 생성
        try {
            f.transferTo(f2); // 파일 복사
            System.out.println(f2.getAbsolutePath());
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        memberService.register(member);
//        return "thymeleaf/member/view";
        return "redirect:/Members";
    }

    @GetMapping("/read_img/{img}")
    // 이미지는 바이너리값 -> byte[]
    public ResponseEntity<byte[]> read_img(@PathVariable String img){
        File f = new File(path+img);
        HttpHeaders header = new HttpHeaders(); // HttpHeaders : 여러 설정을 담음.
        ResponseEntity<byte[]> result = null; // ResponseEntity 응답 객체 선언.
        try { // 여러 설정값 중 Content-Type라는 값이 있음.
            header.add("Content-Type", Files.probeContentType(f.toPath())); // 응답 데이터의 종류를 설정
            // 응답 객체 생성
            result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f),
                    header, HttpStatus.OK);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {


        Optional<Member> member1 = memberService.findMember(id);
        model.addAttribute("member", member1.get());

        File dir = new File(path);
        String[] files = dir.list(); // 디렉토리에 저장된 파일들 이름을 배열에 담아줌.
        model.addAttribute("imgs", files);


        return "thymeleaf/member/view";
    }

    @PostMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {


        Optional<Member> member1 = memberService.findMember(id);
        model.addAttribute("member", member1.get());


        return "thymeleaf/member/view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        memberService.deleteMember(id);
        System.out.println("삭제");
        return "redirect:/Members";
    }


    @GetMapping
    /* default page = 0, default size = 10 */
    public String listBySearchAndPaging(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(required = false, defaultValue = "") String search, Model model) {

        Page<Member> page = memberService.findMembers(search, pageable);

        long totalElements = page.getTotalElements();
        List<Member> list = page.getContent();
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

        return "thymeleaf/member/list";
    }


    @GetMapping("/signup")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm, @CookieValue(value = "rememberId", required = false) String rememberId, Model model) {
        if (rememberId != null) {
            loginForm.setUserid(rememberId);
            loginForm.setRemember(true);
        }
        return "thymeleaf/member/loginForm";
    }

    @PostMapping("/signin")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult,
                        @RequestParam(name = "redirect", defaultValue = "thymeleaf/member/view") String redirect, HttpServletRequest request, HttpServletResponse response, Model model) {
        log.info("로그인저장 체크 : {}", loginForm.getRemember());
        log.info("리다이렉트 : {}", redirect);
        if (bindingResult.hasErrors()) {
            // BindingResult는 모델에 자동 저장된다.
            return "thymeleaf/member/loginForm";
        }

        Member loginMember = memberService.isMember(loginForm.getUserid(), loginForm.getPassword());
        if (loginMember == null) {
            // 글로벌 오류 생성 및 로그인화면으로 포워드
            bindingResult.reject("loginFail", "아이디 또는 비밀번호를 확인하여 주세요.");
            return "thymeleaf/member/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);

        // 로그인 저장 체크시
        if (loginForm.getRemember()) {
            Cookie cookie = new Cookie("rememberId", loginMember.getUserid());
            cookie.setMaxAge(60 * 60 * 24 * 30);
            cookie.setPath("/");
            response.addCookie(cookie);
            Optional<Member> optional = memberService.intoLogin(loginMember.getUserid());
            model.addAttribute("member", optional.get());
        } else {
            Cookie cookie = new Cookie("rememberId", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            Optional<Member> optional = memberService.intoLogin(loginMember.getUserid());
            model.addAttribute("member", optional.get());
        }
///        return "redirect:"+redirect;
        return "thymeleaf/member/view";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/Members";
    }

    @GetMapping("/updateMember/{id}")
    public String updateMember(@PathVariable Long id, Model model) {


        Optional<Member> member1 = memberService.findMember(id);
        model.addAttribute("member", member1.get());


        return "thymeleaf/member/updateForm";
    }

    @PostMapping("/updateMember/{id}")
    public String updates(@ModelAttribute MemberForm form, StoreDto dto, @PathVariable Long id, Model model) throws IOException {

        MultipartFile f = dto.getFile();
        String fname = f.getOriginalFilename(); // 원본 파일명
        File f2 = new File(path + fname); // 업로드된 파일을 저장할 새 파일 생성
        try {
            f.transferTo(f2); // 파일 복사
            System.out.println(f2.getAbsolutePath());
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        storeService.save(dto);

            return "redirect:/Members";

    }
}

