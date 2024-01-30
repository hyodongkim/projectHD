//function fixBox_slideUp(){
//    $(".fixBox").slideUp();
//
//};
//function fixBox_slideDown(){
//    $(".fixBox").slideDown();
//};
        AOS.init();

        let swiper1 = new Swiper('.swiper1', {
            // Optional parameters
            direction: 'horizontal',
            loop: true,
            autoplay: {
                delay: 9000,
            },

            // If we need pagination
            pagination: {
              el: '.swiper-pagination',
            },

            // Navigation arrows
            navigation: {
              nextEl: '.swiper-button-next',
              prevEl: '.swiper-button-prev',
            },

            // And if we need scrollbar
            scrollbar: {
              el: '.swiper-scrollbar',
            },
 });


let swiper2 = new Swiper('.swiper2', {
            // Optional parameters
            direction: 'horizontal',
            loop: true,
            autoplay: {
                delay: 9000,
            },

            // If we need pagination
            pagination: {
              el: '.swiper-pagination',
            },

            // Navigation arrows
            navigation: {
              nextEl: '.swiper-button-next',
              prevEl: '.swiper-button-prev',
            },

            // And if we need scrollbar
            scrollbar: {
              el: '.swiper-scrollbar',
            },
 });




//    function click_aside(e){
//        //  $("#menu_button").css('backround','linear-gradient(90deg , white, orange )').show();
//        //  $("#menu_bar").slideDown();
//        e.preventDefault();
//
//
//        var target = document.querySelector('article');
//        var player = target.animate([
//        { transform: 'translateX(0%)'},
//        { transform: 'translateX(50%)'},
//        { transform: 'translateX(100%)'},
//        { transform: 'translateX(100%)'},
//        { transform: 'translateX(50%)'},
//        { transform: 'translateX(0%)'}
//        ],{
//            duration : 100,
//            delay:15000
//        });
//
//        player.addEventListener('finish', function() {
//            target.style.transform = 'translateX(0%)';
//        });
//
//    };




