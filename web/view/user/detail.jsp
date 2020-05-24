<%--
  Created by IntelliJ IDEA.
  User: 111
  Date: 2020/5/6
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>商品详情</title>
    <style>
        .big{
            height: 100%;
            width: 100px;
            display: inline-block;
            margin-top: 0px;
            border: 1px solid black;
        }
        .turn{
            border: 1px solid black;
            height: 100%;
            width: 30px;
            display: inline-block;
            margin-top: 0px;
        }
        ul{
            padding: 0px;
            margin-top: 0px;
        }
        .small{
            height: 100%;
            width: 50px;
            border: 1px solid orangered;
        }
        .control {
            color: white;
            font-size: 20px;
            border: 1px solid orangered;
            background-color: orangered;
            border-radius: 50px;
            width: 100px;
            height: 40px;
            margin-left: 30px;
            outline: none;
        }
        .cantControl{
            color: white;
            font-size: 20px;
            border: 1px solid gray;
            background-color: gray;
            border-radius: 50px;
            width: 100px;
            height: 40px;
            margin-left: 30px;
            outline: none;
        }
    </style>
    <script>
        window.onload = function () {
            var id = "${param.id}";
            document.getElementById("form").onsubmit = function(){
                var id = "${param.id}";
                alert(checkComment());
                if(checkComment()){
                    giveCommoment(id);
                }
            }
            load(id,0);
        }
        function load(id,currentPhoto) {
            var xhttp;
            if (window.XMLHttpRequest) {
                xhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xhttp.open("GET", '/detailServlet?id='+id+'&pageSize=5', true);
            xhttp.send();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    var data = JSON.parse(xhttp.responseText);
                    var src = "../img/";
                    document.getElementById("bigPitcher").innerHTML = '<img id="main" src="'+src+data.img+'" style="width: 100%;height: 100%">'
                    document.getElementById("intro").innerHTML = '<span style="color: darkgrey; width: 100%;height: 100%;font-size: 25px;word-wrap:break-word;">'+data.introduce+'</span>'
                    document.getElementById("detail").innerHTML = '<div style="width: 100%;height: 30%;font-size: 20px;word-wrap:break-word;">店家：'+data.sellerName+'</div>\n' +
                        '        <div style="width: 100%;height: 30%;font-size: 20px;word-wrap:break-word;">地址：'+data.sellerAddr+'</div>\n' +
                        '        <div style="width: 100%;height: 30%;font-size: 20px;word-wrap:break-word;">联系电话：'+data.sellerPhone+'</div>';
                    document.getElementById("price").innerHTML = '<div style="width: 100%;height: 50%;word-wrap:break-word;font-size: 20px">价格：<span id="giveMoney" style="color: red;font-size: 20px">'+data.price+'</span>￥</div>';
                    var pitcher = data.picture;
                    var before = currentPhoto-1;
                    if(before <= 0){
                        before = 0;
                    }
                    var li = '<li class="turn" onclick="load('+id+','+before+')"><a><img src="../img/previous.png" style="width: 100%;height: 100%"></a></li>';
                    for(var i = 0;i < pitcher.length;i++){
                        li += '<li class="big" onclick="load('+id+','+i+')"><a><img src="'+src+pitcher[i].href+'" style="width: 100%;height: 100%"></a></li>';
                    }
                    var after = currentPhoto + 1;
                    if(after >= pitcher.length){
                        after = pitcher.length;
                    }
                    li += '<li class="turn" onclick="load('+id+','+after+')"><a><img src="../img/next.png" style="width: 100%;height: 100%"></a></li>';
                    document.getElementById("showpitcher").innerHTML = li;
                    document.getElementById("main").src = "../img/" + pitcher[currentPhoto].href;
                    var bottom  = document.getElementById("bottom");
                    if(data.favourite){
                        bottom.innerHTML = '<input type="submit" value="已经收藏" class="cantControl">' +
                            '<input onclick="payFor()" type="submit" value="立即支付" class="control">';
                    }else{
                        bottom.innerHTML = '<input onclick="setFavourite()" type="submit" value="点击收藏" class="control">' +
                            '<input onclick="payFor()" type="submit" value="立即支付" class="control">';
                    }
                    var showComment = document.getElementById("allComment");
                    var commentLi = "";
                    var comments  = data.comments;
                    if(data.totalCount > 0){
                        for(var i = 0;i < comments.length;i++){
                            commentLi += '<li style="margin-top: 10px;width: 100%;height: 120px;border: 1px solid gainsboro;list-style-type: none">' +
                                '<div style="height: 100%;width: 70%;border: 1px solid gainsboro;float: left">' +
                                '<div style="font-size: 18px;margin: 5px;width: 98%;height: 70%;">'+comments[i].content+'</div>' +
                                ' <span style="margin:5px;font-size: 20px">'+comments[i].time+'</span>' +
                                '</div><div style="height: 100%;width: 28%;float: left">' +
                                '<div style="widtih: 20px;height: 70px;border: 1px solid black;border-radius: 500%;margin-top: 5px;margin-left: 70px">' +
                                '<img style="width: 100%;height: 100%;border-radius: 500%" src="../headPitcher/'+comments[i].userHeadPitcher+'"></div>' +
                                '<div style="margin-left: 30px;margin-top: 10px"><span>用户：'+comments[i].userNameTwo+'</span></div></div></li>';
                        }
                    }
                    else{
                        commentLi += '<li style="margin-top: 10px;height: 40px;list-style-type: none"><div style="font-size: 25px;margin-top: 5px;">还没有任何评论</div></li>'
                    }
                    showComment.innerHTML = commentLi;
                }
            }
        }
        function giveCommoment(routeId) {
            var content = document.getElementById("content").value;
            var xhttp;
            if (window.XMLHttpRequest) {
                xhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xhttp.open("GET", '/giveCommentServlet?routeId='+routeId+'&content='+content+'', true);
            xhttp.send();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    alert(xhttp.responseText);
                    location.href = './detail.jsp?id='+routeId+'';
                }
            }

        }
        function setFavourite() {
            var id = ${param.id};
            var xhttp;
            if (window.XMLHttpRequest) {
                xhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xhttp.open("GET", '/addFavouriteServlet?id='+id, true);
            xhttp.send();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    alert(xhttp.responseText);
                    location.href = './detail.jsp?id='+id+'';
                }
            }
        }
        function checkComment() {
            var content = document.getElementById("content").value;
            var flag = true;
            if(content == ""){
                alert("评论内容不能为空");
                flag = false;
            }
            return flag;
        }
        function payFor() {
            var id = ${param.id};
            var price = document.getElementById("giveMoney").innerHTML;
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
                    location.href = './detail.jsp?id='+id+'';

                }
            }
        }
    </script>
