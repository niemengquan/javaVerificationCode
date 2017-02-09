<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/2/4
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="application/javascript">
        var index=1;
        function addImg(e){
            var parentDiv=document.getElementById("insert");
            var topValue=0,leftValue=0;
            var obj=parentDiv;
            while (obj){
                leftValue+=obj.offsetLeft;
                topValue+=obj.offsetTop;
                obj=obj.offsetParent;
            }
            e=e||window.event;
            var left=e.clientX+document.body.scrollLeft - document.body.clientLeft-10;//-110px
            var top=e.clientY+document.body.scrollTop -document.body.clientTop-10;//
            var imgDivId="img_"+index++;

            var newDiv =document.createElement("div");
            parentDiv.appendChild(newDiv);

            newDiv.id=imgDivId;
            newDiv.style.position="relative";
            newDiv.style.zIndex=index;
            newDiv.style.width="20px";
            newDiv.style.height="20px";
            newDiv.style.top=top-topValue-150+10+"px";
            newDiv.style.left=left-leftValue-300+"px";
            newDiv.style.display="inline";
            newDiv.setAttribute("onClick","removeSelf('"+imgDivId+"')");

            var img=document.createElement("img");
            newDiv.appendChild(img);
            img.src="<%=request.getContextPath()%>/img/weixin.png";
            img.style.width="40px";
            img.style.height="40px";
            img.style.top="0px";
            img.style.left="0px";
            img.style.position="absolute";
            img.style.zIndex=index;
        }
        function removeSelf(id) {
            document.getElementById("insert").removeChild(document.getElementById(id));
        }
        function login() {
            var parentDiv=document.getElementById("insert");
            var nodes=parentDiv.childNodes;
            var result="";
            for(var i=0;i<nodes.length;i++){
                var imgId=nodes[i].id;
                console.log(imgId);
                if(imgId&&imgId.substring(0,4)=="img_"){
                    var top=document.getElementById(imgId).style.top;
                    var left=document.getElementById(imgId).style.left;
                    result=result+top.replace("px","")+','+left.replace("px","")+';';
                }
            }
            document.getElementById("location").value=result.substr(0,result.length-1);
            document.getElementById("loginForm").submit();
        }
    </script>
</head>
<body>
<div>
    <div>
        <ul>
            <form id="loginForm" action="${pageContext.request.contextPath}/loginController/login.do" method="post" >
                <input type="hidden" name="location" id="location">
                <li class="l_tit">邮箱/用户名/手机号</li>
                <li class="mb_10"><input type="text" name="userName" class="login_input user_icon"></li>
                <li class="l_tit">密码</li>
                <li class="mb_10"><input type="password" name="password" class="login_input user_icon"></li>
                <li>选出图片中的"${tip}"</li>
                <li >
                    <div id="insert">
                        <img src="<%=request.getContextPath()%>/mergeImage/${file}" height="150" width="300" onclick="addImg()">
                    </div>
                </li>
                <li><input type="button" value="登录" class="login_btn" onclick="login()"> </li>
            </form>
        </ul>
    </div>

</div>
</body>
</html>
