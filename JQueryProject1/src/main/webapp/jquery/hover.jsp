<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){
	for(let i=2;i<7;i++)
	{
		$('body').append('<img src=cgv'+i+'.jpg width=120 height=180 style="margin-left:5px">')	
	}
	$('img').css('cursor','pointer')
	$('img').hover(function(){
		$(this).css('opacity',.3); //mouseover 마우스 갖다 댔을때
	},function(){
		$(this).css('opacity',1); //mouseout 마우스 뗐을때
	})
})
</script>
</head>
<body>
	
</body>
</html>