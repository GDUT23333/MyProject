<%--
  Created by IntelliJ IDEA.
  User: 111
  Date: 2020/5/16
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息修改</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <style>
        #mostTop{
            width: 100%;
            height: 40px;
            font-size: 20px;
            margin-left: 10px;
        }
        #my{
            text-decoration: none;
            font-size: 25px;
            color: white;
            letter-spacing: 5px;
        }
        #top{
            width: 100%;
            height: 35px;
            background-color: orangered;
            padding: 10px;
        }
        .control{
            text-decoration: none;
            margin-left: 30px;
            color: white;
            font-size: 15px;
        }
        #information{
            width: 700px;
            height: 490px;
            border: 3px solid gray;
            /*margin-left: 330px;*/
            margin-top: 15px;
            margin-left: 35px;
            float: left;
        }
        .right{
            text-align: right;
        }
        .left{
            padding-left: 30px;
            width: 100px;
            height: 67px;
        }
        #trueName,#nameTwo,#phone{
            width: 150px;
            height: 30px;
            border: 1px solid gray;
            /*设计边框圆角*/
            border-radius: 5px;
        }
        #save{
            width:150px;
            height: 40px;
            font-size: 20px;
            color: white;
            background-color: orangered;
            border: 1px;
            border-color: orangered;
            margin-left: 200px;
        }
        #sign{
            width: 350px;
            height: 30px;
            border: 1px solid gray;
            /*设计边框圆角*/
            border-radius: 5px;
        }
        .headPitcher{
            width:100px;
            height: 60px;

        }
        #float_right{
            float: left;
            color: lightskyblue;
            font-size: 40px;
            margin-top: 40px;
            margin-left: 20px;
        }
        .error{
            color: red;
        }
        #show{
            border: 1px solid gainsboro;
            display: block;
            width: 65px;
            height: 65px;
            border-radius: 500%;
            margin-left: 20px;
        }
        #head{
            width: 100%;
            height: 100%;
            border-radius: 500%;
        }
    </style>
    <script>
        window.onload = function(){
            document.getElementById("quit").onclick = function () {
                var flag = confirm("是否确认退出");
                if(flag){
                    location.href = "/exitServlet";
                }
            }
            document.getElementById("form").onsubmit = function () {

                document.getElementById("nameTwo").onblur = checkNameTwo;
                document.getElementById("phone").onblur = checkPhone;
                document.getElementById("trueName").onblur = checkTrueName;
                if(checkNameTwo()&&checkPhone()&&checkTrueName()){
                    var xhttp;
                    if (window.XMLHttpRequest) {
                        xhttp = new XMLHttpRequest();
                    } else {
                        // code for IE6, IE5
                        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    xhttp.open("post", "/modifyUserServlet",true)
                    var data = new FormData(document.querySelector('form'));
                    xhttp.send(data);
                    xhttp.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            if (xhttp.responseText == "修改成功") {
                                alert(xhttp.responseText);
                                location.href = "./userImformation.jsp";
                            } else {
                                alert(xhttp.responseText);
                            }
                        }
                    }
                }
                return false;
            }
        }

        function checkNameTwo() {
            var s_twoName = document.getElementById("s_twoName");
            var nameTwo = document.getElementById("nameTwo").value;
            var flag = true;
            if("" == nameTwo){
                s_twoName.innerHTML = "昵称不能为空";
                flag = false;
            }else{
                s_twoName.innerHTML="<img width='35' height='25' src='../img/gouzi.png'/>";
                flag = true;
            }
            return flag;
        }
        function checkPhone() {
            var s_phone = document.getElementById("s_phone");
            var phone = document.getElementById("phone").value;
            var reg_userphone = /^1[3456789]\d{9}$/;
            var flag = reg_userphone.test(phone);
            if(!flag){
                s_phone.innerHTML = "电话格式不规范";
            }else{
                s_phone.innerHTML="<img width='35' height='25' src='../img/gouzi.png'/>";
            }
            return flag;
        }
        function checkTrueName() {
            var s_trueName = document.getElementById("s_trueName");
            var trueName = document.getElementById("trueName").value;
            var reg_username = /^[\u2E80-\u9FFF]+$/;
            var flag = reg_username.test(trueName);
            if(!flag){
                s_trueName.innerHTML = "姓名格式不规范";
            }else{
                s_trueName.innerHTML="<img width='35' height='25' src='../img/gouzi.png'/>";
            }
            return flag;
        }
        function changePitcher(imgFiles) {
            var src = window.URL.createObjectURL(imgFiles.files[0]);
            document.getElementById("head").src = src;
        }

    </script>
</head>
<body>
<div id="mostTop">
    <span>欢迎回来！</span>
    <span>${sessionScope.one.nameTwo}!</span>
    <a href="#" style="color: dodgerblue" id="quit">退出</a>
</div>
<div id="top">
    <a href="#" id="my">我的淘宝</a>
    <a href="./userControl.jsp" class="control">购物车</a>
    <a href="./userImformation.jsp" class="control">账户设置</a>
</div>
<div style="width:18%;height: 100px;float: left;margin-top: 20px;font-size: 36px;color: deepskyblue">
    用户信息修改
</div>
<div style="width: 70%;height: 500px;border: 1px solid gainsboro;border-radius: 10px;margin-top: 20px;float: left">
    <form id="form" method="post" enctype="multipart/form-data">
        <table style="margin-left: 90px">
            <tr>
                <td class="right">
                    <span>昵称：</span>
                </td>
                <td class="left">
                    <input type="text" name="name" id="nameTwo" value="${sessionScope.two.nameTwo}">
                </td>
                <td><span id="s_twoName" class="error"></span></td>
            </tr>
            <tr>
                <td class="right">
                    <span>头像：</span>
                </td>
                <td class="headPitcher">
                    <a id="show"><img src="../headPitcher/${sessionScope.two.headPitcher}" id="head" ></a>
                    <input type="file" accept="image/png" id="file" name="file" style="margin-left: 20px;" onchange="changePitcher(this)">
                </td>
            <tr>
            </tr>
            </tr>
            <tr>
                <td class="right">
                    <span>联系电话：</span>
                </td>
                <td class="left">
                    <input type="text" name="phone" id="phone" value="${sessionScope.two.phone}">
                </td>
                <td><span id="s_phone" class="error"></span></td>
            </tr>
            <tr>
                <td class="right">
                    <span>真实姓名：</span>
                </td>
                <td class="left">
                    <input type="text" name="trueName" id="trueName" value="${sessionScope.two.name}">
                </td>
                <td><span id="s_trueName" class="error"></span></td>
            </tr>
            <tr>
                <td class="right">
                    <span>性别：</span>
                </td>
                <td class="left">
                    <span>男</span>
                    <input type="radio" checked name="sex" id= "sex" value="男">
                    <span>女</span>
                    <input type="radio" name="sex" value="女">
                </td>
            </tr>
            <tr>
                <td class="right">
                    <span>个性签名：</span>
                </td>
                <td class="left">
                    <input type="text" name="sian" id="sign" value="${sessionScope.two.sign}">
                </td>
            </tr>
        </table>
        <div>
            <input type="submit" value="保存" id="save">
        </div>
    </form>
</div>
</body>
</html>
