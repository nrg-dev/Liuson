// JavaScript Document
 $(document).ready(function(){
							

$(document).ready(function() {
    $( 'ul.block-menu li' ).hover(
        function(){
            $(this).children('.sub-menu').slideDown(400);
        },
        function(){
            $(this).children('.sub-menu').slideUp(400);
        }
    );
});
							
							
});