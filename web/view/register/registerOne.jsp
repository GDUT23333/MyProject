<%--
  Created by IntelliJ IDEA.
  User: 111
  Date: 2020/4/24
  Time: 1:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>注册页面</title>
    <style>
        #email{
            width: 180px;
            height: 20px;
            border: 1px solid gray;
            /*设计边框圆角*/
            border-radius: 5px;
            padding:10px;
        }
        #next{
            width:150px;
            height: 40px;
            font-size: 20px;
            color: white;
            background-color: orangered;
            border: 1px;
            border-color: orangered;
        }
    </style>
    <script>
        window.onload=function () {
            document.getElementById("form").onsubmit = function () {
                if (checkEmail()) {
                    var email = document.getElementById("email").value;
                    var xhttp;
                    if (window.XMLHttpRequest) {
                        xhttp = new XMLHttpRequest();
                    } else {
                        // code for IE6, IE5
                        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    xhttp.open("GET", "/registerCheckEmail?email=" + email, true);
                    xhttp.send();
                    xhttp.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            if (xhttp.responseText == "邮箱未被注册") {
                                location.href = "/registerSendEmail?email=" + email;
                            } else {
                                alert(xhttp.responseText);
                            }
                        }
                    }
                }
                return false;
            }
        }
        function checkEmail(){
            var email = document.getElementById("email").value;
            var flag = false;
            if(email==""){
                alert("不能为空");
                flag = false;
            }else{
                flag=true;
            }
            return flag;
        }
    </script>
</head>
<body>
<!--吊顶-->
<div style="background-color: gainsboro">
    <a href="#" style="color:red;margin-left: 50px;text-decoration: none">亲，请登录</a>
    <a href="#" style="color:grey;text-decoration: none">免费注册</a>
    <a href="#" style="color:red;margin-left: 100px;text-decoration: none">淘宝网首页</a>
</div>
<!--头部-->
<div>
    <a href="#" style="margin-left: 30px"><img src="../img/1.png" style="width: 100px;margin-top: 20px"></a>
    <span style="font-size: 20px;height: 30px">商家注册</span>
</div>
<div>
    <ul style="list-style-type: none">
        <li style="float: left;font-size: 20px;margin-left:350px">设置用户名</li>
        <li  style="float: left;font-size: 20px;margin-left:100px">填写账号信息</li>
        <li  style="float: left;font-size: 20px;margin-left:100px">注册成功</li>
    </ul>
    <br><br>

</div>
<div>
    <hr size="5px" color="gainsboro" style="width: 25%;float: left;">
    <hr size="5px" color="red" style="width:20%;float: left">
    <hr size="5px" color="gainsboro" style="width:54%;float: left">
</div>
<div align="center" style="margin: 100px;">
    <form action="#" method="post" id="form">
        <table>
            <tr align="center">
                <td><span style="font-size: 30px">电子邮箱:</span>
                <input type="email" placeholder="请输入电子邮箱" name="email" id="email"></td>
            </tr>
            <tr align="center">
                <td><input type="submit" value="下一步" id="next"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
