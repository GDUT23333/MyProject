<%--
  Created by IntelliJ IDEA.
  User: 111
  Date: 2020/4/24
  Time: 1:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>首页</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0">
  <style>
    #right{
      width: 400px;
      height: 40px;
      border: 2px solid orangered;
      text-indent: 20px;
      outline: none;
      border-radius: 20px;
    }
    #left{
      width: 70px;
      height: 40px;
      color: white;
      font-size: 15px;
      align-content: center;
      border: 2px solid orangered;
      background-color: orangered;
      border-radius: 20px;
      outline:none;
    }
    #userControl{
      float: right;
      margin-right: 30px;
      width: 300px;
      height: 200px;
      border: 1px solid grey;

    }
    .control{
      text-decoration:none;
      text-align: center;
      color: white;
      border: 1px solid;
      border-color: orangered;
      background-color: orangered;
      padding-right:25px;
      padding-left: 25px;
      padding-top: 10px;
      padding-bottom: 10px;
      border-radius: 20px;
      margin-top: 30px;
    }
    #show{
        border: 1px solid gainsboro;
        display: block;
        width: 100px;
        height: 100px;
        border-radius: 500%;
        margin-left: 100px;
    }
    #control_user{
      width: 300px;
      height: 90px;
        margin-top: 35px;
        margin-left: 20px;
    }
    #userShow{
      width: 300px;
      height: 100px;
    }
    #main{
      float: left;
      margin-left: 30px;
      width: 850px;
      height: 300px;
    }
    .nav{
        display: inline;
    }
    .navInit{
        list-style-type: none;
        display: inline;
        padding-left: 60px;
    }
      #search{
          width: 100%;
          height: 30px;
          background-color: orange;
          margin-bottom: 10px;
          font-size: 22px;

      }
    img{
        width: 100%;
        height: 100%;
    }
    .searchInit{
        font-size: 20px;
        color: white;
        text-decoration: none;
    }
    .initCenter{
        height: 170px;
        width: 40%;
        float: left;
    }
    .initRight{
        /**border:1px solid  black;*/
        width: 25%;
        height: 170px;
        float: left;
    }
    .initLeft{
        /*border: 1px solid black;*/
        width: 33%;
        height: 170px;
        float: left;
    }
    .introduce{
        margin-top: 3px;
        display:block;
        width: 280px;
        height: 80px;
        font-size: 15px;
        word-wrap:break-word;
        display:block;
        margin-left: 20px;
        color: gray;
    }
    .title{
        margin-top: 5px;
        display:block;
        width: 280px;
        height: 50px;
        font-size:18px;
        word-wrap:break-word;
        display:block;
        margin-left: 20px;
        color: black;
    }
    .price{
        width: 100%;
        float: left;
        margin-top: 40px;
        margin-left: 30px;
        font-size: 30px;
        color: red;
    }
    .detali{
        margin-top: 30px;
        float: left;
        margin-left: 30px;

    }
    .detali_a{
        font-size: 20px;
        color: gray;
        text-decoration: none;
    }
    #count{
        font-size: 20px;
        margin-left: 30px;
    }
    #low{
        padding-left: 20px;
        list-style-type: none;
    }
    .lowLi{
        padding-left: 10px;
        text-decoration: none;
        display: inline;
        list-style-type: none;
        font-size: 20px;
    }
    .lowa{
        color:blue;
        border: 1px solid gainsboro;
        padding: 5px;
        text-decoration: none;
        border-radius: 5px;
    }
  </style>
    <script>
        window.onload = function () {
            load(5,1);
        }
        function load(pageSize,currentPage) {
            var xhttp;
            if (window.XMLHttpRequest) {
                xhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xhttp.open("GET", '/pageSearchAll?pageSize='+pageSize+'&currentPage='+currentPage+'', true);
            xhttp.send();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    var lis = '<li id="searchfirst" class="navInit"><a href="#" class="searchInit">首页</a></li>';
                    var data = JSON.parse(xhttp.responseText);
                    var category = data.categories;
                    alert(xhttp.responseText);
                    if(data.login){
                        document.getElementById("say").innerHTML = "Hi!${sessionScope.two.nameTwo}";
                        document.getElementById("head").src = "./view/headPitcher/${sessionScope.two.headPitcher}";
                    }
                    for(var i = 0; i < category.length;i++){
                        //遍历数组，拼接li标签
                        var li = '<li class="navInit" ><a href='+category[i].id+'"user/search.jsp?id=" class="searchInit">'+category[i].name+'</a></li>'
                        lis += li;
                    }
                    document.getElementById("category").innerHTML = lis;
                    var count = document.getElementById("count");
                    count.innerHTML = '当前位于第'+currentPage+'页'
                    var lists = data.list;
                    var lis = "";
                    alert(lists.length);

                    for(var i = 0;i < lists.length;i++){
                        var img = "../view/img/"+lists[i].img;
                        var href = "";
                        if(data.login){
                            href = './view/user/detail.jsp?id='+lists[i].id;
                        }else{
                            href = './view/login/login.jsp';
                        }
                        lis += ' <li style="margin-top: 20px;width: 100%;height: 173px;border: 1px solid gainsboro;list-style-type: none">' +
                         '<div class="initLeft"><img src='+img+'></div>' +
                         '<div class="initCenter"><p class="title">'+lists[i].title+'</p><p class="introduce">'+lists[i].introduce+'</p></div>' +
                         '<div class="initRight"><span class="price">￥'+lists[i].price+'起</span>' +
                         '<span class="detali"><a class="detali_a" href="'+href+'">查看详情</a></span></div>' +
                         '</li>';
                    }
                    var lowUl = document.getElementById("lowUl");
                    lowUl.innerHTML = lis;
                    var before = currentPage-1;
                    if(before <= 0){
                        before = 1;
                    }
                    var lowInit = '<li class="lowLi" name="cut" onclick="load(5,'+before+')"><a class="lowa">上·一页</a></li>';
                    for(var i = 1;i <= data.totalPage;i++){
                        lowInit += '<li class="lowLi" name="cut" onclick="load(5,'+i+')"><a class="lowa">'+i+'</a></li>';
                    }
                    var after = currentPage + 1;
                    if(after >= data.totalPage){
                        after = data.totalPage;
                    }
                    lowInit += '<li class="lowLi" name="cut" onclick="load(5,'+after+')"><a class="lowa">下一页</a></li>';
                    document.getElementById("low").innerHTML = lowInit;
                }
            }
        }
    </script>
