<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<style>
html, body {
	height: 100%;
	margin:0px;
}
line.edge{
	stroke:rgb(75, 85, 134);
	stroke-width:1 ;
}

line.track{
	stroke:rgb(187, 184, 176);
	stroke-width:3 ;
}

line:hover{
	cursor:pointer;
}
rect {
  transform-origin: 50% 50%;
}
rect.trackCell{
	stroke:rgb(152, 152, 93);
	stroke-width:2 ;
	fill:none;
}
text{fill:#cccbcb;font-size:0.6vw;}


.contextmenu { display: none; position: absolute;  margin: 0; padding: 5px; background: #7f7c96; border-radius: 3px; 
			   list-style: none; overflow: hidden; z-index: 999999;}
.contextmenu li { border-left: 3px solid transparent; transition: ease .2s; }
.contextmenu li a { display: block; padding: 5px;  text-decoration: none; transition: ease .2s; color:#632e00}
.contextmenu li:hover a { color: #9a00b5; }
</style>
<body >

 
	<ul class="contextmenu" id="contextmenu">
		<li><a href="#" onclick="onSendToPoint();">行驶到指定位置</a></li>
		<li><a href="#" onclick="onQueryNaviTrails()">查询导航轨迹</a></li>
		<li><a href="#" onclick="onChangeContainerDirection();">变更货柜朝向</a></li>
		<li><a href="#" onclick="onStartCharge();">开始充电</a></li>
		<li><a href="#" onclick="onStopCharge();">停止充电</a></li>
		<li><a href="#" onclick="onWritePackageSize();">发送包裹尺寸</a></li>
 	</ul>

 <div style="width:100%;height:99%; overflow:auto">
	<svg id="mainSVG" width="100%" height="99.5%" style="overflow: visible;" >   
	  <!--
	  <circle cx="35" cy="35" r="35" style="stroke: black; fill: orange;" onclick="resetVehicle();"/> 
	  <polygon id="vehicle" points="100,10   85,40   85,70   115,70    115,40" style="fill:lime;stroke:purple;stroke-width:5;fill-rule:evenodd; " />
	   <line x1="120" y1="150" x2="120" y2="250" style="stroke:rgb(100,55,69);stroke-width:3"></line>
	   <circle cx="35" cy="35" r="35" style="stroke: black; fill: none;"/>
	   <rect x="150" y="50" width="200" height="100" style="stroke: blue; fill: orange;"/>
	   <path d='M30 30 L20 35 M20 35 L30 40 M30 40 L30 30 M30 35 L150 35' style='fill:none; stroke:black; stroke-width: 2' />
	   -->
	   <circle id="p_lt" cx="0" cy="0" r="1" style="stroke: black; fill: red;"/> 
	</svg> 
 </div>

 
</body>
<script src="${pageContext.request.contextPath}/agvs/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/agvs/js/map.json"></script>
<script src="${pageContext.request.contextPath}/agvs/js/readXml.js"></script>
<script src="${pageContext.request.contextPath}/agvs/js/envMap.js"></script>
</html>