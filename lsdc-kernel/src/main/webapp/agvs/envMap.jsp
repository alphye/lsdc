<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/agvs/css/dialog.css" media="screen" type="text/css" />
</head>
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
rect.actualTrackCell{
    stroke:#06a70c;
    stroke-width:2 ;
    fill: none;
}
text{fill:#cccbcb;font-size:0.6vw;}
text.cellIndex{fill:#06a70c;font-size:0.8vw;}
.cellIndex:hover{cursor: default}
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
<div id="templateArea" style="display:none">
    <textarea id="contextmenu2Template">
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('13#param#');">#index#号位置 &nbsp;</a></li>
    </textarea>
</div>
 
	<ul class="contextmenu contextmenu1" id="contextmenu1">
        <li><a href="javascript:void(0)" onclick="createNaviTask();" onmousemove="hidenContextMenu2();">添加导航任务 &nbsp;&nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="cancleCyclicTask();" onmousemove="hidenContextMenu2();">取消循环任务 &nbsp;&nbsp; </a></li>
        <li  id="selectNaviPosition"><a class="menue_item"  href="javascript:void(0);" onclick="donone();" onmousemove="selectNaviPoints(event,this);" >行驶到指定位置 </a></li>
		<li  ><a class="menue_item"  href="javascript:void(0);" onclick="donone();" onmousemove="selectPackageDirection(event,this);">投递包裹</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('0400');" onmousemove="hidenContextMenu2();">设置原点并返回原点 &nbsp;&nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('0701');" onmousemove="hidenContextMenu2();">清除X驱动告警</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('0700');" onmousemove="hidenContextMenu2();">清除Y驱动告警</a></li>
 	</ul>
    <ul class="contextmenu contextmenu2" id="contextmenu2">
        <%--<li><a href="javascript:void(0)" onclick="sendSimProCmd('1300');">0号位置 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('1301');">1号位置 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('1302');">2号位置 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('1303');">3号位置 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('1304');">4号位置 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('1305');">5号位置 &nbsp;</a></li>--%>
    </ul>
    <ul class="contextmenu contextmenu3" id="contextmenu3">
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('3801');">Y轴正方向 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('3802');">Y轴负方向 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('3804');">X轴正方向 &nbsp;</a></li>
        <li><a href="javascript:void(0)" onclick="sendSimProCmd('3803');">X轴负方向 &nbsp;</a></li>
    </ul>
 <div style="width:100%;height:99%; overflow:auto">
	<svg id="mainSVG" width="100%" height="99.5%" style="overflow: visible;" >   
        <g id="g1">
            <circle id="p_lt" cx="0" cy="0" r="1" style="stroke: black; fill: red;"/>
        </g>
        <g id="g2"></g>
	</svg> 
 </div>
<div id="createNaviTaskBox">
    <div class="row1">
        新建导航任务<a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn" onclick="cancleDialog()">×</a>
    </div>
    <table  style="display:none">
        <tbody id="naviTaskTemplate">
            <tr>
                <td class="lable">
                    <div>目的地：</div>
                </td>
                <td class="cnt">
                    <select name="tasks[#index#].destPoint">
                        #options#
                    </select>
                </td>

                <td class="lable">
                    <div>操作：</div>
                </td>
                <td class="cnt">
                    <select name="tasks[#index#].destOpt">
                        <option value="-1">无</option>
                        <option value="3805">装货</option>
                        <option value="3801">Y轴正方向投递包裹</option>
                        <option value="3802">Y轴负方向投递包裹</option>
                        <option value="3804">X轴负方向投递包裹</option>
                        <option value="3803">X轴负方向投递包裹</option>
                        <option value="3701">充电</option>

                    </select>
                </td>
                <td style="display:#plussignShow#">
                    <div class="plussign" onclick="addNaviTask();"></div>
                    <input type="hidden" name="tasks[#index#].vehicleIp" value="#vehicleIp#" />
                </td>
            </tr>
        </tbody>
    </table>
    <form name="dialogForm" style="height:80%;overflow:auto;">
        <input id="nextNaviTaskIndex" value="0" type="hidden" />
        <table class="diagContentTable">
            <tr>
                <td colspan="2" class="lable">
                    是否循环执行任务:
                </td>
                <td class="cnt">
                    <input id="cyclicExecute" type="checkbox" />
                </td>
                <td colspan="3"></td>
            </tr>
            <tbody id="naviTaskDataArea" >

            </tbody>
        </table>
    </form>
    <div class="row">
        <a href="#" id="submitDialog" class="dialogButton" onclick="submitDialog();">确定</a>
        <a href="#" id="cancleDialog" class="dialogButton" onclick="cancleDialog();">取消</a>
    </div>

</div>

 
</body>
<script src="${pageContext.request.contextPath}/agvs/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/agvs/js/map.json"></script>
<script src="${pageContext.request.contextPath}/agvs/js/actualTrack.json"></script>
<script src="${pageContext.request.contextPath}/agvs/js/readXml.js"></script>
<script src="${pageContext.request.contextPath}/agvs/js/envMap.js"></script>
</html>