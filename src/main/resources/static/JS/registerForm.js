$(document).keydown(function(e){

    if(e.shiftKey && e.which == "68"){
        $("#viewSetCount").attr('type','password');
    }


    if(e.which == "65" && e.ctrlKey){
        $("#viewSetHit").attr('type','password');

    }

});



