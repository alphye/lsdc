<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>AGV调控</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/agvs/css/style.css" media="screen" type="text/css" />
<link rel="icon" href="${pageContext.request.contextPath}/agvs/imgs/ls.ico" type="image/x-icon"/>

<style>
html, body {
	height: 100%;
	margin: 0;
}

body {
	background: #28227d;
}

.content {
	width: 100%;
	height: 98%;
}
.west{
	width: 27%;
	height: 98%;
	float: left;
}
.vehicleInfo {
	width: 100%;
	height: 60%;
	margin-top: 5%;
	font-size: 1.15vw;
	letter-spacing: 2px;
}

.vehicleInfo {
	background: url("${pageContext.request.contextPath}/agvs/imgs/b1.png") no-repeat;
	background-size: 100% 100%;
	
}
.operationArea{
	width:100%;
	font-size:0.85vw;
}
.SVGmap{
	width: 72%;
	height: 100%;
	float: left;
	padding: 2px;
    background: #a76060;
	/**background: url(imgs/192.png);**/
    border: 2px solid #774259;
}
.lable {
	text-align: right;
	width:45%;
}
.cnt {
	text-align: left;
	width:55%;
}
iframe{
 background: #28227d;
 border:0;
}
.button {
	display: inline-block;
	outline: none;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
	font: 0.85vw/100% 'Microsoft yahei',Arial, Helvetica, sans-serif;
	padding: .5em 1.1em .55em;
	text-shadow: 0 1px 1px rgba(0,0,0,.3);
	-webkit-border-radius: .5em; 
	-moz-border-radius: .5em;
	border-radius: .5em;
	-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	-moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	box-shadow: 0 1px 2px rgba(0,0,0,.2);
}
.button:hover {
	text-decoration: none;
}
.button:active {
	position: relative;
	top: 1px;
}
.blue {
	color: #d9eef7;
	border: solid 1px #0076a3;
	background: #0095cd;
	background: -webkit-gradient(linear, left top, left bottom, from(#00adee), to(#0078a5));
	background: -moz-linear-gradient(top,  #00adee,  #0078a5);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#00adee', endColorstr='#0078a5');
}
.blue:hover {
	background: #007ead;
	background: -webkit-gradient(linear, left top, left bottom, from(#0095cc), to(#00678e));
	background: -moz-linear-gradient(top,  #0095cc,  #00678e);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#0095cc', endColorstr='#00678e');
}
.blue:active {
	color: #80bed6;
	background: -webkit-gradient(linear, left top, left bottom, from(#0078a5), to(#00adee));
	background: -moz-linear-gradient(top,  #0078a5,  #00adee);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#0078a5', endColorstr='#00adee');
}
	/* 向上箭头 */
 
        .to_top {
            width: 0;
            height: 0;
            border-bottom: 1.5vw solid #f0f;
            border-left: 1.5vw solid transparent;
            border-right: 1.5vw solid transparent;
			margin-bottom:5px;
        }
 
        /* 向左箭头 */
 
        .to_left {
            width: 0;
            height: 0;
            border-right: 1.5vw solid #ffd900;
            border-top: 1.5vw solid transparent;
            border-bottom: 1.5vw solid transparent;
			margin-right:8px;
        }
 
        /* 向右箭头 */
 
        .to_right {
            width: 0;
            height: 0;
            border-left: 1.5vw solid greenyellow;
            border-top: 1.5vw solid transparent;
            border-bottom: 1.5vw solid transparent;
			margin-left:8px;
        }
 
        /* 向下箭头 */
 
        .to_bottom {
            width: 0;
            height: 0;
            border-top: 1.5vw solid skyblue;
            border-left: 1.5vw solid transparent;
            border-right: 1.5vw solid transparent;
			margin-top:5px;
        }
		.btn:hover{
			cursor:pointer;
		}
</style>
<script src="${pageContext.request.contextPath}/agvs/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/agvs/js/Constants.json"></script>
<script src="${pageContext.request.contextPath}/agvs/js/view.js" ></script>
</head>
<body>
	<div style="width: 100%; height: 100%;" align="center">
		<div id="content" class="content" align="left">
			<div class="west">
				<div id="vehicleInfo" class="vehicleInfo" align="center">
					<input type="text" id="vehicleIp"/>
					<table style="position: relative;top: 20%;">
						<tr class='outlinedA'>
							<td class="lable">当前位置：</td>
							<td class="cnt" id="vehicle_position">0,0</td>
						</tr>
						
						<tr class='distant-top'>
							<td class="lable">导航状态：</td>
							<td class="cnt"  id="vehicle_naviState"></td>
						</tr>
						
						<tr class='distant-top'>
							<td class="lable">操作状态：</td>
							<td class="cnt"  id="vehicle_operationState"></td>
						</tr>
						
						<tr class="outlinedB">
							<td class="lable">横向速度：</td>
							<td class="cnt" ><span id="vehicle_vx">0</span> mm/s</td>
						</tr>

						<tr class="outlinedB">
							<td class="lable">纵向速度：</td>
							<td class="cnt" ><span id="vehicle_vy">0</span> mm/s</td>
						</tr>
						
						<tr class="hsl">
							<td class="lable">总里程数：</td>
							<td class="cnt" ><span id="vehicle_mileage">0</span> mm</td>
						</tr>
						
						<tr class="distant-front">
							<td class="lable">剩余电量：</td>
							<td class="cnt"><span id="vehicle_batteryResidues">100</span>%</td>
						</tr>
						<tr style="display:none">
							<td class="label" >
								<input id="p_x"/>
								<input id="p_y"/>
							</td>
							<td class="cnt" >
								<button onclick="uppstn();"> updateVehiclePosition</button>
							</td>
						</tr>
					
					</table>
					
				</div>
				<div class="operationArea" style="width:100%" align="center">
					<table style="width:95%;text-align:center;">
						<tr>
							<td><span class="erode">发送导航</span></td><td style="width:30%;">&nbsp;</td><td> <span class="erode">追加导航</span></td>
						</tr>
						
						<tr>
							<td>
								<table style="width:100%">
									<tr>
										<td></td>
										<td align="center" style="width:30%;">
											<div class="to_top btn" onclick=move(3)></div>
										</td>
										<td></td>
										
									</tr>
									
									<tr>
										<td align="right">
											<div class="to_left btn" onclick=move(2)></div>
										</td>
										<td><input id="moveCells" style="width: 2vw; background: #82abd0;text-align:center;" /></td>
										<td>
											<div class="to_right btn" onclick=move(1)></div>
											
										</td>
										
									</tr>
									
									<tr>
										<td></td>
										<td align="center">
											<div  class=" to_bottom btn" onclick=move(4)></div>
										</td>
										<td></td>
									</tr>
								</table>
							</td>
							<td><a onclick="clearError()" class="blue button">清除错误</a></td>
							<td>
								<table style="width:100%">
									<tr>
										<td></td>
										<td align="center" style="width:30%;">
											<div class="to_top btn" onclick=appendTrail(3)></div>
										</td>
										<td></td>
										
									</tr>
									
									<tr>
										<td align="right">
											<div class="to_left btn" onclick=appendTrail(2)></div>
										</td>
										<td><input id="appendCells" style="width: 2vw; background: #82abd0;text-align:center;" /></td>
										<td>
											<div class="to_right btn" onclick=appendTrail(1)></div>
											
										</td>
										
									</tr>
									
									<tr>
										<td></td>
										<td align="center">
											<div  class=" to_bottom btn" onclick=appendTrail(4)></div>
										</td>
										<td></td>
									</tr>
								</table>
							</td>
						</tr>
						
						<tr>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td><a onclick="modifyNaviTask('cancleNaviTask')" class="blue button">取消导航</a></td>
							<td><a onclick="modifyNaviTask('pauseNaviTask')" class="blue button">暂停导航</a></td>
							<td><a onclick="modifyNaviTask('recoverNaviTask')" class="blue button">恢复导航</a></td>
						</tr>
						<tr>
							<td><a onclick="beltRotationOn()" class="blue button">皮带转动</a></td>
							<td><a onclick="beltRotationStop()" class="blue button">停止转动</a></td>
							<td><a onclick="beltRotationOnAndStop()" class="blue button">转动后停止</a></td>
						</tr>
						<tr>
							<td><a onclick="modifyOperationTask('cancleOperationTask')" class="blue button">取消操作</a></td>
							<td><a onclick="modifyOperationTask('pauseOperationTask')" class="blue button">暂停操作</a></td>
							<td><a onclick="modifyOperationTask('recoverOperationTask')" class="blue button">恢复操作</a></td>
						</tr>
					</table>
					
					
				</div>
			</div>
			<div class="SVGmap">
				<iframe name="SVGFrame" width="100%" height="100%" src="${pageContext.request.contextPath}/agvs/envMap.jsp"></iframe>
			</div>
		</div>
	</div>
</body>
</html>