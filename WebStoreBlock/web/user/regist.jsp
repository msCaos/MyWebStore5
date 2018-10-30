<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link href="${pageContext.request.contextPath }/user/css/style.css" rel='stylesheet' type='text/css' />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!--webfonts-->
		<!--//webfonts-->
		<script src="js/setDate.js" type="text/javascript"></script>
		<script>
			function isUserUsernameAvailable() {
			    var username = document.getElementsByName("username")[0];
			    var msg = document.getElementById("messeage");
				if (username.value==""){
                    msg.innerText="用户名不能为空！";
                    msg.style.color="red";
			        return;
				}
				var request = new XMLHttpRequest();
			    	request.onreadystatechange=function () {  
			    	    if (request.readyState==4 && request.status==200){
			    	        var resTex = request.responseText;
			    	        // alert(resTex);
			    	        if (resTex=="true"){
                                msg.innerText="用户名可用";
                                msg.style.color="blue";
							}else {
                                msg.innerText="用户名已存在!";
                                msg.style.color="red";
							}
						}
					};
			    	var url="${pageContext.request.contextPath}/AjaxServlet?op=isUserUsernameAvailable&username="+username.value;
			    	request.open("GET",url,true);
			    	request.send(null)
            }
		</script>
		<script>
            function isEmailValid() {
                var email = document.getElementsByName("email")[0];
                var msg = document.getElementById("checkemail");
                var emailValue=email.value;
                //alert(emailValue);
                var reg = new RegExp("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");
                if (emailValue==""){
                    msg.innerText="邮箱不能为空！";
                    msg.style.color="red";
                }else if (reg.test(emailValue)){
                    msg.innerText="邮箱格式正确！";
                    msg.style.color="blue";
				}else {
                    msg.innerText="邮箱格式不正确！";
                    msg.style.color="red";
				}
            }
		</script>

		<script>
            function isBirthdayValid() {
                var birthday = document.getElementsByName("birthday")[0];
                var msg = document.getElementById("checkBirthday");
                var birthdayValue=birthday.value;
                //alert(birthdayValue);
                var reg = new RegExp("^((19[2-9]\\d{1})|(20((0[0-9])|(1[0-8]))))\\-((0?[1-9])|(1[0-2]))\\-((0?[1-9])|([1-2][0-9])|30|31)$");
                if (birthdayValue==""){
                    msg.innerText="出生日期不能为空！";
                    msg.style.color="red";
                }else if (reg.test(birthdayValue)){
                    msg.innerText="出生日期格式正确！";
                    msg.style.color="blue";
                }else {
                    msg.innerText="出生日期格式不正确！";
                    msg.style.color="red";
                }
            }
		</script>

	</head>

	<body>
		<div class="main" align="center">
			<div class="header">
				<h1>创建一个免费的新帐户！</h1>
			</div>
			<p></p>
			<form method="post" action="${pageContext.request.contextPath }/user/UserServlet">
				<input type="hidden" name="op" value="regist" />
				<ul class="left-form">
					<li>
						${msg.error.username }<br/>
						<input type="text" name="username" placeholder="用户名" value="${msg.username}" required="required" onblur="isUserUsernameAvailable()"/><br><span id="messeage"></span>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>

					</li>
					<li>
						${msg.error.nickname }<br/>
						<input type="text" name="nickname" placeholder="昵称" value="${msg.nickname}" required="required"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.email }<br/>
						<input type="text" name="email" placeholder="邮箱" value="${msg.email}" required="required" onblur="isEmailValid()"/><br><span id="checkemail"></span>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.password }<br/>
						<input type="password" name="password" placeholder="密码" value="${msg.password}" required="required"/>
						<a href="#" class="icon into"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.password }<br/>
						<input type="password" name="password1" placeholder="确认密码" value="${msg.password}" required="required"/>
						<a href="#" class="icon into"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.birthday }<br/>
						<input type="text" placeholder="出生日期" name="birthday" value="${msg.birthday}" required="required" size="15" onblur="isBirthdayValid()"/><br><span id="checkBirthday"></span>
						<div class="clear"> </div>
					</li>
					<li>
						<input type="submit" value="创建账户">
						<div class="clear"> </div>
					</li>
			</ul>

			<div class="clear"> </div>

			</form>

		</div>
		<!-----start-copyright---->

		<!-----//end-copyright---->

	</body>

</html>