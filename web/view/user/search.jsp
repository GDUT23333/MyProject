<%--
  Created by IntelliJ IDEA.
  User: 111
  Date: 2020/5/1
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>搜索页面</title>
    <style>
        #right{
            width: 400px;
            height: 40px;
            border: 2px solid orangered;
            text-indent: 20px;
            outline: none;
            border-radius: 20px;
        }
        #search{
            padding-right: 14px;
            padding-left: 14px;
            padding-top: 7px;
            padding-bottom: 7px;
            text-decoration: none;
            color: white;
            font-size: 15px;
            align-content: center;
            border: 2px solid orangered;
            background-color: orangered;
            border-radius: 20px;
            outline:none;
        }
        .left{
            margin-top: 20px;
            margin-left: 30px;
            /*border: 1px solid black;*/
            width: 64%;
            height: 1050px;
            float: left;
        }
        .header{
            width: 100%;
            height: 30px;
            border: 1px solid gainsboro;
            background-color: gainsboro;
        }
        .content{
            width: 100%;
            height: 1040px;
           /* border: 1px solid gold;
            background-color: gold;*/
        }
        .commodity{
            margin-left: 250px;
            margin-right: 300px;
            width: 60%;
            height: 100%;
        }
        .bottom{
            /*border: 1px solid red;*/
            width: 100%;
            height: 8%;
            margin-top: 10px;
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
        .initLeft{
            /*border: 1px solid black;*/
            width: 33%;
            height: 170px;
            float: left;
        }
        .img{
            width: 100%;
            height: 100%;
        }
        .initRight{
            /**border:1px solid  black;*/
            width: 25%;
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
        .initCenter{
            height: 170px;
            width: 40%;
            float: left;
        }
        #count{
            font-size: 20px;
            margin-left: 30px;
        }

    </style>
    <script>
        window.onload = function () {
            var id = ${param.id};
            var inf = document.getElementById("right").value;
            load(id,1);
            document.getElementById("find").innerHTML= '<a id="search" href="javascript:load(0,1)">搜索</a>';
            }


        function checkEmpty() {
            var inf = document.getElementById("right");
            var flag = true;
            if(inf==""){
                flag = false;
                alert("搜索信息不能为空");
            }
            return flag;
        }
        function changeColor(obj) {
            obj.style.backgroundColor='yellow';
            obj.style.border = 'none';
            obj.style.color = 'black';
        }

        function load(id,currentPage){
            var inf = document.getElementById("right").value;
            if(inf=="" && id==0){
                alert("搜索信息不能为空");
                return;
            }
            var xhttp;
            if (window.XMLHttpRequest) {
                xhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xhttp.open("GET", '/searchServlet?id='+id+'&pageSize=5&currentPage='+currentPage+'&inf='+inf+'', true);
            xhttp.send();
            var lists;
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    alert(xhttp.responseText);
                    if(xhttp.responseText == "搜索不到商品"){
                        alert(xhttp.responseText);
                        return;
                    }
                    var data = JSON.parse(xhttp.responseText);
                    var pageSize = data.pageSize;
                    var totalPage = data.totalPage;
                    var totalCount = data.totalCount;
                    var lists = data.list;
                    var lis = "";
                    var href = "";
                    var flag = data.login;
                    for(var i = 0;i < lists.length;i++){
                        var src = "../img/"+ lists[i].img;
                        if(flag){
                            href = "./detail.jsp?id=" + lists[i].id;
                        }else{
                            href = "../login/login.jsp";
                        }
                        var li ='<li style="margin-top: 10px;height: 173px;border: 1px solid gainsboro;list-style-type: none">'+
                            '<div class="initLeft"><img class="img" src='+src+'></div>'+
                            '<div class="initCenter"><p class="title">'+lists[i].title+'</p><p class="introduce">'+lists[i].introduce+'</p></div>'+
                            '<div class="initRight"><span class="price">￥'+lists[i].price+'起</span>' +
                            '<span class="detali"><a class="detali_a" href="'+href+'">查看详情</a></span></div>'+
                            '</li>';
                        lis += li;
                    }
                    var before = currentPage - 1;
                    if(before <= 0){
                        before = 1;
                    }
                    var bottomlis = '<li class="lowLi" name="cut" onclick="javascript:load('+id+','+before+')"><a href="javascript:load('+id+','+before+')" class="lowa" onclick="changeColor(this)">'+'上一页'+'</a></li>';
                    for(var j = 1;j <= totalPage;j++){
                        var bottomLi = '<li class="lowLi" name="cut" onclick="javascript:load('+id+','+j+')"><a href=javascript:load('+id+','+j+') class="lowa" onclick="changeColor(this)">'+j+'</a></li>';
                        bottomlis += bottomLi;
                    }
                    var after = currentPage + 1;
                    if(after >= totalPage){
                        after = totalPage;
                    }
                    bottomlis += '<li class="lowLi" name="cut" onclick="javascript:load('+id+','+after+')"><a href="javascript:load('+id+','+after+')" class="lowa" onclick="changeColor(this)">'+'下一页'+'</a></li>';
                    document.getElementById("get").innerHTML = lis;
                    document.getElementById("low").innerHTML = bottomlis;
                    document.getElementById("count").innerHTML ='共'+totalCount+'条数据,当前位于第'+currentPage+'页';
                }
                window.scroll(0,0);//回到顶点
            }
        }
    </script>
</head>
<body>
<div>
    <div style="background-color: gainsboro">
        <a href="view/login/login.jsp" style="color:red;margin-left: 50px;text-decoration: none" target="_blank">亲，请登录</a>
        <a href="view/register/registerOne.jsp" style="color:grey;text-decoration: none" target="_blank">免费注册</a>
        <a href="#" style="color:red;margin-left: 100px;text-decoration: none">淘宝网首页</a>
    </div>
    <!--<form action="#" method="post" id="form">-->
        <table style="margin: 25px;border-collapse: separate;border-spacing: 50px 0px;">
            <tr>
                <td width="100px"><a href="#"><img src="/view/img/1.png"></a></td>
                <td>
                    <input id="right" type="text" placeholder="请输入商品名称">
                    <span id="find"></span>
                </td>

            </tr>
        </table>
    <!--</form>-->
    <div>
        <div class="left">
            <div class="header">
                <span class="commodity">商品信息</span>
                <span>价格</span>
            </div>
            <div class="content">
            <ul style="padding: 0px" id="get">
            </ul>
                <div class="bottom">
                    <span id="count"></span>
                    <ul id="low">
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
