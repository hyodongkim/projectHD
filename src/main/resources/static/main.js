    $(document).ready(function(){

    });


    function click_aside(){
        //  $("#menu_button").css('backround','linear-gradient(90deg , white, orange )').show();
        //  $("#menu_bar").slideDown();

        var target = document.querySelector('article');
        var player = target.animate([
        { transform: 'translateX(0%)'},
        { transform: 'translateX(50%)'},
        { transform: 'translateX(100%)'},
        { transform: 'translateX(100%)'},
        { transform: 'translateX(50%)'},
        { transform: 'translateX(0%)'}
        ],{
            duration : 1500,
            delay:300
        });

        player.addEventListener('finish', function() {
            target.style.transform = 'translateX(0%)';
        });
    };
