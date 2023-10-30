<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link rel="stylesheet" type="text/css" href="CSS/main.css">
</head>
<body>

    <header>
    </header>

    <nav>
    </nav>


    <main>

        <aside>
            <a href="/1"><span class="click_aside_st">눌러봐</span></a> <br>
            <a href="/2"><span class="click_aside_nd">어서빨리</span></a>
        </aside>


        <article>

            <c:if test="${id eq null}">
                <c:import url="/WEB-INF/views/login.jsp"/>
            </c:if>

            <c:if test="${id eq '1'}">
                <c:import url="/WEB-INF/views/join.jsp"/>
            </c:if>

            <c:if test="${id eq '2'}">
                <c:import url="/WEB-INF/views/login.jsp"/>
            </c:if>

        </article>

    </main>

    <footer>
    </footer>
</body>
</html>