</head>
<body>
<div style="background-color: gainsboro">
  <a href="login/login.jsp" style="color:red;margin-left: 50px;text-decoration: none" target="_blank">亲，请登录</a>
  <a href="register/registerOne.jsp" style="color:grey;text-decoration: none" target="_blank">免费注册</a>
  <a href="#" style="color:red;margin-left: 100px;text-decoration: none">淘宝网首页</a>
</div>
<div>
    <form>
    <table style="margin: 25px;border-collapse: separate;border-spacing: 50px 0px;">
    <tr>
      <td width="100px"><a href="#"><img src="img/1.png"></a></td>
      <td>
        <input id="right" type="text" placeholder="输入关键字进行搜索">
        <input type="submit" id="left" value="搜索">
      </td>
    </tr>
    </table>
  </form>
  <div id="search">
      <ul class="nav" id="category">
      </ul>
  </div>
  <div id="userControl">
    <div id="userShow">
        <a id="show"><img src="img/head1.png" id="head"></a>
    </div>
      <div>
          <span style="margin-left: 120px;" id="say">Hi！你好</span>
      </div>
    <div id="control_user">
      <a href="login/login.jsp" class="control">登录</a>
      <a href="register/userRegister.jsp" class="control">注册</a>
      <a href="register/registerOne.jsp" class="control">开店</a>
    </div>
  </div>
</div>
<div id="main">
  <a href="#"><img src="img/first.png" style="width: 850px;height: 300px"></a>
</div>
<div style="width: 80%;margin-top: 330px;margin-left: 30px">
    <div style="width: 100%;height: 90%;">
        <div style="width: 100%;height: 30px;background-color: gainsboro;">
            <span style="width: 100px;text-align: center;margin-left: 400px;font-size: 18px">商品信息</span>
            <span style="margin-left: 400px;font-size: 18px">价格</span>
        </div>
        <ul style="padding: 0px" id="lowUl">
            <li style="width: 100%;height: 173px;border: 1px solid green;list-style-type: none">
            </li>
        </ul>
    </div>
    <div style="width: 100%;height: 10%;">
            <span id="count"></span>
            <ul id="low">
                <li class="lowLi" name="cut"><a class="lowa">上·一页</a></li>
                <li class="lowLi" name="cut"><a class="lowa">下一页</a></li>
            </ul>
    </div>

</div>
</body>
</html>
