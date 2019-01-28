	var realMapWidth=12000;
	var realMapHeight=12000;
	var x_lineNum=21;// 横线数量
	var y_lineNum=21;// 竖线数量
	var screenWidth = document.documentElement.clientWidth || document.body.clientWidth;
	var screenHeight = document.documentElement.clientHeight || document.body.clientHeight;
    
	var leftOffset=screenWidth*0.1;
	var topOffset=screenHeight*0.1;
	
	var mapHT=screenHeight*0.85;// 地图高
	var mapWD=mapHT*(realMapWidth/realMapHeight);// 地图宽
	
	var real_virtual_rate=mapWD/realMapWidth; // 真实地图与界面展示的地图的比例
		
	var p_left_top =	 {"X":leftOffset,"Y":topOffset};// 地图左上角坐标
	var p_left_bottom =  {"X":leftOffset,"Y":topOffset+mapHT};// 地图左下角坐标
	var p_right_top =	 {"X":leftOffset+mapWD,"Y":topOffset};// 地图右上角坐标
	var p_right_bottom = {"X":leftOffset+mapWD,"Y":topOffset+mapHT};// 地图右下角坐标
	
	var gridWD=mapWD/(y_lineNum-1);// 每个格子的宽度
	var gridHT=mapHT/(x_lineNum-1);// 每个格子的高度

	$(function(){
		initialize();
		$("line").bind("click",onLineClick);
		$("rect").bind("contextmenu",onRectContextMenu);
		  $(document).click(function(){
			$(".contextmenu").hide();
		  });
	});
	function onRectContextMenu(e){
		var winWidth = $(document).width();
		var winHeight = $(document).height();
		var posX = e.pageX;
		var posY = e.pageY;
		var menuWidth = $(".contextmenu").width();
		var menuHeight = $(".contextmenu").height();
		var secMargin = 10;
		if(posX + menuWidth + secMargin >= winWidth
		&& posY + menuHeight + secMargin >= winHeight){
		  posLeft = posX - menuWidth - secMargin + "px";
		  posTop = posY - menuHeight - secMargin + "px";
		}
		else if(posX + menuWidth + secMargin >= winWidth){
		  posLeft = posX - menuWidth - secMargin + "px";
		  posTop = posY + secMargin + "px";
		}
		else if(posY + menuHeight + secMargin >= winHeight){
		  posLeft = posX + secMargin + "px";
		  posTop = posY - menuHeight - secMargin + "px";
		}
		else {
		  posLeft = posX + secMargin + "px";
		  posTop = posY + secMargin + "px";
		};
		$(".contextmenu").css({
		  "left": posLeft,
		  "top": posTop
		}).show();
		return false;
		
}
	function initialize(){
		// 真实地图的宽高比如果比屏幕的宽高比大，则要将<SVG>的width扩大。否则会显示不全
		var n=realMapWidth/realMapHeight-screenWidth/screenHeight;
		if(n>0){
			$('#mainSVG').css('width',(100+Math.ceil(n*100))+'%');
		}
		// makeMapWithJSON_withTrack();
		//makeMap2();
		makVehicle();
		resetVehicle();
	}
	function makeSVG(tag, attrs) {
		var el= document.createElementNS('http://www.w3.org/2000/svg', tag);
		for (var k in attrs)
			el.setAttribute(k, attrs[k]);
		return el;
	}
	function makeMap() {
			
			for(var i=0;i<x_lineNum;i++){
				// 画横线
				for(var j=0;j<y_lineNum-1;j++){
					var p_left={"X":p_left_top.X+j*gridWD,"Y":p_left_top.Y+i*gridHT};
					var p_right={"X":p_left_top.X+(j+1)*gridWD,"Y":p_left_top.Y+i*gridHT};
					var line=makeSVG("line",{x1:p_left.X,y1:p_left.Y,x2:p_right.X,y2:p_right.Y});
					$("#mainSVG").append(line);
				}
				/*var p_left={"X":p_left_top.X,"Y":p_left_top.Y+i*gridHT};
				var p_right={"X":p_right_top.X,"Y":p_right_top.Y+i*gridHT};
				var line=makeSVG("line",{x1:p_left.X,y1:p_left.Y,x2:p_right.X,y2:p_right.Y});
				$("#mainSVG").append(line);
				*/
			}
			for(var i=0;i<y_lineNum;i++){
				
				// 画竖线
				for(var j=0;j<y_lineNum-1;j++){
					var p_left={"X":p_left_top.X+i*gridWD,"Y":p_left_top.Y+j*gridHT};
					var p_right={"X":p_left_top.X+i*gridWD,"Y":p_left_top.Y+(j+1)*gridHT};
					var line=makeSVG("line",{x1:p_left.X,y1:p_left.Y,x2:p_right.X,y2:p_right.Y});
					$("#mainSVG").append(line);
				}
				/*p_left={"X":p_left_top.X+i*gridWD,"Y":p_left_top.Y};
				p_right={"X":p_left_bottom.X+i*gridWD,"Y":p_left_bottom.Y};
				var line=makeSVG("line",{x1:p_left.X,y1:p_left.Y,x2:p_right.X,y2:p_right.Y});
				$("#mainSVG").append(line);
				*/
			}
			// alert(screenWidth+"---"+screenHeight+"---"+leftOffset+"---"+topOffset);
		}
	function makeMap2() {
		
		for(var i=0;i<x_lineNum;i++){
			// 画横线
			
			var p_left={"X":p_left_top.X,"Y":p_left_top.Y+i*gridHT};
			var p_right={"X":p_right_top.X,"Y":p_right_top.Y+i*gridHT};
			var line=makeSVG("line",{x1:p_left.X,y1:p_left.Y,x2:p_right.X,y2:p_right.Y});
			$("#mainSVG").append(line);
			
		}
		for(var i=0;i<y_lineNum;i++){
			
			// 画竖线
			
			p_left={"X":p_left_top.X+i*gridWD,"Y":p_left_top.Y};
			p_right={"X":p_left_bottom.X+i*gridWD,"Y":p_left_bottom.Y};
			var line=makeSVG("line",{x1:p_left.X,y1:p_left.Y,x2:p_right.X,y2:p_right.Y});
			$("#mainSVG").append(line);
			
		}
		// alert(screenWidth+"---"+screenHeight+"---"+leftOffset+"---"+topOffset);
	}
	/**
	 *地图坐标系的坐标值 转换为 浏览器坐标系的坐标值
	 * @description 因浏览器的坐标系是左上角为原点，而地图坐标系的原点在左下角，所以要将小车的位置坐标转换一下
	 * p_left_bottom 地图原点在浏览器坐标系中的坐标值
	 * @param coor 地图坐标系的坐标值
	 * @return 浏览器坐标系中的坐标值
	*/
	function transformToBrowserCoor(coor){
		var transformedCoor={};
		transformedCoor.X=p_left_bottom.X+coor.X;
		transformedCoor.Y=p_left_bottom.Y-coor.Y;
		return transformedCoor;	
	}
	/**
	 *浏览器坐标系的坐标值 转换为 地图坐标系的坐标值
	 * @param coor 浏览器坐标系中的坐标值
     * @return 地图坐标系的坐标值
	*/
	function transformToMapCoor (coor){
		var transformedCoor={};
		transformedCoor.X=coor.X;-p_left_bottom.X
		transformedCoor.Y=p_left_bottom.Y-coor.Y;
		return transformedCoor;	
	}
	
	/**
	 *将小车传输来的真实坐标按比例转换为虚拟地图上的坐标，并转换坐标系
	 */
	function tranformRealCoorToBrowserCoor(coor){
		var transformedCoor={};
		transformedCoor.X=real_virtual_rate*coor.X;
		transformedCoor.Y=real_virtual_rate*coor.Y;
		return transformToBrowserCoor(transformedCoor);	
	}
	function makVehicle(vehicleIp){
		var vihecleOffset=3;
		var vihecleLength=gridWD-vihecleOffset;
		var vehicle=makeSVG('rect',{x:0,y:0,width:vihecleLength,height:vihecleLength,id:'vehicle-01',style:"stroke: blue; fill: red;",transform:"translate("+(-vihecleLength/2)+","+(-vihecleLength/2)+")"});
		$("#mainSVG").append(vehicle);
	}

	function resetVehicle(){
		vehicleMoveTo("vehicle-01",tranformRealCoorToBrowserCoor({'X':0,'Y':0}));
	}
	function updateVehiclePosition(vehicleId,coor){
		vehicleMoveTo("vehicle-01",tranformRealCoorToBrowserCoor(coor));
	}
	function vehicleMoveTo(vehicleId,coor){
		$("#"+vehicleId).attr("x",coor.X).attr("y",coor.Y);
	}
	function onLineClick(event){
		//var e = event || window.event;
		//var browseXY={"X":e.screenX,"Y":e.screenY};
		//var mapXY=transformToMapCoor(tranformBrowseXYToSVGXY(browseXY));
		//alert(mapXY.X+"===="+mapXY.Y);
		//alert(e.screenX+"----"+e.screenY);
		
		/*var range = document.getElementById("p_lt").getBoundingClientRect();
		var range2 = document.getElementById("p_lt").getBBox();
		var rect = range.getBoundingClientRect();
		var x = rect.left, y = box.top;*/
	}	
	function onSendToPoint(){
		var coorStr = prompt("请输入终点坐标","0,0");
        if(coorStr){
			var coor ={"X":coorStr.split(",")[0],"Y":coorStr.split(",")[1]};
			 window.parent.moveToPoint(coor);
         }
	}
	
	function onChangeContainerDirection(){
		var direction = prompt("请输入货柜最终朝向,1-向前，2-向右","1");
		if(direction){
			if(isNaN(direction) || (direction!=1&& direction !=2)){
				alert("请输入数字1或2");
				return;
			}
			window.parent.changeContainerDirection(direction);
		}
	}
	
	function onStopCharge(){
		window.parent.stopCharge();
	}
	function onStartCharge(){
		window.parent.startCharge();
	}
	
	function onQueryNaviTrails(){
		window.parent.queryNaviTrails();
	}
	function onWritePackageSize(){
		var size = prompt("请输入包裹尺寸","100.0,100.0");
		 if(size){
			var packageSize ={"packageLength":size.split(",")[0],"packageWidth":size.split(",")[1]};
			window.parent.writePackageSize(packageSize);
         }
		
	}