</head>
<body>
<div style="background-color: gainsboro">
    <a href="#" style="color:red;margin-left: 50px;text-decoration: none">亲，请登录</a>
    <a href="#" style="color:grey;text-decoration: none">免费注册</a>
    <a href="#" style="color:red;margin-left: 100px;text-decoration: none">淘宝网首页</a>
</div>
<div>
   <a href="#"><img src="/view/img/1.png"></a>
</div>
<div style="width: 91%;height: 500px;border: 1px solid gainsboro;margin-left: 60px;margin-top: 30px">
    <div style="width: 44%;height: 470px;margin:20px;float: left;">
        <div style="width: 100%;height: 350px;" id="bigPitcher">

        </div>
        <div style="width: 100%;height: 100px;margin-top:20px;">
            <ul id="showpitcher">
            </ul>
        </div>
    </div>
    <div style="width: 50%;height: 470px;float: left;margin-left: 1px;margin-top: 20px">
        <div style="width: 100%;height: 100px;" id="intro">

        </div>
        <div style="width: 100%;height: 210px;margin-top: 20px" id="detail">

        </div>
        <div style="width: 100%;height: 70px;margin-top: 20px" id="price">
        </div>
            <div style="width: 100%;height: 50%;" id="bottom">
            </div>
        </div>
    </div>
</div>
<div style="width: 100%;height: 300px;padding-left: 0px">
<div style="float: left;width: 45%;height: 240px;border: 1px solid gainsboro;border-radius: 10px;margin-left: -540px;margin-top: 30px;">
    <div style="margin: 10px;color: skyblue;width: 130px;height: 40px;font-size: 25px;">给点建议吧</div>
    <form id="form" target="exec_target">
        <iframe hidden id="exec_target" name="exec_target"></iframe>
    <div style="width: 500px;height: 100px;">
        <div style="float: left;margin-left: 10px;width: 75px;height: 100%;border: 1px solid gainsboro;background-color: gainsboro">
            <div style="margin-top: 35px;margin-left: 5px">评价商品</div>
        </div>
        <div style="float: left">
            <textarea id="content" style="resize:none;outline: none;padding: 5px;font-size:20px;width: 400px;height: 100%;border: 1px solid gainsboro"></textarea>
        </div>
    </div>
    <input type="submit" value="提交"  style="margin-top: 30px;margin-left: 150px;width: 80px;height: 30px;border: 1px solid red;background-color: red;color: white;border-radius: 5px;font-size: 15px">
    </form>
</div>
<div style="width: 400px;height: 240px;border: 1px solid gainsboro;border-radius: 10px;float: right;margin-top: 30px;margin-right: 100px">
    <div style="margin: 10px;color: skyblue;width: 130px;height: 40px;font-size: 25px;">我的状态</div>
    <div style="margin-left: 150px;width: 100px ;height: 100px;border: 1px solid black;border-radius: 500%;">
        <img src="../headPitcher/${sessionScope.two.headPitcher}" style="width: 100%;height: 100%;border-radius: 500%">
    </div>
    <div style="font-size: 20px;margin-left: 150px;margin-top: 10px">你好！${sessionScope.two.nameTwo}</div>
    <div style="font-size: 20px;margin-left: 150px;margin-top: 10px">余额：${sessionScope.two.money}</div>
</div>
</div>
<div style="width: 60%;border: 1px solid gainsboro;border-radius: 10px;margin-left: 60px;margin-top: 30px">
    <div style="margin-left: 10px;width: 200px;height: 45px;font-size: 30px;color: skyblue;margin-top: 5px">评论区</div>
    <div style="margin-left: 10px">
    <ul style="padding: 0px" id="allComment">
    </ul>
</div>
</div>
</body>
</html>
