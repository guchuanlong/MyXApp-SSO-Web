$(function(){
$(".login_title li").click(function () {
                $(".login_title li").each(function () {
                    $(this).removeClass("current");
                });
                $(this).addClass("current");
            });
$('.login_title li').click(function(){
  var index=$('.login_title li').index(this);
      if(index==0){
      $('.form1').show();
      $('.form2').hide();
   }
   if(index==1){
     $('.form1').hide();
     $('.form2').show();
   }
  }) ;
});

$(function(){
$(".caozuo dl").click(function () {
                $(".caozuo dl").each(function () {
                    $(this).removeClass("gou");
                });
                $(this).addClass("gou");
            });
});