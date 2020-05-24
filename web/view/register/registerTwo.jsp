<%--
  Created by IntelliJ IDEA.
  User: 111
  Date: 2020/4/24
  Time: 1:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>注册页面</title>
    <style>
        #password,#c_password,#name,#phone,#nameTwo,#addr{
            width: 150px;
            height: 30px;
            border: 1px solid gray;
            /*设计边框圆角*/
            border-radius: 5px;
        }
        .right{
            text-align: right;
        }
        .left{
            padding-left: 30px;
            width: 100px;
            height: 45px;
        }
        #next{
            width:150px;
            height: 40px;
            font-size: 20px;
            color: white;
            background-color: orangered;
            border: 1px;
            border-color: orangered;
            margin-left: 550px;
        }
        .error{
            color: red;
        }

    </style>
    <script>
        window.onload = function () {
            //绑定离焦事件
            document.getElementById("password").onblur = checkPassword;
            document.getElementById("c_password").onblur = comfirmPassword;
            document.getElementById("name").onblur = checkName;
            document.getElementById("nameTwo").onblur = checkTwoName;
            document.getElementById("phone").onblur = checkPhone;
            document.getElementById("addr").onblur = checkAddr;
            document.getElementById("form").onsubmit = function () {
                var flag =  checkPassword()&&comfirmPassword()&&checkName()&&checkTwoName()&&checkPhone()&&checkAddr();
                if(flag){
                    loadPhone();
                }
            return false;
        }
        function loadPhone() {
            var flag = false;
            var phone = document.getElementById("phone").value;
            var xhttp;
            if (window.XMLHttpRequest) {
                xhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xhttp.open("GET", "/registerCheckPhone?phone=" + phone, true);
            xhttp.send();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    if (xhttp.responseText == "手机未被注册") {
                        var email = document.getElementById("email").value;
                        var password = document.getElementById("password").value;
                        var name = document.getElementById("name").value;
                        var nameTwo = document.getElementById("nameTwo").value;
                        var phone = document.getElementById("phone").value;
                        var sex = document.getElementById("form").sex.value;
                        var addr = document.getElementById("addr").value;
                        var merchant = {"email":email,"password":password,"name":name,"nameTwo":nameTwo,"phone":phone,"sex":sex,"addr":addr,"headPitcher":"head1.png"};
                        var data = JSON.stringify(merchant);
                        location.href = "/registerFinally?data=" + encodeURIComponent(data);
                    } else {
                        alert(xhttp.responseText);
                    }
                }
            }
        }
       function checkPassword() {
            var password = document.getElementById("password").value;
            var s_password = document.getElementById("s_password");
            var reg_password = /^\w{6,12}$/;
            var flag = reg_password.test(password);
            if(!flag){
                 s_password.innerHTML="密码长度要为6~10位" ;
            }else{
                s_password.innerHTML="<img width='35' height='25' src='view/img/gouzi.png'/>";
            }
            return flag;
        }

        function comfirmPassword() {
            var password = document.getElementById("password").value;
            var c_password = document.getElementById("c_password").value;
            var twos_password = document.getElementById("twos_password");
            var flag = true;
            if(password != c_password){
                twos_password.innerHTML="两次密码输入不一致";
                flag = false;
            }else{
               twos_password.innerHTML="<img width='35' height='25' src='view/img/gouzi.png'/>";
            }
            return flag;
        }
        function checkName() {
            var username = document.getElementById("name").value;
            var reg_username = /^[\u2E80-\u9FFF]+$/;
            var s_username = document.getElementById("s_username");
            var flag = reg_username.test(username);
            if(!flag){
                s_username.innerHTML="用户名非法";
            }else{
                s_username.innerHTML="<img width='35' height='25' src='view/img/gouzi.png'/>";
            }
            return flag;
        }
            function checkAddr() {
                var addr = document.getElementById("addr").value;
                var reg_addr = /^[\u2E80-\u9FFF]+$/;
                var s_addr = document.getElementById("s_addr");
                var flag = reg_addr.test(addr);
                if(!flag){
                    s_addr.innerHTML="地址名不对";
                }else{
                    s_addr.innerHTML="<img width='35' height='25' src='view/img/gouzi.png'/>";
                }
                return flag;
            }
        function checkPhone() {
            var userphone = document.getElementById("phone").value;
            var reg_userphone = /^1[3456789]\d{9}$/;
            var s_phone = document.getElementById("s_phone");
            var flag = reg_userphone.test(userphone);
            if(!flag){
                s_phone.innerHTML="手机号码格式不规范";
            }else{
                s_phone.innerHTML="<img width='35' height='25' src='view/img/gouzi.png'/>";
            }
            return flag;
        }
        function checkTwoName() {
            var twoName = document.getElementById("nameTwo").value;
            var s_nameTwo = document.getElementById("s_nameTwo");
            var flag = false;
            if(""==twoName){
                s_nameTwo.innerHTML="昵称不能为空";
            }else{
                s_nameTwo.innerHTML="<img width='35' height='25' src='view/img/gouzi.png'/>";
                flag = true;
            }
            return flag;
        }
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
    <a href="#" style="margin-left: 30px"><img src="view/img/1.png" style="width: 100px;margin-top: 20px"></a>
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
    <hr size="5px" color="red" style="width:35%;float: left">
    <hr size="5px" color="gainsboro" style="width:39%;float: left">
</div>
<form action="#" id="form" method="post">
    <table  style="margin-top: 40px;margin-left: 500px">
        <tr><td class="right"><span>登录邮箱:</span></td>
            <td><input name="email" id="email" value="${requestScope.email}" readOnly="readOnly" style="font-size: 20px;border:none;outline:none;">
            </td>
        </tr>
        <tr>
            <td class="right"><span>登录密码</span></td>
            <td class="left"><input type="text" name="password" id="password" placeholder="输入你的密码"></td>
            <td><span id="s_password" class="error"></span></td>
        </tr>
        <tr>
            <td class="right"><span>密码确认</span></td>
            <td class="left"><input type="text" id="c_password" placeholder="再次确认密码"></td>
            <td><span id="twos_password" class="error"></span></td>
        <tr>
            <td class="right">
                <span>姓名</span>
            </td>
            <td class="left">
                <input type="text" name="name" id="name" placeholder="请输入姓名">
            </td>
            <td><span id="s_username" class="error"></span></td>
        </tr>
        <tr>
            <td class="right">
                <span>昵称</span>
            </td>
            <td class="left">
                <input type="text" name="nameTwo" id="nameTwo" placeholder="请输入昵称">
            </td>
            <td><span id="s_nameTwo" class="error"></span></td>
        </tr>
        <tr>
            <td class="right">
                <span>地址</span>
            </td>
            <td class="left">
                <input type="text" name="addr" id="addr" placeholder="请输入地址（店名）">
            </td>
            <td><span id="s_addr" class="error"></span></td>
        </tr>
        <tr>
            <td class="right">
                <span>手机号</span>
            </td>
            <td class="left"><input type="text" name="phone" id="phone" placeholder="请输入手机号">
            </td>
            <td><span id="s_phone" class="error"></span></td>
        </tr>
        <tr><td class="right"><span>性别</span></td>
            <td class="left"><span>男</span><input type="radio" checked name="sex" value="男">
                <span>女</span><input type="radio" name="sex" value="女"></td>
        </tr>

    </table>
    <div>
        <input type="submit" value="下一步" id="next">
    </div>
</form>

</body>
</html>
