<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>main</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />

    <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
    <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>

    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="CSS/main.css">
    <link rel="stylesheet" href="IMG/">
    <script src="JS/main.js" defer></script>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display&display=swap" rel="stylesheet">
  </head>
<body>

<div class="whole">
  <div class="fixBox" onmouseover="fadeIn();">
    <header>
      <h1 class="logo">Mr.Kim</h1>
    </header>

    <nav>
      <a href="">MD & CC</a>
      <a href="">메인</a>
      <a href="">인테리어</a>
      <a href="">주방</a>
      <a href="">침실</a>
      <a href="">소개</a>
      <a href="">문의</a>
      <i class="fa-solid fa-bag-shopping"></i>
    </nav>
  </div>

  <div class="main_background" onmouseover="fadeOut();">
    <main>
        <div class="box" data-aos="zoom-in">
          <div class="swiper">

            <div class="swiper-wrapper">

              <div class="swiper-slide"><img src="./IMG/img1.webp"></div>
              <div class="swiper-slide"><img src="./IMG/img3.webp"></div>
            </div>
          </div>
        </div>

            <div class="box" data-aos="zoom-in"><img src="./IMG/img10.webp"></div>


            <div class="box" data-aos="zoom-in"><img src="./IMG/img6.webp"></div>


            <div class="box" data-aos="zoom-in"><img src="./IMG/img9.webp"></div>


            <div class="box" data-aos="zoom-in"><img src="./IMG/img12.webp"></div>


            <div class="box" data-aos="zoom-in"><img src="./IMG/img7.webp"></div>



    </main>


  </div>
  <footer>

      <div class="footerLeft">
        <div class="logo">Mr.Kim</div>
      </div>

      <div class="footerRight">
        41-1, Gangguhaean-gil, Ganggu-myeon, Yeongdeok-gun, Gyeongsangbuk-do 36460 Korea<br>
        Copyright 한국인력강화진흥원 All right reserved

        <div class="footerRight_i">
          <i class="fa-brands fa-facebook"></i> 페이스북 &nbsp;
          <i class="fa-brands fa-instagram"></i> 인스타그램 &nbsp;
          <i class="fa-brands fa-twitter"></i>트위터
        </div>
      </div>

  </footer>

  <div class="attention">
    © 2035 hyodong.com을 통해 제작된 본 홈페이지에 대한 모든 권리는 김효동(사업자명)에 귀속됩니다.
  </div>

</div>
</body>
</html>