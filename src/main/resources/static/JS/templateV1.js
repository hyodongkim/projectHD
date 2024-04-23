window.onload = function(){
    $(".fixBox").css("display" ,"none");
}
function fixBox_slideUp(){
    $(".fixBox").slideUp();

};
function fixBox_slideDown(){
    $(".fixBox").slideDown();
};
function new_window() {
    window.open(
        "http://localhost:5000"
    );
}


