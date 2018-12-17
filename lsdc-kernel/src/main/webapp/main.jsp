<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>车辆调控</title>
    <link rel="icon" href="${pageContext.request.contextPath}/agvs/imgs/ls.ico" type="image/x-icon"/>
    <script src="${pageContext.request.contextPath}/agvs/js/Constants.json"></script>
    <script src="${pageContext.request.contextPath}/static_res/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/static_res/sockjs.js"></script>
    <script src="${pageContext.request.contextPath}/static_res/stomp.js"></script>
</head>

<style>
    html, body {
        height: 100%;
        margin: 0;
    }

</style>
<body>
<div id="navi"  style="display:none">
    <span style="display: none;">${accountId}</span>
</div>


<div id="content" style="width:99.5%;height:99.5%;">
    <iframe name="viewFrame" width="100%" height="100%" src="${pageContext.request.contextPath}/agvs/view.jsp"></iframe>
</div>
</body>

<script type="text/javascript">
    var stomp,sock;
    var stompConnected=false;
    $(window).load(function () {
        if (window.WebSocket){
            websocketConfig();
        } else {
            alert("错误","浏览器不支持websocket技术通讯.");
        }
    });

    //websocket配置
    function websocketConfig() {
        //对应后台WebSoccketConfig的配置
        sock = new SockJS("${pageContext.request.contextPath}/contactSocket");
        sockHandle();
        stompConfig().then(function(){
            mainInit();
        });
    }

    function mainInit() {
        bindSubscripe();
    }

    function stompConfig() {
        //使用STOMP子协议的websocket客户端
        var _promise = new Promise(function(resolve, reject){
            stomp = Stomp.over(sock);
            stomp.connect({}, function() {
                console.log("stomp connected");
                stompConnected = true;
                resolve("");
            });
        });
        return _promise;
    }
    function bindSubscripe() {
        console.log("stomp subscripe");
        viewFrame.subscripeTopic();
    }


    function retrievalVehicleInfo(){
        var command={"commandType":"retrievalVehicleInfo","message":{"vehicleIp":"127.0.0.1"}};
        sendMessage("/wbskt/retrievalVehicleInfo",JSON.stringify(command))
    }

    //发送消息
    function sendMessage(address,message) {
        //发送
        stomp.send(address,{},message);
    }



    function sockHandle() {
        sock.onopen = function () {
            console.log("------连接成功------");
        };
        sock.onmessage = function (event) {
            console.log('-------Received: ' + event.data);
        };
        sock.onclose = function (event) {
            console.log('--------Info: connection closed.------');
        };
        //连接发生错误
        sock.onerror = function () {
            alert("连接错误", "网络超时或通讯地址错误.");
            disconnect();
        } ;
    }



    //关闭websocket
    function disconnect() {
        if (sock != null) {
            sock.close();
            sock = null;
        }
    }

    function vehicleOnline(){
        sendMessage(constantsJSON.stopicAddressConstants.vehicleOnline,"");
    }
</script>
</html>
