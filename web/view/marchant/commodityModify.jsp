<%--
  Created by IntelliJ IDEA.
  User: 111
  Date: 2020/5/9
  Time: 18:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品修改</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <style>
        .control{
            text-decoration: none;
            margin-left: 30px;
            color: white;
            font-size: 15px;
        }
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
        .save {
            color: white;
            font-size: 20px;
            border: 1px solid orangered;
            background-color: orangered;
            border-radius: 50px;
            width: 100px;
            height: 40px;
            margin-left: 10px;
            outline: none;
        }
        img{
            width: 100%;
            height: 100%;
        }
    </style>
    <script>
        function load(id,currentPhoto) {
            var xhttp;
            if (window.XMLHttpRequest) {
                xhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xhttp.open("get", '/merchantDetailServlet?id='+id, true);
            xhttp.send();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    var data = JSON.parse(xhttp.responseText);
                    document.getElementById("bigPitcher").innerHTML = '<img id="main" src="../img/'+data.img+'" style="width: 100%;height: 100%">';
                    document.getElementById("intro").innerHTML = data.introduce;
                    document.getElementById("title").innerHTML = data.title;
                    document.getElementById("price").value = data.price;
                    document.getElementById("detail").innerHTML = '<div style="width: 100%;height: 33%;font-size: 20px;word-wrap:break-word;">店家：'+data.sellerName+'</div>\n' +
                        '<div style="width: 100%;height: 33%;font-size: 20px;word-wrap:break-word;">地址：'+data.sellerAddr+'</div>\n' +
                        '<div style="width: 100%;height: 33%;font-size: 20px;word-wrap:break-word;">联系电话：'+data.sellerPhone+'</div>';

                    var pitcher = data.picture;
                    var before = currentPhoto-1;
                    if(before <= 0){
                        before = 0;
                    }
                    var li = '<li class="turn" onclick="load('+id+','+before+')"><a><img src="../img/previous.png" style="width: 100%;height: 100%"></a></li>';
                    for(var i = 0;i < pitcher.length;i++){

                        li += '<li class="big" onclick="load('+id+','+i+')"><a><img src="../img/'+pitcher[i].href+'" style="width: 100%;height: 100%"></a></li>';
                    }
                    var after = currentPhoto + 1;
                    if(after >= pitcher.length){
                        after = pitcher.length;
                    }
                    li += '<li class="turn" onclick="load('+id+','+after+')"><a><img src="../img/next.png" style="width: 100%;height: 100%"></a></li>';
                    document.getElementById("showpitcher").innerHTML = li;
                }
            }
        }
        function checkLen(){
            var r = document.getElementById("allFile");
            var flag = true;
            if(r.files.length > 4){
                alert("最多只能上传4张图片");
                flag = false;
            }
            return flag;
        }
        function showPitcher() {
            var allImgs = document.getElementById("allFile");
            var big = document.getElementById("bigPitcher");
            var small = document.getElementById("showpitcher");
            if(allImgs.files.length <= 0){
                small.innerHTML = ' <li class="turn"><a><img src="../img/previous.png"></a></li>' +
                    '                <li class="turn"><a><img src="../img/next.png"></a></li>'
                return;
            }
            var urlFirst = window.URL.createObjectURL(allImgs.files[0]);
            big.innerHTML = '<img src="'+urlFirst+'">';

            var allLis = ' <li class="turn"><a><img src="../img/previous.png"></a></li>';
            var url = '';
            for(var i = 0;i < allImgs.files.length;i++){
                url = window.URL.createObjectURL(allImgs.files[i]);
                allLis += '<li class="big"><a><img src="'+url+'"></a></li>';
            }
            allLis += '<li class="turn"><a><img src="../img/next.png"></a></li>';
            small.innerHTML = allLis;
        }
        window.onload = function () {
            document.getElementById("quit").onclick = function () {
                var flag = confirm("是否确认退出");
                if(flag){
                    location.href = "/exitServlet";
                }
            }
            var id = ${param.id};
            var category = ${param.category};
            alert(category);
            load(id,1);
            document.getElementById("form").onsubmit = function () {
                if(checkLen()){
                    var xhttp;
                    if (window.XMLHttpRequest) {
                        xhttp = new XMLHttpRequest();
                    } else {
                        // code for IE6, IE5
                        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }

                    xhttp.open("post", "/modifyCommodityServlet", true);
                    var id = ${param.id};
                    var title = document.getElementById("title").value;
                    var intro = document.getElementById("intro").value;
                    var price = document.getElementById("price").value;
                    var data = new FormData();
                    data.append("title",title);
                    data.append("intro",intro);
                    data.append("price",price);
                    data.append("id",id);
                    data.append("category",category);
                    var imgs = document.getElementById("allFile");
                    for(var i = 0;i < imgs.files.length;i++){
                        var imgName = imgs.files[i].name;
                        data.append(imgName,imgs.files[i]);
                    }
                    xhttp.send(data);
                    xhttp.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            alert(xhttp.responseText);
                            load(id,1);
                    }
                }
            }
                return false;
        }
        }
    </script>
