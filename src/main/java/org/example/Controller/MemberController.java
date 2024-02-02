package org.example.Controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Dto.HdMemberForm;
import org.example.Dto.LoginForm;
import org.example.Dto.MemberForm;
import org.example.Entity.Member;
import org.example.Repository.MemberRepository;
import org.example.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Members")
@Slf4j
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/register")
    public String registerForm(@ModelAttribute("member") Member member) {
        return "thymeleaf/member/registerForm";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("member") MemberForm member, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes, Model model) {
        // 검증 실패 시 다시 입력폼으로 포워드
        if (bindingResult.hasErrors()) {
            log.info("bindingResults : {}", bindingResult);
            // BindingResult는 모델에 자동 저장된다.
            return "thymeleaf/member/registerForm";
        }

        // 검증 성공
        Member registerMember = new Member();
        registerMember.setId(member.getId());
        registerMember.setUserid(member.getUserid());
        registerMember.setPassword(member.getPassword());
        registerMember.setEmail(member.getEmail());
        registerMember.setName(member.getName());
        registerMember.setSex(member.getSex());
        registerMember.setAge(member.getAge());
        registerMember.setBirth(member.getBirth());
        registerMember.setDay(member.getDay());
        registerMember.setIntroduction(member.getIntroduction());
        registerMember.setPhoto(member.getPhoto());

        // 회원 등록
        Long userId = memberService.register(registerMember);

        redirectAttributes.addAttribute("memberId", userId);
        redirectAttributes.addAttribute("status", "new");
        // 회원 상세로 리다이렉트
//        return "thymeleaf/member/view";
        return "redirect:/Members";
    }
    @GetMapping("/{userid}")
    public String view(@PathVariable String userid, Model model) {
        Optional<Member> optional = memberService.findMember(userid);
        model.addAttribute("member", optional.get());
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
            Optional<Member> optional = memberService.findMember(loginMember.getUserid());
            model.addAttribute("member", optional.get());
        } else {
            Cookie cookie = new Cookie("rememberId", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            Optional<Member> optional = memberService.findMember(loginMember.getUserid());
            model.addAttribute("member", optional.get());
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

    @GetMapping("/updateMember/{userid}")
    public String updateMember(@PathVariable String userid,Model model){

        Optional<Member> member = memberService.findMember(userid);
//        model.addAttribute("member",optional.get());
        Member form = new Member();

        form.setId(member.get().getId());
        form.setUserid(member.get().getUserid());
        form.setPassword(member.get().getPassword());
        form.setEmail(member.get().getEmail());
        form.setName(member.get().getName());
        form.setSex(member.get().getSex());
        form.setAge(member.get().getAge());
        form.setBirth(member.get().getBirth());
        form.setDay(member.get().getDay());
        form.setIntroduction(member.get().getIntroduction());
        form.setPhoto(member.get().getPhoto());

        model.addAttribute("member",form);

        return "thymeleaf/member/updateForm";
    }
    @PostMapping("/updateMember/{userid}")
    public String updates(@ModelAttribute("form") MemberForm form,@PathVariable String userid, Model model){

//        Optional<Member> member = memberService.findMember(id);

        Member member = new Member();
        member.setId(form.getId());
        member.setUserid(form.getUserid());
        member.setPassword(form.getPassword());
        member.setEmail(form.getEmail());
        member.setName(form.getName());
        member.setSex(form.getSex());
        member.setAge(form.getAge());
        member.setBirth(form.getBirth());
        member.setDay(form.getDay());
        member.setIntroduction(form.getIntroduction());
        member.setPhoto(form.getPhoto());

        memberService.updateMember(member);

        return "redirect:/Members";
    }
}
