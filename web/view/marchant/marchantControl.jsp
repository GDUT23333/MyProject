<%--
  Created by IntelliJ IDEA.
  User: 111
  Date: 2020/4/28
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商家管理界面</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <style>
        #mostTop{
            width: 100%;
            height: 40px;
            font-size: 20px;
            margin-left: 10px;
        }
       #my {
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
        .initCenter{
            height: 170px;
            width: 40%;
            float: left;
        }
        .initLeft{
            /*border: 1px solid black;*/
            width: 34%;
            height: 170px;
            float: left;
        }
        img{
            width: 100%;
            height: 100%;
        }
        .initRight{
            /**border:1px solid  black;*/
            width: 25%;
            height: 170px;
            float: left;
        }
        ul{
            list-style-type: none;
            padding: 0px;
        }
        .show{
            list-style-type: none;
            height: 170px;
            border: 1px solid gainsboro;
            margin-top: 20px;
        }
        .low{
            list-style-type: none;
            display: inline-block;
            margin-left: 10px;
            font-size: 23px;

        }
        .change{
            color: slateblue;
            padding: 5px;
            border: 1px solid gainsboro;
            border-radius: 5px;
            text-decoration: none;
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
        .do{
            background-color: orangered;
            border-radius: 5px;
            color: white;
            margin-left: 15px;
            display: block;
            float: left;
            width: 100px;
            height: 30px;
            font-size: 20px;
            text-align: center;
            text-decoration: none;
            margin-top: 60px;
            border: 1px solid orangered;
        }
    </style>
    <script>
        window.onload = function () {
            document.getElementById("quit").onclick = function () {
                var flag = confirm("是否确认退出");
                if(flag){
                    location.href = "/exitServlet";
                }
            }
            var id = "${sessionScope.one.email}";
            load(id,1);
        }
        function load(id,currentPage) {
            var xhttp;
            if (window.XMLHttpRequest) {
                xhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xhttp.open("GET", '/merchantFindAllServlet?email='+id+'&currentPage='+currentPage+'&pageSize=5', true);
            xhttp.send();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    if(xhttp.responseText == "还未上架任何商品"){
                        alert(xhttp.responseText);
                        return;
                    }
                    var data = JSON.parse(xhttp.responseText);
                    var lists = data.list;
                    var routeShow = document.getElementById("allRoute");
                    var lowshow = document.getElementById("lowshow");
                    var lishow = "";
                    if(currentPage >= data.totalPage){
                        currentPage = data.totalPage;
                    }
                    alert(xhttp.responseText);
                    for(var i = 0; i < lists.length;i++){
                        var src = "../img/" + lists[i].img;
                        lishow +='<li class="show"><div class="initLeft"><img src="'+src+'"></div>\n' +
                            '<div class="initCenter"><p class="title">'+lists[i].title+'</p><p class="introduce">'+lists[i].introduce+'</p></div>\n' +
                            '<div class="initRight">' +
                            '<span style="margin-top: 20px"><a class="do" href="/view/marchant/commodityModify.jsp?id='+lists[i].id+'&category='+lists[i].category+'">查看修改</a></span>' +
                            '<span><a class="do" href="#" onclick="deleteCommodity('+lists[i].id+','+'\''+id+'\''+','+currentPage+')">删除</a></span></div></li>'
                    }
                    routeShow.innerHTML = lishow;
                    var before = currentPage - 1;
                    if(before <= 1){
                        before = 1;
                    }
                    var low = '<li class="low" onclick="load('+'\''+id+'\''+','+before+')"><a class="change" href="#">上一页</a></li>';
                    for (var i = 1;i <= data.totalPage;i++){
                        low += '<li class="low" onclick="load('+'\''+id+'\''+','+i+')"><a class="change" href="#">'+i+'</a></li>';
                    }
                    var after = currentPage + 1;
                    if(after >= lists.length){
                        after = lists.length;
                    }
                    low += '<li class="low" onclick="load('+'\''+id+'\''+','+after+')"><a class="change" href="#">下一页</a></li>';
                    lowshow.innerHTML = low;
                    document.getElementById("warn").innerHTML = '共'+data.totalCount+'条数据，当前位于第'+currentPage+'页';
                }
                window.scroll(0,0);//回到顶点
            }
        }
        function deleteCommodity(id,email,currentPage) {
            var flag = confirm("是否确认删除");
            if(flag){
                var xhttp;
                if (window.XMLHttpRequest) {
                    xhttp = new XMLHttpRequest();
                } else {
                    // code for IE6, IE5
                    xhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }
                xhttp.open("GET", '/deleteCommodityServlet?id='+id+'', true);
                xhttp.send();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                            if(xhttp.responseText == "删除成功"){
                                alert(xhttp.responseText);
                                load(email,currentPage);
                            }else{
                                alert(xhttp.responseText);
                            }
                    }
                }
            }
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
    <a href="marchantControl.jsp" class="control">管理上架商品</a>
    <a href="addCommodity.jsp" class="control">添加商品</a>
    <a href="marchantImformation.jsp" class="control">账户设置</a>
</div>
<div style="width: 80%;height: 1100px;border: 1px solid white;margin-left: 130px;margin-top: 30px">
    <div style="width: 100%;height: 40px;background-color: darkgrey">
        <span style="float:left;margin-top: 10px;margin-left: 280px">商品信息</span>
        <span style="float: left;margin-left: 500px;margin-top: 10px">操作</span>
    </div>
    <div>
        <ul id="allRoute">

        </ul>
    </div>
    <div>
        <span style="font-size: 20px;margin-left: 10px" id="warn"></span>
        <ul id="lowshow">

        </ul>
    </div>
</div>
</body>
</html>