</head>
</head>
<body>
<div id="mostTop">

    <span>欢迎回来！</span>
    <span>${sessionScope.one.nameTwo}!</span>
    <a style="width: 30px;height: 30px;margin-left: 20px"><img style="width: 30px;height: 30px" src="../headPitcher/${sessionScope.one.headPitcher}"></a>
    <a href="#" style="color: dodgerblue;margin-left: 20px" id="quit">退出</a>

</div>
<div id="top">
    <a href="#" id="my">我的淘宝</a>
    <a href="marchantControl.jsp" class="control">管理上架商品</a>

    <a href="addCommodity.jsp" class="control">添加商品</a>
    <a href="marchantImformation.jsp" class="control">账户设置</a>
</div>
<div style=" width: 91%;height: 550px;border: 1px solid gainsboro;margin-left: 60px;margin-top: 30px;border: 1px solid gainsboro">
    <div style="width: 44%;height: 470px;margin:20px;float: left;">
        <div style="width: 100%;height: 350px;" id="bigPitcher">

        </div>
        <div style="width: 100%;height: 100px;margin-top:20px;">
            <ul id="showpitcher">
            </ul>
        </div>
    </div>

    <form id="form" method="post" enctype="multipart/form-data" target="hidden">
        <iframe name="hidden" style="display:none;"></iframe>
    <div style="width: 50%;height: 470px;float: left;margin-left: 1px;margin-top: 20px;">
        <span style="font-size: 20px">标题</span>
        <div style="width: 100%;height: 100px;margin-top: 10px;border: 1px solid gray;border-radius: 10px" >
            <textarea name="title" id="title" style="border: none;outline:none;resize: none;color: darkgrey; width: 98%;height:97%;margin-top: 2px;margin-left:5px;font-size: 25px;overflow: hidden"></textarea>
        </div>
        <span style="font-size: 20px">简介</span>
        <div style="width: 100%;height: 100px;margin-top: 10px;border: 1px solid gray;border-radius: 10px">
            <textarea name="intro" id="intro" style="border: none;outline:none;resize: none;color: darkgrey; width: 98%;height:97%;margin-top: 2px;margin-left:5px;font-size: 25px;overflow: hidden"></textarea>
        </div>
        <div style="width: 100%;height: 130px;margin-top: 20px;" id="detail">
        </div>
        <div style="width: 100%;height: 50px;margin-top: 10px;">
            <div style="width: 100%;height: 50%;word-wrap:break-word;font-size: 20px">价格：
                <input name="price" id="price" style="color: red;font-size: 20px;width: 100px;border-radius: 5px;border: 1px solid black;outline: none;padding: 3px">
                <span style="font-size: 20px;color: red">￥</span>
            </div>
        </div>
        <div style="width: 100%;height: 50%;">
            <input onchange="showPitcher()" id="allFile" name="allFile" type="file" accept="image/png" multiple="multiple" value="重新上传图片">
            <input type="submit" value="保存" class="save" >
        </div>
    </div>
    </form>
</div>
</div>

</div>
</body>
</html>
