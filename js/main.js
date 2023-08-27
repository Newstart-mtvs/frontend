


let scrHig = $('section').height();

$(window).on('scroll',function(){

    let scr = $(window).scrollTop();
      
    if(scr >= 0 && scr < scrHig){
      $('nav').stop().animate({'right':'-100%'})
    }   
    if(scr >= scrHig){
      $('nav').stop().animate({'right':0})
    }


    function liClass(e){
      $('nav li').eq(e).addClass('on').siblings().removeClass('on')
  }

  if(scr >= 0 && scr < scrHig){
      liClass(0)
  }

  if(scr >= scrHig && scr < scrHig*2){
      liClass(1)
  }

  if(scr >= scrHig*2 && scr < scrHig*3){
      liClass(2)
  }

  if(scr >= scrHig*3 && scr < scrHig*4){
      liClass(3)
  } 

  if(scr >= scrHig*4){
      liClass(4)
  }

});


$('nav li').click(function(){

  let i = $(this).index()

  $('html, body').stop().animate({'scrollTop' : scrHig * i +1})

});


$('#wrap section').on('mousewheel',function(e,d){

  if(d>0){
    let prv = $(this).prev().offset().top;
  $('html, body').stop().animate({'scrollTop':prv});

  }else if(d<0){
    let nxt = $(this).next().offset().top;
  $('html, body').stop().animate({'scrollTop':nxt});  

  }
  
});

// const elemList = [$("img1"), $("#img2"), $("#img3"), $("#img4"), $("#login") ]

// function hideOrShow(idx) {
  
// }

// function hideFunc(elem) {
//   switch(elem) {
//     case 1:

//   }
// }

$(document).ready(function(){
  $("#img1").show();
  $("#img2").hide();
  $("#img3").hide();
  $("#img4").hide();
  $("#img5").hide();


  $("#img1").click(function(){
    $("#img1").hide();
    $("#img2").show();
    $("#img3").hide();
    $("#img4").hide();
    $("#img5").hide();


  });

  $("#img2").click(function(){
    $("#img1").hide();
    $("#img2").hide();
    $("#img3").show(); 
    $("#img4").hide();
    $("#img5").hide();

  });

  $("#img3").click(function(){
    $("#img1").hide();
    $("#img2").hide();
    $("#img3").hide(); 
    $("#img4").show();
    $("#img5").show();

  });

});

