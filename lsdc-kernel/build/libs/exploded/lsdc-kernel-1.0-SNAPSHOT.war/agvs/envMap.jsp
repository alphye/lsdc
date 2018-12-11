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
.arrow{
    width: 6px;
    height: 6px;
    border-top: 1px solid #999;
    border-right: 1px solid #999;
    transform: rotate(45deg);
    position: absolute;
    right: 0px;
}

.contextmenu { display: none; position: absolute;  margin: 0; padding: 5px; background: #7f7c96; border-radius: 3px; 
			   list-style: none; overflow: hidden; z-index: 1000;border:1px solid #231010}
.contextmenu li { border-left: 3px solid transparent; transition: ease .2s; }
.contextmenu li a { display: block; padding: 5px 0px 5px 5px;  text-decoration: none; transition: ease .2s; color:#222222}
.contextmenu li:hover a { color: #9a00b5; }
.menue_item:after{content:'>';color:#000;opacity:.5;text-shadow:0 0 1px currentColor;
   /* border-top: 1px solid #222222;
    border-right: 1px solid #222222;
    transform: rotate(45deg); */
    position: absolute;right: 5px;}
</style>
<body >

 
	<ul class="contextmenu contextmenu1" id="contextmenu1">

        <li  id="selectNaviPosition"><a class="menue_item"  href="javascript:void(0);" onclick="donone();" onmousemove="selectNaviPoints(event,this);" >行驶到指定位置 </a></li>
		<li  ><a class="menue_item"  href="javascript:void(0);" onclick="donone();" onmousemove="selectPackageDirection(event,this);">投递包裹</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('040E');" onmousemove="hidenContextMenu2();">设置原点并返回原点 &nbsp;&nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('0701');" onmousemove="hidenContextMenu2();">清除X驱动告警</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('0700');" onmousemove="hidenContextMenu2();">清除Y驱动告警</a></li>
 	</ul>
    <ul class="contextmenu contextmenu2" id="contextmenu2">
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('1300');">0号位置 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('1301');">1号位置 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('1302');">2号位置 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('1303');">3号位置 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('1304');">4号位置 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('1305');">5号位置 &nbsp;</a></li>
    </ul>
    <ul class="contextmenu contextmenu3" id="contextmenu3">
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('3801');">投向车头方向 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('3802');">投向车尾方向 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('3803');">投向车体左侧 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('3804');">投向车体右侧 &nbsp;</a></li>
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