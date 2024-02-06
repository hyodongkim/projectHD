package org.example.Controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Dto.LoginForm;
import org.example.Dto.MemberForm;
import org.example.Entity.Member;
import org.example.Repository.MemberRepository;
import org.example.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import java.util.UUID;

@Controller
@RequestMapping("/Members")
@Slf4j
public class MemberController {

    @Value("${file.dir}")
    private String fileDir;

    @Value("${spring.servlet.multipart.location}")
    private String location;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("member", new Member());

        return "thymeleaf/member/registerForm";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute MemberForm form, @RequestParam(required = false) MultipartFile imageFile,
                           RedirectAttributes redirectAttributes, BindingResult bindingResult, Model model) throws IOException {
        // 검증 실패 시 다시 입력폼으로 포워드
        if (bindingResult.hasErrors()) {
            log.info("bindingResults : {}", bindingResult);
            // BindingResult는 모델에 자동 저장된다.
            return "thymeleaf/member/registerForm";
        }

        // 검증 성공
        Member registerMember = new Member();
//        registerMember.setId(form.getId());
        registerMember.setUserid(form.getUserid());
        registerMember.setPassword(form.getPassword());
        registerMember.setEmail(form.getEmail());
        registerMember.setName(form.getName());
        registerMember.setSex(form.getSex());
        registerMember.setAge(form.getAge());
        registerMember.setBirth(form.getBirth());
        registerMember.setDay(form.getDay());
        registerMember.setIntroduction(form.getIntroduction());
        registerMember.setPhoto(String.valueOf(imageFile));


        // 회원 등록

        memberService.register(registerMember);
//        redirectAttributes.addAttribute("memberId", userId);
        model.addAttribute("member",form);
        redirectAttributes.addAttribute("status", "new");
        // 회원 상세로 리다이렉트
//        return "thymeleaf/member/view";
        return "redirect:/Members";
    }
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Optional<Member> member= memberService.findMember(id);
        model.addAttribute("member",member);
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

        return "thymeleaf/member/list";
    }


    @GetMapping("/signup")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm, @CookieValue(value = "rememberId", required = false) String rememberId, Model model) {
        if(rememberId != null) {
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
            Optional<Member> optional = memberService.findMember(loginMember.getId());
            model.addAttribute("member", optional);
        } else {
            Cookie cookie = new Cookie("rememberId", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            Optional<Member> optional = memberService.findMember(loginMember.getId());
            model.addAttribute("member", optional);
        }
///        return "redirect:"+redirect;
        return "thymeleaf/member/view";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session  = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/Members";
    }

    @GetMapping("/updateMember/{id}")
    public String updateMember(@PathVariable Long id,Model model, @ModelAttribute("form") Member form){

        Member member = new Member();

        form.setId(member.getId());
        form.setUserid(member.getUserid());
        form.setPassword(member.getPassword());
        form.setEmail(member.getEmail());
        form.setName(member.getName());
        form.setSex(member.getSex());
        form.setAge(member.getAge());
        form.setBirth(member.getBirth());
        form.setDay(member.getDay());
        form.setIntroduction(member.getIntroduction());
        form.setPhoto(member.getPhoto());

        Optional<Member> member1 = memberService.findMember(id);

        model.addAttribute("member1",member1);


        return "thymeleaf/member/updateForm";
    }
    @PostMapping("/updateMember/{id}")
    public String updates(@RequestParam(required = false) MultipartFile imageFile, @ModelAttribute("form") MemberForm form,@PathVariable Long id, Model model) throws IOException {


        Optional<Member> member2 = memberService.findMember(id);

        // 검증 성공
        Member registerMember = new Member();
        registerMember.setId(form.getId());
        registerMember.setUserid(form.getUserid());
        registerMember.setPassword(form.getPassword());
        registerMember.setEmail(form.getEmail());
        registerMember.setName(form.getName());
        registerMember.setSex(form.getSex());
        registerMember.setAge(form.getAge());
        registerMember.setBirth(form.getBirth());
        registerMember.setDay(form.getDay());
        registerMember.setIntroduction(form.getIntroduction());
        registerMember.setPhoto(String.valueOf(imageFile));

        memberService.updateMember(registerMember);


        model.addAttribute("member2",member2);


        return "redirect:/Members";
    }
}
