// JavaScript Document

$(window).resize(function() {
  
 }); 
 
  
		
 
/* function auto_refresh(){
			var randomnumber = Math.floor(Math.random() * 100);
			
		}*/
   $(document).ready(function(){
							  
							  jQuery.easing['jswing']=jQuery.easing['swing'];jQuery.extend(jQuery.easing,{easeOutBounce:function(x,t,b,c,d){if((t/=d)<(1/2.75)){return c*(7.5625*t*t)+b}else if(t<(2/2.75)){return c*(7.5625*(t-=(1.5/2.75))*t+.75)+b}else if(t<(2.5/2.75)){return c*(7.5625*(t-=(2.25/2.75))*t+.9375)+b}else{return c*(7.5625*(t-=(2.625/2.75))*t+.984375)+b}}});

		 $('.left-img').hover(function() {
    $(this).stop().animate({
        "margin-left":"50px"
    }, 900, "easeOutBounce");
}, function() {
    $(this).stop().animate({
        "margin-left":"10px"
    }, 900, "easeOutBounce");
});	
		  $('.right-img').hover(function() {
    $(this).stop().animate({
        "margin-left":"80px"
    }, 900, "easeOutBounce");
}, function() {
    $(this).stop().animate({
        "margin-left":"60px"
    }, 900, "easeOutBounce");
});	
				/*$('.left-img1').hover({
				$('.left-img').animate({
							"margin-left":"40px"
						  },800);			  
									 });*/
							  //auto_refresh();
var windowsize = $(window).width();  //alert(windowsize);

if (windowsize < 480) {
	
	setInterval(function(){
					   $( ".logo:hidden:first" ).fadeIn( "slow" );
						},1000);
   					
					setInterval(function(){  
						$('.menu1').animate({
							"margin-left":"0px",
							"margin-top":'165px'
						  },400)
						  
						  },2000);
						 setInterval(function(){  
						$('.menu1').animate({
						    "margin-left":"0px",
							"margin-top":'165px'
						  },400)
						  
						  },2000);
						  setInterval(function(){  
						$('.menu1').animate({
						    "margin-left":"0px",
							"margin-top":'160px'
						  },600)
						  
						  },2000); 
						  
						  setInterval(function(){  
						$('.menu2').animate({
							"margin-left":"80px",
							"margin-top":'160px'
						  },400)
						  
						  },2500);
						  setInterval(function(){  
						$('.menu2').animate({
						    "margin-left":"80px",
							"margin-top":'165px'
						  },600)
						  
						  },2500);
						  setInterval(function(){  
						$('.menu2').animate({
						    "margin-left":"80px",
							"margin-top":'160px'
						  },600)
						  
						  },2500);
						
						setInterval(function(){  
						$('.menu3').animate({
							"margin-left":"160px",
							"margin-top":'160px'
						  },400)
						  
						  },3000);
						  setInterval(function(){  
						$('.menu3').animate({
						    "margin-left":"160px",
							"margin-top":'165px'
						  },600)
						  
						  },3000);
						  setInterval(function(){  
						$('.menu3').animate({
						    "margin-left":"160px",
							"margin-top":'160px'
						  },600)
						 },3000);
						 
						 setInterval(function(){  
						$('.menu4').animate({
							"margin-left":"240px",
							"margin-top":'160px'
						  },400)
						  
						  },3500);
						  setInterval(function(){  
						$('.menu4').animate({
						    "margin-left":"240px",
							"margin-top":'165px'
						  },600)
						  
						  },3500);
						  setInterval(function(){  
						$('.menu4').animate({
						    "margin-left":"240px",
							"margin-top":'160px'
						  },600)
						 },3500);
						
						 
						  setInterval(function(){  
						$('.menu7').animate({
							"margin-left":"160px",
							"margin-top":'230px'
						  },400)
						  
						  },4000);
						  setInterval(function(){  
						$('.menu7').animate({
						    "margin-left":"160px",
							"margin-top":'230px'
						  },600)
						  
						  },4000);
						  setInterval(function(){  
						$('.menu7').animate({
						    "margin-left":"165px",
							"margin-top":'235px'
						  },600)
						 },4000);
						 
						 
						  setInterval(function(){  
						$('.menu6').animate({
							"margin-left":"80px",
							"margin-top":'230px'
						  },400)
						  
						  },4500);
						  setInterval(function(){  
						$('.menu6').animate({
						    "margin-left":"80px",
							"margin-top":'235px'
						  },600)
						  
						  },4500);
						  setInterval(function(){  
						$('.menu6').animate({
						    "margin-left":"80px",
							"margin-top":'230px'
						  },600)
						 },4500);
						 
						 setInterval(function(){  
						$('.menu5').animate({
							"margin-left":"0px",
							"margin-top":'230px'
						  },400)
						  
						  },5000);
						  setInterval(function(){  
						$('.menu5').animate({
						    "margin-left":"5px",
							"margin-top":'230px'
						  },600)
						  
						  },5000);
						  setInterval(function(){  
						$('.menu5').animate({
						    "margin-left":"0px",
							"margin-top":'230px'
						  },600)
						 },5000);
	
	
}
	
	else if (windowsize > 480 && windowsize < 800) {
		//$("#header").load(location.href + "#header");
		
		setInterval(function(){
					   $( ".logo:hidden:first" ).fadeIn( "slow" );
						},1000);
						
						setInterval(function(){  
						$('.menu1').animate({
						    "margin-left":"110px",
							"margin-top":'40px'
						  },700)
						  
						  },2000);
						
						  
						setInterval(function(){   
						 $('.menu2').animate({
						    "margin-left":"170px",
							"margin-top":'150px'
						  },700)
						  
						  },2500); 
						 
						  
						setInterval(function(){  
						$( ".menu4:hidden:first" ).fadeIn( "2000" );
						  },3000);
						setInterval(function(){  
						$('.menu4').animate({
						    "margin-left":"360px",
							"margin-top":'130px'
						  },500)
						  
						  },3500);
						setInterval(function(){
					 		 $( ".book:hidden:first" ).fadeIn( "slow" );
						},3500);
						   
						  setInterval(function(){  
						$( ".menu3:hidden:first" ).fadeIn( "2000" );
						  },4000);
						setInterval(function(){  
						$('.menu3').animate({
						    "margin-left":"258px",
							"margin-top":'165px'
						  },600)
						  
						  },4000);
						  
						  
						  setInterval(function(){  
						$( ".menu5:hidden:first" ).fadeIn( "2000" );
						  },4500);
						 
						setInterval(function(){  
						$('.menu5').animate({
						    "margin-left":"484px",
							"margin-top":'110px'
						  },500)
						  
						  },5000);
						setInterval(function(){
							 $( ".british:hidden:first" ).fadeIn( "slow" );
						},5000);
						  
						  setInterval(function(){  
						$( ".menu6:hidden:first" ).fadeIn( "2000" );
						  },5500);
						  setInterval(function(){  
						$('.menu6').animate({
						    "margin-left":"570px",
							"margin-top":'100px'
						  },600)
						  
						  },5500);
						  
						  setInterval(function(){  
						$('.menu7').animate({
						    "margin-left":'568px',
							"margin-top":'15px'
						  },500)
						  
						  },6000);
		
	}
		
		else {
			//$("#header").load(location.href + "#header");
			 $( ".logo:hidden:first" ).fadeIn( "slow" );
			setInterval(function(){
					 //  $( ".logo:hidden:first" ).fadeIn( "slow" );
						},1000);
						
						setInterval(function(){  
						$('.menu1').animate({
						    "margin-left":"130px",
							"margin-top":'60px'
						  },700)
						  
						  },2000);
						setInterval(function(){  
						$('.menu1').animate({
						    "margin-left":"133px",
							"margin-top":'60px'
						  },700)
						  
						  },2000);
						setInterval(function(){  
						$('.menu1').animate({
						    "margin-left":"130px",
							"margin-top":'63px'
						  },700)
						  
						  },2000);
						
						  
						 setInterval(function(){   
						 $('.menu2').animate({
						    "margin-left":"200px",
							"margin-top":'200px'
						  },700)
						  
						  },2500); 
						  setInterval(function(){   
						 $('.menu2').animate({
						    "margin-left":"204px",
							"margin-top":'200px'
						  },700)
						  
						  },2500); 
						   setInterval(function(){   
						 $('.menu2').animate({
						    "margin-left":"200px",
							"margin-top":'204px'
						  },700)
						  
						  },2500); 
						 
						  
						setInterval(function(){  
						$( ".menu4:hidden:first" ).fadeIn( "2000" );
						  },3000);
						setInterval(function(){  
						$('.menu4').animate({
						    "margin-left":"511px",
							"margin-top":'180px'
						  },500)
						  
						  },3500);
						setInterval(function(){  
						$('.menu4').animate({
						    "margin-left":"515px",
							"margin-top":'180px'
						  },500)
						  
						  },3500);
						setInterval(function(){  
						$('.menu4').animate({
						    "margin-left":"511px",
							"margin-top":'184px'
						  },500)
						  
						  },3500);
						
						setInterval(function(){
					 		 $( ".book:hidden:first" ).fadeIn( "slow" );
						},3500);
						  
						  setInterval(function(){  
						$( ".menu3:hidden:first" ).fadeIn( "2000" );
						  },4000);
						setInterval(function(){  
						$('.menu3').animate({
						    "margin-left":"305px",
							"margin-top":'190px'
						  },600)
						  
						  },4000);
						setInterval(function(){  
						$('.menu3').animate({
						    "margin-left":"309px",
							"margin-top":'190px'
						  },600)
						  
						  },4000);
						setInterval(function(){  
						$('.menu3').animate({
						    "margin-left":"305px",
							"margin-top":'194px'
						  },600)
						  
						  },4000);
						  
						  setInterval(function(){  
						$( ".menu5:hidden:first" ).fadeIn( "2000" );
						  },4500);
						 
						setInterval(function(){  
						$('.menu5').animate({
						    "margin-left":"670px",
							"margin-top":'160px'
						  },500)
						  
						  },5000);
						setInterval(function(){  
						$('.menu5').animate({
						    "margin-left":"674px",
							"margin-top":'160px'
						  },500)
						  
						  },5000);
						setInterval(function(){  
						$('.menu5').animate({
						    "margin-left":"670px",
							"margin-top":'164px'
						  },500)
						  
						  },5000);
						
						setInterval(function(){
							 $( ".british:hidden:first" ).fadeIn( "slow" );
						},5000);
						  
						  setInterval(function(){  
						$( ".menu6:hidden:first" ).fadeIn( "2000" );
						  },5500);
						  setInterval(function(){  
						$('.menu6').animate({
						    "margin-left":"780px",
							"margin-top":'105px'
						  },600)
						  
						  },5500);
						  setInterval(function(){  
						$('.menu6').animate({
						    "margin-left":"784px",
							"margin-top":'105px'
						  },600)
						  
						  },5500);
						  setInterval(function(){  
						$('.menu6').animate({
						    "margin-left":"780px",
							"margin-top":'109px'
						  },600)
						  
						  },5500);
						  
						  setInterval(function(){  
						$('.menu7').animate({
						    "margin-left":'760px',
							"margin-top":'15px'
						  },500)
						  
						  },6000);
						   setInterval(function(){  
						$('.menu7').animate({
						    "margin-left":'764px',
							"margin-top":'15px'
						  },500)
						  
						  },6000);
						    setInterval(function(){  
						$('.menu7').animate({
						    "margin-left":'760px',
							"margin-top":'19px'
						  },500)
						  
						  },6000);
						  
						  
										  
			
		}

 


  
 });
				   
				 /*  $(document).ready(function(){
					   
						  
						  
				 });*/		  

/*
setInterval(function(){
					   $( ".logo:hidden:first" ).fadeIn( "slow" );
						},1000);
						setInterval(function(){ 
								var position = $('.menu1').position();
								var lft_val = position.left + 8 ;
								var top_val = position.top + 8 ;
								//alert(top_val); 
								$('.menu1').animate({
									left:lft_val,
									top:top_val
								  },1000)
						  },2000);*/
				   
				 
				   
				  