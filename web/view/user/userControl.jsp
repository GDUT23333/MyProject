<%--
  Created by IntelliJ IDEA.
  User: 111
  Date: 2020/5/16
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>用户管理</title>
    <style>
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
        #my {
            text-decoration: none;
            font-size: 25px;
            color: white;
            letter-spacing: 5px;
        }
        .lowLi{
            display: inline-block;
            list-style-type: none;

        }
        .lowA{
            font-size: 25px;
            border: 1px solid blue;
            border-radius: 5px;
            margin-left: 10px;
            padding: 5px;
        }
        .do{
            display: block;
            padding-left: 20px;
            padding-right: 20px;
            padding-top: 5px;
            padding-bottom: 5px;
            background-color: orangered;
            font-size: 18px;
            color: white;
            border: 1px solid orangered;
            border-radius: 5px;
            text-align: center;
            margin-top: 10px;
            margin-left: 4px;
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
        img{
            width: 100%;
            height: 100%;
        }
    </style>
    <script>
        function load(currentPage,pageSize) {
            var xhttp;
            if (window.XMLHttpRequest) {
                xhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xhttp.open("GET", '/checkMyFavouriteServlet?pageSize='+pageSize+'&currentPage='+currentPage+'', true);
            xhttp.send();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    var data = JSON.parse(xhttp.responseText);
                    var lists = data.list;
                    var lis = "";
                    if(lists.length == 0){
                        load(currentPage-1,pageSize);
                        return;
                    }
                    for(var i = 0;i < lists.length;i++){
                        lis += '<li style="list-style-type: none;margin-top: 13px;height: 173px"><div style="width: 100%;height: 100%;border: 1px solid gainsboro;">' +
                            '<div style="height: 100%;width: 30%;float: left"><img src="../img/'+lists[i].img+'"></div>' +
                            '<div style="height: 100%;width: 40%;float: left">' +
                            '<p class="title">'+lists[i].title+'</p><p class="introduce">'+lists[i].introduce+'</p>'+
                            '</div>' +
                            '<div style="height: 100%;width: 19%;float: left;">' +
                            '<div style="margin-top: 50px">' +
                            '<span style="font-size: 30px;color: red;margin-left: 30px" id="'+i+'">'+lists[i].price+'</span>' +
                            '<span style="font-size: 30px;color: red;">￥</span>' +
                            '</div>' +
                            '</div>' +
                            '<div style="height: 100%;width: 10%;float: left">' +
                            '<input id="cancel" class="do" type="button" value="删除" onclick="cancel('+lists[i].id+','+currentPage+','+pageSize+')"></input>' +
                            '<input onclick="payFor('+i+')" class="do" type="button" value="结算">' +
                            '<a class="do" target="_blank" type="button" href="./detail.jsp?id='+lists[i].id+'">查看</a>' +
                            '</div>' +
                            '</div>' +
                            '</li>';
                    }
                    var before = currentPage - 1;
                    if(before <= 0){
                        before = 1;
                    }
                    var after = currentPage + 1;
                    if(after >= data.totalPage){
                        after = data.totalPage;
                    }
                    var lowLi = '<li class="lowLi" onclick="load('+before+',5)"><a class="lowA">上一页</a></li>';
                    for(var i = 1; i <= data.totalPage;i++){
                        lowLi += '<li class="lowLi" onclick="load('+i+',5)"><a class="lowA">'+i+'</a></li>';
                    }
                    lowLi += '<li class="lowLi" onclick="load('+after+',5)"><a class="lowA">下一页</a></li>';
                    document.getElementById("get").innerHTML = lis;
                    document.getElementById("lowUl").innerHTML = lowLi;
                    document.getElementById("count").innerHTML = '共'+data.totalCount+'条数据，现在位于第'+data.currentPage+'页';
                }
            }
        }
        function detail(id){
            location.href = './detail.jsp?id='+id+'';
        }
        function cancel(id,currentPage,pageSize) {
            var flag = confirm("是否确认删除");
            if(flag){
                var xhttp;
                if (window.XMLHttpRequest) {
                    xhttp = new XMLHttpRequest();
                } else {
                    // code for IE6, IE5
                    xhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }
                xhttp.open("GET", '/cancelFavouriteServlet?routeId='+id+'', true);
                xhttp.send();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        alert(xhttp.responseText);
                        load(currentPage,pageSize);
                    }
                }
            }
        }
        window.onload = function () {
            document.getElementById("quit").onclick = function () {
                var flag = confirm("是否确认退出");
                if(flag){
                    location.href = "/exitServlet";
                }
            }
            load(1,5);
        }
        function payFor(i) {
            var price = document.getElementById(i).innerHTML;
            var xhttp;
            if (window.XMLHttpRequest) {
                xhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xhttp.open("GET", '/payForServlet?price='+price+'', true);
            xhttp.send();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    alert(xhttp.responseText);
                    if(xhttp.responseText == "余额不足"){
                        return;
                    }
                   location.href = "./userControl.jsp";

                }
            }
        }
    </script>
</head>
<body>
<div id="mostTop">
    <span style="font-size: 20px">欢迎回来！</span>
    <span style="font-size: 20px">${sessionScope.two.nameTwo}</span>
    <a href="#" style="color: dodgerblue" id="quit">退出</a>
    <span style="font-size: 20px;margin-left: 50px">余额:${sessionScope.two.money}</span>
</div>
<div id="top">
    <a href="#" id="my">我的淘宝</a>
    <a href="./userControl.jsp" class="control">购物车</a>
    <a href="./userImformation.jsp" class="control">账户设置</a>
</div>
<div style="width:15%;height: 100px;float: left;margin-top: 20px;font-size: 36px;color: deepskyblue">
    我的购物车
</div>
<div style="width: 70%;margin-top: 20px;float: left">
    <div style="width: 100%;height: 24px;background-color: gainsboro">
        <span style="font-size: 18px;color: white;margin-left: 250px">商品信息</span>
        <span style="font-size: 18px;color: white;margin-left: 480px">操作</span>
    </div>
    <div style="width: 100%;">
        <ul style="padding: 0px" id="get">
        </ul>
    </div>
    <div style="width: 100%;">
        <span style="font-size: 20px;" id="count"></span>
        <div>
            <ul style="padding: 0px" id="lowUl">
            </ul>
        </div>
    </div>
</div>
</body>
</html>
