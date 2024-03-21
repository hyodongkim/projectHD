package org.example.Filter;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/" , "/Members/signin", "/Members/signup", "/Members/logout", "/Members",
                                                "/CSS/*","/JS/*","/PROFILE/*","/IMG/*","layout/*" ,"/Boards","/Members/register"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();
        try {
            log.info("인증 체크 필터 시작{}", requestURI);
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행{}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null) {
                    log.info("미인증 사용자 요청 {}", requestURI);
                    // 로그인으로 redirect
//                    httpResponse.sendRedirect("/login?redirectURL="+ requestURI);
//                    httpResponse.sendRedirect("/Members/signup?redirectURL="+requestURI);
                    return;
                }

            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 체크 필터 종료{}", requestURI);
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크 X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI); // 화이트리스크에 있는건 !true = false 체크 안함
    }
}
