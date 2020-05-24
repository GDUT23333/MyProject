<%--
  Created by IntelliJ IDEA.
  User: 111
  Date: 2020/4/24
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页面</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <style>
        #back{
            background: url("../img/3.png");
            background-repeat: no-repeat;
            background-size: 100% 100%;
            height: 493px;
        }
        #init{
            padding-left: 800px;
            padding-top: 100px;

        }
        .show{
            width: 200px;
            height: 25px;
            border-radius: 5px;
            padding:10px;
        }
        #login{
            width:200px;
            height: 50px;
            font-size: 20px;
            color: white;
            background-color: orangered;
            border: 1px;
            border-color: orangered;
        }
        .right{
            font-size: 18px;
        }
    </style>
    <script>
        window.onload = function() {

            alert("${cookie.userName.value }");
            alert("${cookie.userPassword.value}");
            document.getElementById("name").value = "${cookie.userName.value }";
            document.getElementById("password").value = "${cookie.userPassword.value}";
            document.getElementById("form").onsubmit = function () {

                if (checkName() && checkPassword()) {
                    var name = document.getElementById("name").value;
                    var password = document.getElementById("password").value;
                    var isRember = "N";
                    if(document.getElementById("rember").checked){
                        isRember = "Y";
                    }
                    alert(isRember);
                    var xhttp;
                    if (window.XMLHttpRequest) {
                        xhttp = new XMLHttpRequest();
                    } else {
                        // code for IE6, IE5
                        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    xhttp.open("GET",'/loginServlet?name='+name+'&password='+password+'&isRember='+isRember+'', true);
                    xhttp.send();
                    xhttp.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            if (xhttp.responseText == "商家登录成功") {
                                location.href = "../marchant/marchantControl.jsp";
                            } else if(xhttp.responseText == "用户登录成功"){
                                location.href = "/index.jsp";
                            }else{
                                alert(xhttp.responseText);
                            }
                        }
                    }
                }
                return false;
            }
        }
        function checkName(){
            var flag = true;
            var name = document.getElementById("name").value;
            if("" == name){
                alert("用户名不能为空");
                flag = false;
            }
            return flag;
        }
        function checkPassword() {
            var flag = true;
            var password = document.getElementById("password").value;
            if(password==""){
                alert("密码不能为空");
                flag = false;
            }
            return flag;
        }
    </script>
</head>
<body>
<div>
    <a href=""><img src="../img/1.png" style="margin-left: 30px;width: 120px;height: 50px;margin-top: 15px"></a>
</div>
<div id="back">
<div id="init">
    <form action="#" method="post" id="form">
        <table style="background-color: honeydew;width: 380px;height: 300px">
            <tr>
                <td style="font-size:18px ">密码登录</td>
            </tr>
            <tr align="center">
                <td  class="right">
                    <span>用户:</span>
                <input type="text" class="show" placeholder="请输入邮箱或手机号" name="name" id="name">
                </td>
            </tr>
            <tr align="center">
                <td  class="right" >
                    <span>密码:</span>
                <input type="password" class="show" placeholder="请输入密码" name="password" id="password">
                </td>
            </tr>
            <tr>
                <td class="right">
                    <input id="rember" type="checkbox" style="margin-left: 60px">记住密码
                </td>
            </tr>
            <tr align="center">
                <td><input type="submit" value="登录" id="login"></td>
            </tr>
        </table>
    </form>
</div>
</div>

</body>
</html>
